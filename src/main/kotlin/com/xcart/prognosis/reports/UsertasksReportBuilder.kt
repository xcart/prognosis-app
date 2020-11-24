package com.xcart.prognosis.reports

import com.xcart.prognosis.domain.Issue
import com.xcart.prognosis.domain.IssueInfo
import com.xcart.prognosis.logic.WorkloadAnalysis
import com.xcart.prognosis.reports.usertasks.TaskWorkload
import com.xcart.prognosis.reports.usertasks.UsertasksReport
import com.xcart.prognosis.repositories.YouTrack
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class UsertasksReportBuilder @Autowired constructor(val youTrack: YouTrack) {
    fun gather(login: String, query: String): UsertasksReport {
        val issues = youTrack.fetchIssues(modifyQuery(query, login))
        val tasks = getTaskWorkloadList(issues)
        val duration = getReportDuration(tasks)
        return UsertasksReport(tasks, duration, login)
    }

    private fun getTaskWorkloadList(issues: List<Issue>): List<TaskWorkload> {
        val analysis = WorkloadAnalysis(issues)
        return issues.fold(mutableListOf<TaskWorkload>()) { acc, issue ->
            val swimlane = analysis.getIssueSwimlane(issue, LocalDate.now())
            if (swimlane.isNotEmpty()) {
                acc.add(TaskWorkload(swimlane, IssueInfo(issue), issue.startDate, issue.endDate!!))
            }
            acc
        }.sortedBy { it.endDate }
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