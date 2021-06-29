package com.xcart.prognosis.domain

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class IssueInfo(
    val id: String = "",
    val idReadable: String = "",
    val summary: String? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val startDate: LocalDate? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val verificationDate: LocalDate? = null,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val endDate: LocalDate? = null,
    val state: IssueState,
    val notImplemented: Boolean = true
) {
    constructor(issue: Issue) : this(
        issue.id, issue.idReadable, issue.summary, issue.startDate,
        issue.verificationDate, issue.endDate,
        issue.state,
        issue.isOnImplementationStage
    )
}