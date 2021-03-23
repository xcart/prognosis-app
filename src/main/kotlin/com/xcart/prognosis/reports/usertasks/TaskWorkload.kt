package com.xcart.prognosis.reports.usertasks

import com.xcart.prognosis.domain.IssueInfo
import com.xcart.prognosis.reports.workload.DailyWorkloadItem
import java.time.LocalDate

data class TaskWorkload(
    val swimlane: List<DailyWorkloadItem>,
    val issue: IssueInfo,
    val startDate: LocalDate,
    val endDate: LocalDate
) {
    val isOverdue = run {
        LocalDate.now() > endDate
    }

    var isMissedVerification = run {
        LocalDate.now() > issue.verificationDate
    }
}