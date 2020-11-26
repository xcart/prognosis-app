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
    /**
     * Issue estimation in minutes
     */
    val estimation = {
        val cfield = customFields.find { it.name == "Estimation" }
        val estimation = if (cfield?.value is HashMap<*, *>)
            cfield.value["minutes"] as Int
        else DEFAULT_ESTIMATION

        when (state) {
            IssueState.QualityAssurance,
            IssueState.HasDefects,
            IssueState.QaInProgress -> (estimation * 0.1f).toInt()

            IssueState.QaPassed,
            IssueState.Completed,
            IssueState.Canceled -> 0

            else -> estimation
        }
    }()

    /**
     * Due date timestamp
     */
    val dueDate = {
        val cfield = customFields.find { it.name == "Due Date" }
        if (cfield?.value !== null) Timestamp(cfield.value as Long).toLocalDateTime().toLocalDate() else null
    }()

    /**
     * Due date timestamp
     */
    private val verificationDate = {
        val cfield = customFields.find { it.name == "Verification date" }
        if (cfield?.value !== null) Timestamp(cfield.value as Long).toLocalDateTime().toLocalDate() else null
    }()

    val state = {
        val cfield = customFields.find { it.name == "State" }
        if (cfield?.value === null || cfield.value !is HashMap<*, *>) {
            null
        } else {
            when (cfield.value["name"]) {
                "New" -> IssueState.New
                "Open" -> IssueState.Open
                "In progress" -> IssueState.InProgress
                "Waiting" -> IssueState.Waiting
                "Quality Assurance" -> IssueState.QualityAssurance
                "Has Defects" -> IssueState.HasDefects
                "QA In Progress" -> IssueState.QaInProgress
                "QA Passed" -> IssueState.QaPassed
                "Completed" -> IssueState.Completed
                "Canceled" -> IssueState.Canceled
                else -> null
            }
        }
    }()

    /**
     * Start date timestamp (approximate)
     */
    val startDate: LocalDate = {
        val cfield = customFields.find { it.name == "Start Date" }
        val timestamp = if (cfield?.value !== null) cfield.value as Long else created
        Timestamp(timestamp).toLocalDateTime().toLocalDate()
    }()

    /**
     * Start date timestamp (approximate)
     */
    val endDate = {
        when (state) {
            IssueState.New,
            IssueState.Open,
            IssueState.InProgress,
            IssueState.Waiting -> {
                if (verificationDate !== null) verificationDate else dueDate
            }
            else -> dueDate
        }
    }()

    val assignee = {
        val cfield = customFields.find { it.name == "Assignee" }
        if (cfield?.value is HashMap<*, *>) User(cfield.value) else null
    }()

    val client = {
        val cfield = customFields.find { it.name == "Client" }
        if (cfield?.value is HashMap<*, *>) cfield.value["name"] as String else null
    }()

    val businessDays = {
        if (endDate == null) {
            null
        } else {
            val dayOff = ContextUtil.getBean(DayOff::class.java)
            startDate.listDaysUntil(endDate)
                    .filter { it.isBusinessDay() }
                    .filter { assignee == null || !it.isVacationDay(dayOff.getUserVacations(assignee)) }
                    .count()

        }

    }()
}

