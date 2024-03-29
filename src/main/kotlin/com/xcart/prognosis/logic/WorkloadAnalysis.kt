package com.xcart.prognosis.logic

import com.xcart.prognosis.domain.Issue
import com.xcart.prognosis.domain.IssueInfo
import com.xcart.prognosis.domain.LocalDateExtensions.isBusinessDay
import com.xcart.prognosis.domain.LocalDateExtensions.isHoliday
import com.xcart.prognosis.domain.LocalDateExtensions.isVacationDay
import com.xcart.prognosis.domain.LocalDateExtensions.isWeekend
import com.xcart.prognosis.domain.LocalDateExtensions.listDaysUntil
import com.xcart.prognosis.domain.LocalDateExtensions.nextDay
import com.xcart.prognosis.domain.User
import com.xcart.prognosis.domain.VacationPeriod
import com.xcart.prognosis.reports.workload.DailyWorkloadItem
import com.xcart.prognosis.reports.workload.DailyWorkloadItemPhase
import com.xcart.prognosis.reports.workload.DailyWorkloadItemType
import com.xcart.prognosis.repositories.DayOff
import java.time.LocalDate

internal const val TESTING_PHASE_RATE: Float = 0.2f

class WorkloadAnalysis(
    private val issues: List<Issue>,
    private val dayOff: DayOff
) {

    fun getOverdueIssues(user: User, after: LocalDate): Int {
        val overdueIssues = issues
            .filter {
                it.assignee?.login == user.login
                    && it.endDate != null
                    && it.endDate < LocalDate.now()
            }
        return overdueIssues.sumBy { it.estimation }
    }

    fun getDailyWorkloadForUser(
        user: User,
        startDate: LocalDate
    ): List<DailyWorkloadItem> {
        val userIssues = issues.filter { it.assignee?.login == user.login }
        val filteredIssues = userIssues.filter { it.endDate != null }
        val lastIssue = filteredIssues.maxBy { it.endDate ?: LocalDate.MIN }
        if (lastIssue == null || filteredIssues.isNullOrEmpty()) {
            return emptyList()
        }
        val endDate = lastIssue.endDate
        return if (startDate < endDate && endDate != null)
            startDate.listDaysUntil(endDate)
                .map(toDailyWorkloadItem(filteredIssues, user))
        else {
            emptyList()
        }
    }

    fun getRescheduledIssue(
        issue: Issue,
        newStartDate: LocalDate
    ): Issue {
        if (issue.assignee == null || issue.endDate == null) {
            throw Exception(
                "Issue has no assignee or end date - cannot be " +
                    "rescheduled"
            )
        }

        val vacations = dayOff.getUserVacations(issue.assignee)
        val businessDaysShift = getBusinessDaysCountBetween(
            issue.startDate,
            newStartDate,
            vacations,
            includingEnd = false
        )

        val verificationDate = if (issue.verificationDate !== null)
            shiftToAnotherBusinessDay(
                issue.verificationDate,
                businessDaysShift,
                vacations
            )
        else null

        val dueDate = if (issue.dueDate !== null)
            shiftToAnotherBusinessDay(
                issue.dueDate,
                businessDaysShift, vacations
            )
        else null

        return Issue(issue, newStartDate, verificationDate, dueDate)
    }

    fun getIssueSwimlane(
        issue: Issue,
        startDate: LocalDate
    ): List<DailyWorkloadItem> {
        if (issue.assignee == null || issue.endDate == null || issue.endDate < startDate) {
            return emptyList()
        }
        return startDate.listDaysUntil(issue.endDate)
            .map(toDailyWorkloadItem(listOf(issue), issue.assignee, true))
    }

    private fun toDailyWorkloadItem(
        issues: List<Issue>,
        user: User,
        singleIssue: Boolean = false
    ): (LocalDate) -> DailyWorkloadItem {
        return { date ->
            val issuesOnDay = issues
                .filter { it.startDate <= date && it.endDate!! >= date }
            val issueInfo = if (singleIssue) null
            else issuesOnDay.map { IssueInfo(it) }
            val vacations = dayOff.getUserVacations(user)
            val verificationDate =
                if (singleIssue && !issuesOnDay.isNullOrEmpty())
                    issuesOnDay.first()
                        .verificationDate else null

            val type = when {
                date.isVacationDay(vacations) -> DailyWorkloadItemType.Vacation
                date.isHoliday() -> DailyWorkloadItemType.Holiday
                date.isWeekend() -> DailyWorkloadItemType.Weekend
                else -> DailyWorkloadItemType.WorkingDay
            }

            val phase = when {
                verificationDate != null && date == verificationDate ->
                    DailyWorkloadItemPhase.VerificationDay
                verificationDate != null && date > verificationDate ->
                    DailyWorkloadItemPhase.Testing
                else -> DailyWorkloadItemPhase.Implementation
            }

            val value = when {
                type == DailyWorkloadItemType.WorkingDay
                    && issuesOnDay.isNotEmpty() -> calculateWorkloadValue(
                    date, issuesOnDay, vacations
                )
                else -> 0f
            }
            DailyWorkloadItem(date, value, issueInfo, type, phase)
        }
    }

    private fun calculateWorkloadValue(
        day: LocalDate,
        issuesOnDay: List<Issue>,
        vacations: List<VacationPeriod>
    ): Float {
        return issuesOnDay.fold(0f) { acc, issue ->
            val verificationDate = issue.verificationDate ?: issue.endDate!!

            val workload = if (day <= verificationDate) {
                if (issue.isOnImplementationStage) {
                    getDistributedWorkload(
                        issue.estimation, issue.startDate, verificationDate,
                        vacations
                    )
                } else {
                    0.0f
                }
            } else {
                getDistributedWorkload(
                    (issue.estimation * TESTING_PHASE_RATE).toInt(),
                    verificationDate.nextDay(),
                    issue.endDate!!,
                    vacations
                )
            }

            acc + workload
        }
    }

    private fun getDistributedWorkload(
        totalWork: Int,
        startDate: LocalDate,
        endDate: LocalDate,
        vacations: List<VacationPeriod>
    ): Float {
        val days = getBusinessDaysCountBetween(
            startDate, endDate,
            vacations
        )
        return if (days > 0)
            (totalWork / days).toFloat()
        else 0f
    }

    private fun shiftToAnotherBusinessDay(
        original: LocalDate,
        amount: Int,
        vacations: List<VacationPeriod>
    ): LocalDate {
        var date = original
        var added = 0
        while (added < kotlin.math.abs(amount)) {
            date = if (amount > 0)
                date.plusDays(1)
            else date.minusDays(1)
            if (date.isBusinessDay() && !date.isVacationDay(vacations)) {
                added++
            }
        }

        return date
    }

    private fun addBusinessDaysTo(
        date: LocalDate,
        amount: Int,
        vacations: List<VacationPeriod>
    ): LocalDate {
        var result = date
        var added = 0
        while (added < amount) {
            result = date.plusDays(1)
            if (date.isBusinessDay() && !date.isVacationDay(vacations)) {
                added++
            }
        }

        return result
    }

    private fun getBusinessDaysCountBetween(
        startDate: LocalDate,
        endDate: LocalDate,
        vacations: List<VacationPeriod>,
        includingEnd: Boolean = true
    ): Int {
        val daysList = if (endDate > startDate) {
            startDate.listDaysUntil(endDate)
        } else {
            endDate.listDaysUntil(startDate)
        }
        var absCount = daysList
            .filter { it.isBusinessDay() }
            .filter { !it.isVacationDay(vacations) }
            .count()

        if (!includingEnd && absCount > 0) {
            absCount -= 1
        }

        return if (endDate > startDate) {
            absCount
        } else {
            absCount * -1
        }
    }
}
