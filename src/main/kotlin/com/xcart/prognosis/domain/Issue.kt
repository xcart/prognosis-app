package com.xcart.prognosis.domain

import com.fasterxml.jackson.annotation.JsonCreator
import java.sql.Timestamp
import java.time.LocalDate

internal const val DEFAULT_ESTIMATION: Int = 4 * 60 // four hours

data class Issue(
    val id: String,
    val idReadable: String,
    val state: IssueState,
    val startDate: LocalDate,
    val verificationDate: LocalDate?,
    val dueDate: LocalDate?,
    val estimation: Int = DEFAULT_ESTIMATION,
    val summary: String? = null,
    val isDraft: Boolean = false,
    val reporter: User? = null,
    val assignee: User? = null,
    val manager: User? = null,
    val client: String? = null
) {
    // Rescheduling constructor
    constructor(
        issue: Issue, startDate: LocalDate, verificationDate:
        LocalDate?, dueDate: LocalDate?
    ) : this(
        issue.id, issue.idReadable,
        issue.state, startDate,
        verificationDate, dueDate,
        issue.estimation, issue.summary,
        issue.isDraft, issue.reporter, issue.assignee, issue.manager, issue
            .client
    )

    companion object {
        @JvmStatic
        @JsonCreator
        fun createFromYoutrack(
            id: String = "",
            idReadable: String = "",
            created: Long = 0,
            summary: String? = null,
            isDraft: Boolean = false,
            reporter: User? = null,
            customFields: List<IssueCustomField> = emptyList()
        ): Issue {
            val assignee = run {
                val cfield = customFields.find { it.name == "Assignee" }
                if (cfield?.value is HashMap<*, *>) User(cfield.value) else null
            }

            val manager = run {
                val cfield = customFields.find { it.name == "Delivery Manager" }
                if (cfield?.value is HashMap<*, *>) User(
                    cfield.value
                ) else reporter
            }

            val client = run {
                val cfield = customFields.find { it.name == "Client" }
                if (cfield?.value is HashMap<*, *>) cfield.value["name"] as String else null
            }

            /**
             * Start date
             */
            val startDate: LocalDate = run {
                val cfield = customFields.find { it.name == "Start Date" }
                val timestamp =
                    if (cfield?.value !== null) cfield.value as Long else created
                Timestamp(timestamp).toLocalDateTime().toLocalDate()
            }

            /**
             * Due date
             */
            val dueDate = run {
                val cfield = customFields.find { it.name == "Due Date" }
                if (cfield?.value !== null) Timestamp(
                    cfield.value as Long
                ).toLocalDateTime().toLocalDate() else null
            }

            /**
             * Verification date
             */
            val verificationDate = run {
                val cfield =
                    customFields.find { it.name == "Verification Date" }
                if (cfield?.value !== null) Timestamp(cfield.value as Long)
                    .toLocalDateTime().toLocalDate() else dueDate
            }

            val state = run {
                val cfield = customFields.find { it.name == "State" }
                if (cfield?.value === null || cfield.value !is HashMap<*, *>) {
                    IssueState.Submitted
                } else {
                    when (cfield.value["name"]) {
                        "Waiting" -> IssueState.Waiting
                        "Submitted" -> IssueState.Submitted
                        "Scheduled" -> IssueState.Scheduled
                        "In progress" -> IssueState.InProgress
                        "Quality Assurance" -> IssueState.QualityAssurance
                        "Has Defects" -> IssueState.HasDefects
                        "QA Passed" -> IssueState.QaPassed
                        "On Review" -> IssueState.OnReview
                        "Completed" -> IssueState.Completed
                        "Canceled" -> IssueState.Canceled
                        else -> IssueState.Submitted
                    }
                }
            }

            /**
             * Issue estimation in minutes
             */
            val estimation = run {
                val cfield = customFields.find { it.name == "Estimation" }
                val estimation = if (cfield?.value is HashMap<*, *>)
                    cfield.value["minutes"] as Int
                else DEFAULT_ESTIMATION
                when (state) {
                    IssueState.OnReview,
                    IssueState.QaPassed,
                    IssueState.Completed,
                    IssueState.Canceled -> 0

                    else -> estimation
                }
            }
            return Issue(
                id, idReadable, state, startDate, verificationDate, dueDate,
                estimation, summary, isDraft, reporter, assignee, manager,
                client
            )
        }
    }

    val isOnImplementationStage = run {
        (state.ordinal ?: 0) < IssueState.QualityAssurance.ordinal
    }

    /**
     * End date
     */
    val endDate = run {
        if (dueDate !== null)
            dueDate
        else verificationDate
    }
}

