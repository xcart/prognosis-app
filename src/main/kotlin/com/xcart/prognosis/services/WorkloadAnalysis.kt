package com.xcart.prognosis.services

import com.xcart.prognosis.domain.Issue
import com.xcart.prognosis.domain.User
import com.xcart.prognosis.reports.WorkloadItem
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Stream
import kotlin.streams.toList


class WorkloadAnalysis (private val issues: List<Issue>) {
    private val holidays = emptyList<LocalDate>()

    fun getDailyWorkloadForUser(user: User, startDate: LocalDate): List<WorkloadItem> {
        val userIssues = issues.filter { it.assignee?.id == user.id }
        val filteredIssues = userIssues.filter { it.dueDate != null }
        val lastIssue = filteredIssues.maxBy { it.dueDate ?: LocalDate.MIN }
        if (lastIssue == null || filteredIssues.isNullOrEmpty()) {
            return emptyList()
        }
        val endDate = lastIssue.dueDate
        return if (startDate < endDate && endDate != null)
            listDaysBetween(startDate, endDate)
                    .map(getMappingFunc(filteredIssues))
        else {
            return emptyList()
        }
    }

    private fun listDaysBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val daysBetween = ChronoUnit.DAYS.between(startDate, endDate)
        return Stream.iterate(startDate, { date -> date.plusDays(1) })
                .limit(daysBetween + 1)
                .toList()
    }

    private fun LocalDate.isBusinessDay(): Boolean {
        val isHoliday = { date: LocalDate -> holidays.contains(date)  }
        val isWeekend = { date: LocalDate ->
            (date.dayOfWeek === DayOfWeek.SATURDAY
                    || date.dayOfWeek === DayOfWeek.SUNDAY)
        }

        return !isHoliday(this) && !isWeekend(this)
    }

    private fun countBusinessDaysBetween(startDate: LocalDate, endDate: LocalDate) : Int {
        return listDaysBetween(startDate, endDate)
                .filter { it.isBusinessDay() }
                .count()
    }

    private fun getMappingFunc(issues: List<Issue>): (LocalDate) -> WorkloadItem {
        return { date ->
            var issuesOnDay = issues
                    .filter { it.startDate <= date && it.dueDate!! >= date && it.estimation > 0 }
            var value = calculateWorkloadValue (date, issuesOnDay)
            WorkloadItem(date, value)
        }
    }

    private fun calculateWorkloadValue(date: LocalDate, issuesOnDay: List<Issue>): Float {
        if (!date.isBusinessDay()) {
            return 0f
        }
        return issuesOnDay.fold(0f) { acc, issue ->
            val issueDays = issue.dueDate?.let {
                countBusinessDaysBetween(issue.startDate, it)
            } ?: 1
            acc + (issue.estimation / issueDays)
        }
    }
}