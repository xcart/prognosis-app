package com.xcart.prognosis.logic

import com.xcart.prognosis.domain.Issue
import com.xcart.prognosis.domain.IssueInfo
import com.xcart.prognosis.domain.LocalDateExtensions.isBusinessDay
import com.xcart.prognosis.domain.LocalDateExtensions.isVacationDay
import com.xcart.prognosis.domain.LocalDateExtensions.listDaysUntil
import com.xcart.prognosis.domain.User
import com.xcart.prognosis.reports.usertasks.TaskDailyWorkloadItem
import com.xcart.prognosis.reports.workload.DailyWorkloadItem
import com.xcart.prognosis.repositories.DayOff
import com.xcart.prognosis.services.ContextUtil
import java.time.LocalDate

class WorkloadAnalysis(private val issues: List<Issue>) {

    private var dayOff: DayOff = ContextUtil.getBean(DayOff::class.java)

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

    fun getIssueSwimlane(issue: Issue, startDate: LocalDate): List<TaskDailyWorkloadItem> {
        if (issue.assignee == null || issue.endDate == null || issue.endDate!! < startDate) {
            return emptyList()
        }
        return startDate.listDaysUntil(issue.endDate!!)
                .map(toTaskDailyWorkloadItem(issue, issue.assignee!!))
    }

    private fun toDailyWorkloadItem(issues: List<Issue>, user: User): (LocalDate) -> DailyWorkloadItem {
        return { date ->
            var issuesOnDay = issues
                    .filter { it.startDate <= date && it.endDate!! >= date }
            var value = calculateWorkloadValue(date, issuesOnDay, user)
            DailyWorkloadItem(date, value, issuesOnDay.map { IssueInfo(it) })
        }
    }

    private fun toTaskDailyWorkloadItem(issue: Issue, user: User): (LocalDate) -> TaskDailyWorkloadItem {
        return { date ->
            var issuesOnDay = listOf(issue)
                    .filter { it.startDate <= date && it.endDate!! >= date }
            var value = calculateWorkloadValue(date, issuesOnDay, user)
            TaskDailyWorkloadItem(date, value)
        }
    }

    private fun calculateWorkloadValue(date: LocalDate, issuesOnDay: List<Issue>, user: User): Float {
        if (issuesOnDay.isEmpty() || !date.isBusinessDay() || date.isVacationDay(dayOff.getUserVacations(user))) {
            return 0f
        }
        return issuesOnDay.fold(0f) { acc, issue ->
            val issueDays = if (issue.businessDays == null) 1 else issue.businessDays!!
            val workload: Float = if (issue.estimation != null && issueDays > 0)
                (issue.estimation!! / issueDays).toFloat()
            else 150.0f
            acc + workload
        }
    }
}
