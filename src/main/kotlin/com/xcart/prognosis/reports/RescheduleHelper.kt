package com.xcart.prognosis.reports

import com.xcart.prognosis.domain.IssueInfo
import com.xcart.prognosis.errors.ReschedulingError
import com.xcart.prognosis.logic.WorkloadAnalysis
import com.xcart.prognosis.reports.workload.TaskWorkload
import com.xcart.prognosis.repositories.DayOff
import com.xcart.prognosis.repositories.YouTrack
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class RescheduleHelper @Autowired constructor(
    val dayOff: DayOff,
    val youTrack: YouTrack
) {
    fun rescheduleIssue(issueId: String, newStartDate: LocalDate):
        TaskWorkload {
        val issue = youTrack.fetchIssue(issueId)
        if (issue.verificationDate === null) {
            throw ReschedulingError("Issue $issueId does not have a " +
                                        "verification date.")
        }

        val analysis = WorkloadAnalysis(listOf(issue), dayOff)
        val swimlane = analysis.getRescheduledIssueSwimlane(issue, newStartDate)
        return TaskWorkload(
            swimlane,
            IssueInfo(issue),
            issue.startDate,
            issue.verificationDate,
            issue.endDate!!,
            issue.assignee
        )
    }
}