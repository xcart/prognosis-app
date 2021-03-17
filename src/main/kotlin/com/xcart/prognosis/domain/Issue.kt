package com.xcart.prognosis.domain

import com.xcart.prognosis.domain.LocalDateExtensions.isBusinessDay
import com.xcart.prognosis.domain.LocalDateExtensions.isVacationDay
import com.xcart.prognosis.domain.LocalDateExtensions.listDaysUntil
import com.xcart.prognosis.repositories.DayOff
import com.xcart.prognosis.services.ContextUtil
import java.sql.Timestamp
import java.time.LocalDate
import java.util.*

internal const val DEFAULT_ESTIMATION: Int = 60

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
    private val verificationDate = run {
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
                "Requested" -> IssueState.Requested
                "Assigned" -> IssueState.Assigned
                "Open" -> IssueState.Open
                "In progress" -> IssueState.InProgress
                "Waiting" -> IssueState.Waiting
                "Quality Assurance" -> IssueState.QualityAssurance
                "Has Defects" -> IssueState.HasDefects
                "Ready To Deploy" -> IssueState.ReadyToDeploy
                "QA Passed" -> IssueState.QaPassed
                "Completed" -> IssueState.Completed
                "Canceled" -> IssueState.Canceled
                else -> null
            }
        }
    }

    /**
     * Start date timestamp (approximate)
     */
    val endDate = when (state) {
        IssueState.Requested,
        IssueState.Assigned,
        IssueState.Open,
        IssueState.InProgress,
        IssueState.Waiting -> {
            if (verificationDate !== null && verificationDate > LocalDate.now()) verificationDate else dueDate
        }
        else -> dueDate
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
            IssueState.QualityAssurance,
            IssueState.HasDefects -> (estimation * 0.1f).toInt()

            IssueState.ReadyToDeploy,
            IssueState.QaPassed,
            IssueState.Completed,
            IssueState.Canceled -> 0

            else -> estimation
        }
    }

    fun getBusinessDaysCount(): Int? {
        return if (endDate == null) {
            null
        } else {
            // TODO: Rewrite without this ugly hack
            val dayOff = ContextUtil.getBean(DayOff::class.java)
            startDate.listDaysUntil(endDate)
                    .filter { it.isBusinessDay() }
                    .filter { assignee == null || !it.isVacationDay(dayOff.getUserVacations(assignee)) }
                    .count()
        }
    }
}

