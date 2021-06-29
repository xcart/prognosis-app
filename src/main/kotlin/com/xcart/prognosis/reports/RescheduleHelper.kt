package com.xcart.prognosis.reports

import com.xcart.prognosis.domain.IssueCustomField
import com.xcart.prognosis.domain.IssueInfo
import com.xcart.prognosis.errors.ReschedulingError
import com.xcart.prognosis.logic.WorkloadAnalysis
import com.xcart.prognosis.reports.workload.TaskWorkload
import com.xcart.prognosis.repositories.DayOff
import com.xcart.prognosis.repositories.YouTrack
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDate

@Service
class RescheduleHelper @Autowired constructor(
    val dayOff: DayOff,
    val youTrack: YouTrack
) {
    fun rescheduleIssue(issueId: String, shiftAmount: Int):
        TaskWorkload {
        val issue = youTrack.fetchIssue(issueId)
        if (issue.verificationDate === null) {
            throw ReschedulingError(
                "Issue $issueId does not have a " +
                    "verification date."
            )
        }

        val newStartDate = issue.startDate.plusDays(shiftAmount.toLong())
        val analysis = WorkloadAnalysis(listOf(issue), dayOff)
        val rescheduledIssue = analysis.getRescheduledIssue(issue, newStartDate)
        val swimlane = analysis.getIssueSwimlane(rescheduledIssue, newStartDate)

        return TaskWorkload(
            swimlane,
            IssueInfo(issue),
            rescheduledIssue.startDate,
            rescheduledIssue.verificationDate!!,
            rescheduledIssue.endDate!!,
            rescheduledIssue.assignee
        )
    }

    fun persistChange(
        issueId: String, startDate: LocalDate,
        verificationDate: LocalDate, endDate: LocalDate
    ) {
        youTrack.updateIssueFields(
            issueId,
            listOf(
                getDateCustomField("Start Date", startDate),
                getDateCustomField("Verification Date", verificationDate),
                getDateCustomField("Due Date", endDate)
            )
        )
    }

    fun getDateCustomField(name: String, value: LocalDate): IssueCustomField {
        return IssueCustomField(
            name = name,
            type = "DateIssueCustomField",
            value = Timestamp
                        .valueOf(value.atStartOfDay())
        )
    }
}