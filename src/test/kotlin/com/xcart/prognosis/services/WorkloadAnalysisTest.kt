package com.xcart.prognosis.services

import com.xcart.prognosis.domain.Issue
import com.xcart.prognosis.domain.IssueCustomField
import com.xcart.prognosis.domain.User
import org.junit.jupiter.api.Test
import java.sql.Timestamp

internal class WorkloadAnalysisTest {

    @Test
    fun getDailyWorkloadForUser() {
        val user = hashMapOf<String, Any?>(
                "id" to "1",
                "login" to "test",
                "fullName" to "Test User"
        )
        val issues = listOf<Issue>(
            // 2020-01-01 - 2020-01-10
            Issue(created = 1577836800000,
                isDraft = false,
                customFields = listOf(
                        IssueCustomField(name = "Assignee", value = user),
                        IssueCustomField(name = "Due Date", value = 1578614400000),
                        IssueCustomField(name = "Estimation", value = hashMapOf<String, Any?>("minutes" to 600))
                )
            ),
            // 2020-01-05 - 2020-01-15
            Issue(created = 1578182400000,
                    isDraft = false,
                    customFields = listOf(
                            IssueCustomField(name = "Assignee", value = user),
                            IssueCustomField(name = "Due Date", value = 1579046400000),
                            IssueCustomField(name = "Estimation", value = hashMapOf<String, Any?>("minutes" to 1200))
                    )
            )
        )
        val startDate = Timestamp(1577836800000).toLocalDateTime().toLocalDate()
        val result = WorkloadAnalysis(issues).getDailyWorkloadForUser(User(id = "1"), startDate)

//        val list1 = listOf<Issue>(issues[0])
//        val list2 = listOf<Issue>(issues[0],issues[1])
//        val list3 = listOf<Issue>(issues[1])
//        val expected = listOf<WorkloadItem>(
//                WorkloadItem(LocalDate.of(2020, 1, 1), 75f, list1),
//                WorkloadItem(LocalDate.of(2020, 1, 2), 75f, list1),
//                WorkloadItem(LocalDate.of(2020, 1, 3), 75f, list1),
//                WorkloadItem(LocalDate.of(2020, 1, 4), 0f, list1),
//                WorkloadItem(LocalDate.of(2020, 1, 5), 0f, list2),
//                WorkloadItem(LocalDate.of(2020, 1, 6), 225f, list2),
//                WorkloadItem(LocalDate.of(2020, 1, 7), 225f, list2),
//                WorkloadItem(LocalDate.of(2020, 1, 8), 225f, list2),
//                WorkloadItem(LocalDate.of(2020, 1, 9), 225f, list2),
//                WorkloadItem(LocalDate.of(2020, 1, 10), 225f, list2),
//                WorkloadItem(LocalDate.of(2020, 1, 11), 0f, list3),
//                WorkloadItem(LocalDate.of(2020, 1, 12), 0f, list3),
//                WorkloadItem(LocalDate.of(2020, 1, 13), 150f, list3),
//                WorkloadItem(LocalDate.of(2020, 1, 14), 150f, list3),
//                WorkloadItem(LocalDate.of(2020, 1, 15), 150f, list3)
//        )
//        assertEquals(expected, result)
    }
}