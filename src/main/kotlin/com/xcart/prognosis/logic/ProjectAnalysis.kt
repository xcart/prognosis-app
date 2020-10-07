package com.xcart.prognosis.logic

import com.xcart.prognosis.domain.Issue
import com.xcart.prognosis.domain.IssueInfo
import com.xcart.prognosis.domain.User
import com.xcart.prognosis.reports.projects.Project
import com.xcart.prognosis.reports.projects.WorkloadSpan
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.stream.Stream
import kotlin.streams.toList

class ProjectAnalysis(private val issues: List<Issue>) {
    private val holidays = emptyList<LocalDate>()

    fun getProjectsList(from: LocalDate): List<Project> {
        return issues
            .groupBy { it.client }
            .filterKeys { !it.isNullOrEmpty() }
            .map {
                val projectStartDate = findStartDate(it.value)
                val projectEndDate = findEndDate(it.value)
                val estimation = sumEstimation(it.value)
                val duration = findDuration(projectStartDate, projectEndDate)
                Project(client = it.key!!,
                        manager = findManager(it.value),
                        team = findTeam(it.value),
                        tasks = findTasks(it.value),
                        estimation = estimation,
                        offset = findDuration(from, projectStartDate),
                        duration = duration,
                        startDate = projectStartDate,
                        endDate = projectEndDate
                )
            }
            .sortedByDescending { it.estimation }
            .filter { it.endDate != null }
            .filter { it.estimation > 0 && it.endDate!! > from }
    }

    public fun getWorkloadSpans(projects: List<Project>, from: LocalDate): List<WorkloadSpan> {
        val filteredProjects = projects.filter { it.endDate != null }
        val last = filteredProjects.maxBy { it.endDate ?: LocalDate.MIN }
        if (last == null || filteredProjects.isNullOrEmpty()) {
            return emptyList()
        }
        val endDate = last.endDate
        return if (from < endDate && endDate != null)
            listDaysBetween(from, endDate).fold(mutableMapOf<String, WorkloadSpan>(), { acc, date ->
                val projectsOnDay = filteredProjects
                        .filter { it.startDate!! <= date && it.endDate!! >= date }
                val key = projectsOnDay.fold("", { key, proj -> key + proj.client })
                if (acc.contains(key)) {
                    acc[key]!!.duration++
                } else {
                    acc[key] = WorkloadSpan(1, projectsOnDay)
                }
                acc
            }).map { it.value }
        else {
            emptyList()
        }
    }

    private fun listDaysBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val daysBetween = ChronoUnit.DAYS.between(startDate, endDate)
        return Stream.iterate(startDate, { date -> date.plusDays(1) })
                .limit(daysBetween + 1)
                .toList()
    }

    private fun LocalDate.isBusinessDay(): Boolean {
        val isHoliday = { date: LocalDate -> holidays.contains(date) }
        val isWeekend = { date: LocalDate ->
            (date.dayOfWeek === DayOfWeek.SATURDAY
                    || date.dayOfWeek === DayOfWeek.SUNDAY)
        }

        return !isHoliday(this) && !isWeekend(this)
    }

    private fun findDuration(startDate: LocalDate?, endDate: LocalDate?): Int {
        if (startDate == null || endDate == null) {
            return 0
        }

        return ChronoUnit.DAYS.between(startDate, endDate).toInt()
    }

    private fun findManager(issues: List<Issue>): User {
        return issues
                .groupBy { it.reporter }
                .filter { it.key != null }
                .maxBy { it.value.size }
                ?.key!!
    }

    private fun findTeam(issues: List<Issue>): List<User> {
        return issues.mapNotNull { it.assignee }.distinctBy { it.login }
    }

    private fun findTasks(issues: List<Issue>): List<IssueInfo> {
        return issues.map { IssueInfo(it) }
    }

    private fun sumEstimation(issues: List<Issue>): Int {
        return issues.filter { it.estimation != null }
            .sumBy { it.estimation!! }
    }

    private fun avgLoad(issues: List<Issue>): Int {
        return issues.filter { it.estimation != null }
            .sumBy { it.estimation!! }
    }

    private fun findStartDate(issues: List<Issue>): LocalDate? {
        return issues
            .map { it.startDate }
            .min()
    }

    private fun findEndDate(issues: List<Issue>): LocalDate? {
        return issues.filter { it.dueDate != null }
            .map { it.dueDate!! }
            .max()
    }
}
