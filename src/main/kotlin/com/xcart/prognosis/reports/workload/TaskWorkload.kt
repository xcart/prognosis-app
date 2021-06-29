package com.xcart.prognosis.reports.workload

import com.fasterxml.jackson.annotation.JsonFormat
import com.xcart.prognosis.domain.IssueInfo
import com.xcart.prognosis.domain.User
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class TaskWorkload(
    val swimlane: List<DailyWorkloadItem>,
    val issue: IssueInfo,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val startDate: LocalDate,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val verificationDate: LocalDate,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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