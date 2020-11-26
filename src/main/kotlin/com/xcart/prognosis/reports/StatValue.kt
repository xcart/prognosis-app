package com.xcart.prognosis.reports

enum class StatValueKey {
    SwimlaneDuration, OverdueEstimation
}

data class StatValue (
        val key: StatValueKey,
        val value: Number,
        val label: String = "",
        val precision: Int = 1,
        val unit: String = ""
)