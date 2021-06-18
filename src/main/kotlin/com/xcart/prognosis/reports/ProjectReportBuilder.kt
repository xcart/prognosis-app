package com.xcart.prognosis.reports

import com.xcart.prognosis.domain.Issue
import com.xcart.prognosis.domain.IssueInfo
import com.xcart.prognosis.logic.WorkloadAnalysis
import com.xcart.prognosis.reports.project.ProjectReport
import com.xcart.prognosis.reports.workload.TaskWorkload
import com.xcart.prognosis.repositories.DayOff
import com.xcart.prognosis.repositories.YouTrack
import com.xcart.prognosis.repositories.YouTrackHub
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProjectReportBuilder @Autowired constructor(
    val youTrack: YouTrack,
    val youTrackHub: YouTrackHub,
    val dayOff: DayOff
) {
    fun gather(client: String, query: String): ProjectReport {
        val issues = youTrack.fetchIssues(modifyQuery(query, client))
        val tasks = getTaskWorkloadList(issues)
        val duration = getReportDuration(tasks)
        return ProjectReport(tasks, duration, null, client)
    }

    private fun getTaskWorkloadList(issues: List<Issue>): List<TaskWorkload> {
        val analysis = WorkloadAnalysis(issues, dayOff)
        return issues.fold(mutableListOf<TaskWorkload>()) { acc, issue ->
            if (issue.endDate != null) {
                val swimlane = analysis.getIssueSwimlane(issue, issue.startDate)
                acc.add(
                    TaskWorkload(
                        swimlane,
                        IssueInfo(issue),
                        issue.startDate,
                        issue.verificationDate!!,
                        issue.endDate,
                        issue.assignee
                    )
                )
            }
            acc
        }.sortedBy { it.startDate }
    }

    private fun modifyQuery(query: String, client: String): String {
        return "$query Client: {$client}"
    }

    private fun getReportDuration(tasks: List<TaskWorkload>): Number {
        return tasks.fold(0) { max, issue ->
            if (issue.swimlane.size > max) issue.swimlane.size else max
        }
    }
}