package com.xcart.prognosis.domain

import java.sql.Timestamp
import java.time.LocalDate
import java.util.*

internal const val DEFAULT_ESTIMATION: Int = 4 * 60 // four hours

data class Issue(
        val id: String = "",
        val idReadable: String = "",
        val created: Long = 0,
        val summary: String? = null,
        val isDraft: Boolean = false,
        val reporter: User? = null,
        val customFields: List<IssueCustomField> = emptyList()
) {
    val assignee = run {
        val cfield = customFields.find { it.name == "Assignee" }
        if (cfield?.value is HashMap<*, *>) User(cfield.value) else null
    }

    val manager = run {
        val cfield = customFields.find { it.name == "Delivery Manager" }
        if (cfield?.value is HashMap<*, *>) User(cfield.value) else reporter
    }

    val client = run {
        val cfield = customFields.find { it.name == "Client" }
        if (cfield?.value is HashMap<*, *>) cfield.value["name"] as String else null
    }

    /**
     * Start date timestamp (approximate)
     */
    val startDate: LocalDate = run {
        val cfield = customFields.find { it.name == "Start Date" }
        val timestamp = if (cfield?.value !== null) cfield.value as Long else created
        Timestamp(timestamp).toLocalDateTime().toLocalDate()
    }

    /**
     * Due date timestamp
     */
    val dueDate = run {
        val cfield = customFields.find { it.name == "Due Date" }
        if (cfield?.value !== null) Timestamp(cfield.value as Long).toLocalDateTime().toLocalDate() else null
    }

    /**
     * Due date timestamp
     */
    val verificationDate = run {
        val cfield = customFields.find { it.name == "Verification Date" }
        if (cfield?.value !== null) Timestamp(cfield.value as Long).toLocalDateTime().toLocalDate() else null
    }

    // TODO: Add tests on depending fields
    val state = run {
        val cfield = customFields.find { it.name == "State" }
        if (cfield?.value === null || cfield.value !is HashMap<*, *>) {
            null
        } else {
            when (cfield.value["name"]) {
                "Scheduled" -> IssueState.Scheduled
                "Submitted" -> IssueState.Submitted
                "Assigned" -> IssueState.Assigned
                "Open" -> IssueState.Open
                "In progress" -> IssueState.InProgress
                "Waiting" -> IssueState.Waiting
                "Quality Assurance" -> IssueState.QualityAssurance
                "Has Defects" -> IssueState.HasDefects
                "On Review" -> IssueState.OnReview
                "QA Passed" -> IssueState.QaPassed
                "Completed" -> IssueState.Completed
                "Canceled" -> IssueState.Canceled
                else -> null
            }
        }
    }

    val isOnImplementationStage = run {
        (state?.ordinal ?: 0) < IssueState.QualityAssurance.ordinal
    }

    /**
     * Start date timestamp (approximate)
     */
    val endDate = run {
        if (dueDate !== null)
            dueDate
        else verificationDate
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
}

