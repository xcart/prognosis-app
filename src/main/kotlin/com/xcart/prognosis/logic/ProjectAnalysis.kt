package com.xcart.prognosis.logic

import com.xcart.prognosis.domain.Issue
import com.xcart.prognosis.domain.IssueInfo
import com.xcart.prognosis.domain.User
import com.xcart.prognosis.reports.projects.Project
import java.time.LocalDate
import java.util.*

class ProjectAnalysis(private val issues: List<Issue>) {
    fun getProjectsList(): List<Project> {
        return issues
            .groupBy { it.client }
            .filterKeys { !it.isNullOrEmpty() }
            .map {
                Project(client = it.key!!,
                        manager = findManager(it.value),
                        team = findTeam(it.value),
                        tasks = findTasks(it.value),
                        estimation = sumEstimation(it.value),
                        endDate = findEndDate(it.value)
                )
            }
            .sortedByDescending { it.estimation }
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

    private fun findEndDate(issues: List<Issue>): LocalDate? {
        return issues.filter { it.dueDate != null }
            .map { it.dueDate!! }
            .max()
    }
}
