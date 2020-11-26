package com.xcart.prognosis.reports

import com.xcart.prognosis.domain.Issue
import com.xcart.prognosis.logic.ProjectAnalysis
import com.xcart.prognosis.reports.projects.Project
import com.xcart.prognosis.reports.projects.ProjectGroup
import com.xcart.prognosis.reports.projects.ProjectsReport
import com.xcart.prognosis.reports.projects.WorkloadSpan
import com.xcart.prognosis.repositories.YouTrack
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Service
class ProjectsReportBuilder @Autowired constructor(val youTrack: YouTrack) {
    fun gather(query: String): ProjectsReport {
        val issues = youTrack.fetchIssues(query)
        val groups = getProjectGroups(issues)
        val duration = groups.maxBy { it.duration }?.duration
        return ProjectsReport(groups, duration ?: 30)
    }

    fun getProjectGroups(issues: List<Issue>): List<ProjectGroup> {
        val projects = getProjects(issues)
        return projects.groupBy { it.manager }.map { ProjectGroup(it.key, sumEstimation(it.value), findDuration(it.value), it.value, getWorkloadSpans(it.value)) }.sortedByDescending { it.estimation }
    }

    fun getProjects(issues: List<Issue>): List<Project> {
        val analysis = ProjectAnalysis(issues)
        return analysis.getProjectsList(LocalDate.now())
    }

    fun getWorkloadSpans(projects: List<Project>): List<WorkloadSpan> {
        val analysis = ProjectAnalysis(emptyList())
        return analysis.getWorkloadSpans(projects, LocalDate.now())
    }

    fun sumEstimation(projects: List<Project>): Int {
        return projects.sumBy { it.estimation }
    }

    fun findDuration(projects: List<Project>): Int {
        val project = projects.filter { it.endDate != null }.maxBy { it.endDate!! }
        if (project != null && project.endDate!! > LocalDate.now()) {
            return ChronoUnit.DAYS.between(LocalDate.now(), project.endDate).toInt()
        }
        return 30
    }
}