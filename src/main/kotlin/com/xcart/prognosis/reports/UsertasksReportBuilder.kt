package com.xcart.prognosis.reports

import com.xcart.prognosis.domain.Issue
import com.xcart.prognosis.domain.IssueInfo
import com.xcart.prognosis.domain.User
import com.xcart.prognosis.logic.WorkloadAnalysis
import com.xcart.prognosis.reports.usertasks.UsertasksReport
import com.xcart.prognosis.reports.workload.TaskWorkload
import com.xcart.prognosis.repositories.DayOff
import com.xcart.prognosis.repositories.YouTrack
import com.xcart.prognosis.repositories.YouTrackHub
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UsertasksReportBuilder @Autowired constructor(
    val youTrack: YouTrack,
    val youTrackHub: YouTrackHub,
    val dayOff: DayOff
) {
    fun gather(login: String, query: String): UsertasksReport {
        val hubUser = youTrackHub.fetchUser(login)
        return if (hubUser == null) {
            UsertasksReport(emptyList(), 0, null, login)
        } else {
            val user = User(hubUser)
            val issues = youTrack.fetchIssues(modifyQuery(query, login))
            val tasks = getTaskWorkloadList(issues)
            val duration = getReportDuration(tasks)
            UsertasksReport(tasks, duration, user, login)
        }
    }

    private fun getTaskWorkloadList(issues: List<Issue>):
        List<TaskWorkload> {
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

    private fun modifyQuery(query: String, login: String): String {
        return "$query Assignee: $login"
    }

    private fun getReportDuration(tasks: List<TaskWorkload>): Number {
        return tasks.fold(0) { max, issue ->
            if (issue.swimlane.size > max) issue.swimlane.size else max
        }
    }
}