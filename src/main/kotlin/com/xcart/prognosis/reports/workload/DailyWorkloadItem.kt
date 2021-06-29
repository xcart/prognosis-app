package com.xcart.prognosis.reports.workload

import com.fasterxml.jackson.annotation.JsonFormat
import com.xcart.prognosis.domain.IssueInfo
import java.time.LocalDate

data class DailyWorkloadItem(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date: LocalDate,
    val workload: Float,
    val issues: List<IssueInfo>? = null,
    val type: DailyWorkloadItemType = DailyWorkloadItemType.WorkingDay,
    val phase: DailyWorkloadItemPhase = DailyWorkloadItemPhase.Implementation
)

enum class DailyWorkloadItemType {
    WorkingDay, Vacation, Holiday, Weekend
}

enum class DailyWorkloadItemPhase {
    Implementation, VerificationDay, Testing
}