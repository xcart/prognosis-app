package com.xcart.prognosis.reports

import com.xcart.prognosis.domain.IssueInfo
import java.time.LocalDate

data class StatValue (val label: String, val value: Float, val precision: Int = 1, val unit: String = "")