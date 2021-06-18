package com.xcart.prognosis.reports.workload

import com.xcart.prognosis.domain.IssueInfo
import com.xcart.prognosis.domain.User
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class TaskWorkload(
    val swimlane: List<DailyWorkloadItem>,
    val issue: IssueInfo,
    val startDate: LocalDate,
    val verificationDate: LocalDate,
    val endDate: LocalDate,
    val assignee: User? = null
) {
    val offset = run {
        ChronoUnit.DAYS.between(LocalDate.now(), startDate)
    }

    val overdue = run {
        LocalDate.now() > endDate
    }

    val missedVerification = run {
        LocalDate.now() > verificationDate && issue.notImplemented
    }
}