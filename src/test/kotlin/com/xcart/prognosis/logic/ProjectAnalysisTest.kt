package com.xcart.prognosis.logic

import com.xcart.prognosis.domain.Issue
import com.xcart.prognosis.domain.IssueCustomField
import com.xcart.prognosis.domain.IssueInfo
import com.xcart.prognosis.domain.User
import com.xcart.prognosis.reports.projects.Project
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.sql.Timestamp
import java.time.LocalDate

internal class ProjectAnalysisTest {

    @Test
    fun getProjectsList() {
        val reporter = User(id = "2", login = "pm", fullName = "Test PM")
        val assignee = hashMapOf<String, Any?>(
                "id" to "1",
                "login" to "assignee",
                "fullName" to "Test Assignee"
        )
        val issues = listOf<Issue>(
                // 2020-01-01 - 2020-01-10
                Issue(id = "1",
                        created = 1577836800000,
                        isDraft = false,
                        reporter = reporter,
                        customFields = listOf(
                                IssueCustomField(name = "Assignee", value = assignee),
                                IssueCustomField(name = "Client", value = hashMapOf<String, Any?>("name" to "Test Client")),
                                IssueCustomField(name = "Due Date", value = 1578614400000),
                                IssueCustomField(name = "Estimation", value = hashMapOf<String, Any?>("minutes" to 600))
                        )
                ),
                // 2020-01-05 - 2020-01-15
                Issue(id = "2",
                        created = 1578182400000,
                        isDraft = false,
                        reporter = reporter,
                        customFields = listOf(
                                IssueCustomField(name = "Assignee", value = assignee),
                                IssueCustomField(name = "Client", value = hashMapOf<String, Any?>("name" to "Test Client")),
                                IssueCustomField(name = "Due Date", value = 1579046400000),
                                IssueCustomField(name = "Estimation", value = hashMapOf<String, Any?>("minutes" to 1200))
                        )
                )
        )
        val result = ProjectAnalysis(issues).getProjectsList(Timestamp(1577836800000).toLocalDateTime().toLocalDate())
        val expected = listOf(
                Project(client = "Test Client",
                        team = listOf(User(assignee)),
                        tasks = issues.map { IssueInfo(it) },
                        estimation = 1800,
                        manager = reporter,
                        startDate = Timestamp(1577836800000).toLocalDateTime().toLocalDate(),
                        endDate = Timestamp(1579046400000).toLocalDateTime().toLocalDate(),
                        duration = 14,
                        offset = 0
                )
        )

        assertEquals(expected, result)
    }
}