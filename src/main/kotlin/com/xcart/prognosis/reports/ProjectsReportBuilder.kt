package com.xcart.prognosis.reports

import com.xcart.prognosis.domain.Issue
import com.xcart.prognosis.domain.Team
import com.xcart.prognosis.domain.User
import com.xcart.prognosis.logic.ProjectAnalysis
import com.xcart.prognosis.logic.WorkloadAnalysis
import com.xcart.prognosis.reports.projects.Project
import com.xcart.prognosis.reports.projects.ProjectsReport
import com.xcart.prognosis.reports.workload.TeamWorkload
import com.xcart.prognosis.reports.workload.UserWorkload
import com.xcart.prognosis.reports.workload.WorkloadReport
import com.xcart.prognosis.repositories.YouTrack
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ProjectsReportBuilder @Autowired constructor(val youTrack: YouTrack) {
    fun gather(query: String): ProjectsReport {
        val issues = youTrack.fetchIssues(query)
        val projects = getProjects(issues)
        return ProjectsReport(projects)
    }

    fun getProjects(issues: List<Issue>): List<Project> {
        val analysis = ProjectAnalysis(issues)
        return analysis.getProjectsList()
    }
}