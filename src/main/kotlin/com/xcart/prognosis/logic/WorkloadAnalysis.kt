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
import com.xcart.prognosis.reports.workload.DailyWorkloadItemType
import com.xcart.prognosis.repositories.DayOff
import java.time.LocalDate

internal const val TESTING_PHASE_RATE: Float = 0.2f

class WorkloadAnalysis(private val issues: List<Issue>, private val dayOff: DayOff) {

    fun getOverdueIssues(user: User, after: LocalDate): Int {
        val overdueIssues = issues
            .filter {
                it.assignee?.login == user.login
                    && it.endDate != null
                    && it.endDate < LocalDate.now()
            }
        return overdueIssues.sumBy { it.estimation }
    }

    fun getDailyWorkloadForUser(user: User, startDate: LocalDate): List<DailyWorkloadItem> {
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

    fun getIssueSwimlane(issue: Issue, startDate: LocalDate): List<DailyWorkloadItem> {
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
            val issueInfo = if (singleIssue) null else issuesOnDay.map { IssueInfo(it) }
            val vacations = dayOff.getUserVacations(user)
            val type = when {
                date.isVacationDay(vacations) -> DailyWorkloadItemType.Vacation
                date.isHoliday() -> DailyWorkloadItemType.Holiday
                date.isWeekend() -> DailyWorkloadItemType.Weekend
                else -> DailyWorkloadItemType.WorkingDay
            }
            val value = when {
                type == DailyWorkloadItemType.WorkingDay
                    && issuesOnDay.isNotEmpty() -> calculateWorkloadValue(date, issuesOnDay, vacations)
                else -> 0f
            }
            DailyWorkloadItem(date, value, issueInfo, type)
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
                        issue.estimation, issue.startDate, verificationDate, vacations
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
        val days = getBusinessDaysCountBetween(startDate, endDate, vacations)
        return if (days > 0)
            (totalWork / days).toFloat()
        else 0f
    }

    private fun getBusinessDaysCountBetween(
        startDate: LocalDate,
        endDate: LocalDate,
        vacations: List<VacationPeriod>
    ): Int {
        return startDate.listDaysUntil(endDate)
            .filter { it.isBusinessDay() }
            .filter { !it.isVacationDay(vacations) }
            .count()
    }
}
