package com.xcart.prognosis.reports

import com.xcart.prognosis.domain.IssueInfo
import java.time.LocalDate

enum class StatValueKey {
    SwimlaneDuration
}

data class StatValue (
        val key: StatValueKey,
        val value: Number,
        val label: String = "",
        val precision: Int = 1,
        val unit: String = ""
)