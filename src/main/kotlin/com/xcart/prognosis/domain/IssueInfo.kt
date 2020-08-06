package com.xcart.prognosis.domain

import java.time.LocalDate

data class IssueInfo(
        val id: String = "",
        val idReadable: String = "",
        val summary: String? = null,
        val startDate: LocalDate? = null,
        val endDate: LocalDate? = null
)