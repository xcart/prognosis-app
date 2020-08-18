package com.xcart.prognosis.domain

import java.time.LocalDate

data class IssueInfo(
        val id: String = "",
        val idReadable: String = "",
        val summary: String? = null,
        val startDate: LocalDate? = null,
        val endDate: LocalDate? = null,
        val state: Enum<IssueState>? = null
) {
    constructor(issue: Issue) : this(issue.id, issue.idReadable, issue.summary, issue.startDate, issue.endDate, issue.state)
}