package com.xcart.prognosis.domain

import java.sql.Timestamp
import java.time.LocalDate
import java.util.*

data class Issue(
        val id: String = "",
        val idReadable: String = "",
        val created: Long = 0,
        val summary: String? = null,
        val isDraft: Boolean = false,
        val reporter: User? = null,
        val customFields: List<IssueCustomField> = emptyList()
) {
    /**
     * Issue estimation in minutes
     */
    val estimation: Int?
        get() {
            val cfield = customFields.find { it.name == "Estimation" }
            return if (cfield?.value is HashMap<*, *>) cfield.value["minutes"] as Int else null
        }

    /**
     * Due date timestamp
     */
    val dueDate: LocalDate?
        get() {
            val cfield = customFields.find { it.name == "Due Date" }
            return if (cfield?.value !== null) Timestamp(cfield.value as Long).toLocalDateTime().toLocalDate() else null
        }

    /**
     * Due date timestamp
     */
    val verificationDate: LocalDate?
        get() {
            val cfield = customFields.find { it.name == "Verification date" }
            return if (cfield?.value !== null) Timestamp(cfield.value as Long).toLocalDateTime().toLocalDate() else null
        }

    val state: Enum<IssueState>?
        get() {
            val cfield = customFields.find { it.name == "State" }
            if (cfield?.value === null || cfield.value !is HashMap<*, *>) {
                return null
            }

            return when (cfield.value["name"]) {
                "New" -> IssueState.New
                "Open" -> IssueState.Open
                "In progress" -> IssueState.InProgress
                "Waiting" -> IssueState.Waiting
                "Quality Assurance" -> IssueState.QualityAssurance
                "QA In Progress" -> IssueState.QaInProgress
                "QA Passed" -> IssueState.QaPassed
                "Completed" -> IssueState.Completed
                "Canceled" -> IssueState.Canceled
                else -> null
            }
        }

    /**
     * Start date timestamp (approximate)
     */
    val startDate: LocalDate
        get() {
            val cfield = customFields.find { it.name == "Start Date" }
            val timestamp = if (cfield?.value !== null) cfield.value as Long else created
            return Timestamp(timestamp).toLocalDateTime().toLocalDate()
        }

    /**
     * Start date timestamp (approximate)
     */
    val endDate: LocalDate?
        get() {
            return when (state) {
                IssueState.New,
                IssueState.Open,
                IssueState.InProgress,
                IssueState.Waiting -> {
                    if (verificationDate !== null) verificationDate else dueDate
                }
                else -> dueDate
            }
        }

    val assignee: User?
        get() {
            val cfield = customFields.find { it.name == "Assignee" }
            return if (cfield?.value is HashMap<*, *>) User(cfield.value) else null
        }


    val client: String?
        get() {
            val cfield = customFields.find { it.name == "Client" }
            return if (cfield?.value is HashMap<*, *>) cfield.value["name"] as String else null
        }
}

