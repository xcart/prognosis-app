package com.xcart.prognosis.logic

import com.xcart.prognosis.domain.*
import com.xcart.prognosis.reports.workload.DailyWorkloadItem
import com.xcart.prognosis.reports.workload.DailyWorkloadItemType
import com.xcart.prognosis.repositories.DayOff
import com.xcart.prognosis.repositories.XbApi
import com.xcart.prognosis.services.ContextUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.sql.Timestamp
import java.time.LocalDate

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class WorkloadAnalysisTest {

    @MockBean
    lateinit var xbApi: XbApi

    @BeforeAll
    fun setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.`when`(xbApi.getVacationsInfo()).thenReturn(emptyList())
    }

    @Test
    fun getDailyWorkloadForUser() {
        val user = hashMapOf<String, Any?>(
                "id" to "1",
                "login" to "test",
                "fullName" to "Test User"
        )
        val issues = listOf<Issue>(
                // 2020-01-01 - 2020-01-10
                Issue(id = "1",
                        created = 1577836800000,
                        isDraft = false,
                        customFields = listOf(
                                IssueCustomField(name = "Assignee", value = user),
                                IssueCustomField(name = "Due Date", value = 1578614400000),
                                IssueCustomField(name = "Estimation", value = hashMapOf<String, Any?>("minutes" to 600))
                        )
                ),
                // 2020-01-05 - 2020-01-15
                Issue(id = "2",
                        created = 1578182400000,
                        isDraft = false,
                        customFields = listOf(
                                IssueCustomField(name = "Assignee", value = user),
                                IssueCustomField(name = "Due Date", value = 1579046400000),
                                IssueCustomField(name = "Estimation", value = hashMapOf<String, Any?>("minutes" to 1200))
                        )
                )
        )
        val vacations = ContextUtil.getBean(DayOff::class.java).getAllVacations()
        assertEquals(emptyList<VacationPeriod>(), vacations)

        val startDate = Timestamp(1577836800000).toLocalDateTime().toLocalDate()
        val result = WorkloadAnalysis(issues).getDailyWorkloadForUser(User(login = "test"), startDate)

        val list1 = listOf(IssueInfo(issues[0]))
        val list2 = listOf(IssueInfo(issues[0]), IssueInfo(issues[1]))
        val list3 = listOf(IssueInfo(issues[1]))
        val expected = listOf(
                DailyWorkloadItem(LocalDate.of(2020, 1, 1), 75f, list1),
                DailyWorkloadItem(LocalDate.of(2020, 1, 2), 75f, list1),
                DailyWorkloadItem(LocalDate.of(2020, 1, 3), 75f, list1),
                DailyWorkloadItem(LocalDate.of(2020, 1, 4), 0f, list1, DailyWorkloadItemType.Weekend),
                DailyWorkloadItem(LocalDate.of(2020, 1, 5), 0f, list2, DailyWorkloadItemType.Weekend),
                DailyWorkloadItem(LocalDate.of(2020, 1, 6), 225f, list2),
                DailyWorkloadItem(LocalDate.of(2020, 1, 7), 225f, list2),
                DailyWorkloadItem(LocalDate.of(2020, 1, 8), 225f, list2),
                DailyWorkloadItem(LocalDate.of(2020, 1, 9), 225f, list2),
                DailyWorkloadItem(LocalDate.of(2020, 1, 10), 225f, list2),
                DailyWorkloadItem(LocalDate.of(2020, 1, 11), 0f, list3, DailyWorkloadItemType.Weekend),
                DailyWorkloadItem(LocalDate.of(2020, 1, 12), 0f, list3, DailyWorkloadItemType.Weekend),
                DailyWorkloadItem(LocalDate.of(2020, 1, 13), 150f, list3),
                DailyWorkloadItem(LocalDate.of(2020, 1, 14), 150f, list3),
                DailyWorkloadItem(LocalDate.of(2020, 1, 15), 150f, list3)
        )
        assertEquals(expected, result)
    }
}