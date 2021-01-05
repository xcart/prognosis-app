package com.xcart.prognosis.logic

import com.xcart.prognosis.domain.Issue
import com.xcart.prognosis.domain.IssueInfo
import com.xcart.prognosis.domain.LocalDateExtensions.countDaysUntil
import com.xcart.prognosis.domain.LocalDateExtensions.listDaysUntil
import com.xcart.prognosis.domain.User
import com.xcart.prognosis.reports.projects.Project
import com.xcart.prognosis.reports.projects.WorkloadSpan
import java.time.LocalDate

class ProjectAnalysis(private val issues: List<Issue>) {

    fun getProjectsList(from: LocalDate): List<Project> {
        return issues
            .groupBy { it.client }
            .filterKeys { !it.isNullOrEmpty() }
            .filterValues { findStartDate(it) != null && findEndDate(it) != null }
            .map {
                val projectStartDate = findStartDate(it.value)
                val projectEndDate = findEndDate(it.value)
                Project(client = it.key!!,
                        manager = findManager(it.value),
                        team = findTeam(it.value),
                        tasks = findTasks(it.value),
                        estimation = sumEstimation(it.value),
                        offset = from.countDaysUntil(projectStartDate!!),
                        duration = projectStartDate.countDaysUntil(projectEndDate!!),
                        startDate = projectStartDate,
                        endDate = projectEndDate
                )
            }
            .sortedByDescending { it.estimation }
            .filter { it.endDate != null }
            .filter { it.estimation > 0 && it.endDate!! > from }
    }

    fun getWorkloadSpans(projects: List<Project>, from: LocalDate): List<WorkloadSpan> {
        val filteredProjects = projects.filter { it.endDate != null }
        val last = filteredProjects.maxBy { it.endDate ?: LocalDate.MIN }
        if (last == null || filteredProjects.isNullOrEmpty()) {
            return emptyList()
        }
        val endDate = last.endDate
        return if (from < endDate && endDate != null)
            from.listDaysUntil(endDate).fold(mutableMapOf<String, WorkloadSpan>(), { acc, date ->
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

    private fun findManager(issues: List<Issue>): User {
        return issues
                .groupBy { it.manager }
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
        return issues.sumBy { it.estimation }
    }

    private fun avgLoad(issues: List<Issue>): Int {
        return issues.sumBy { it.estimation }
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
