package com.xcart.prognosis.controllers

import com.xcart.prognosis.presentation.CommonPageState
import com.xcart.prognosis.presentation.PageContext
import com.xcart.prognosis.presentation.RescheduledIssue
import com.xcart.prognosis.reports.RescheduleHelper
import com.xcart.prognosis.reports.UsertasksReportBuilder
import com.xcart.prognosis.services.AuthenticationFacade
import com.xcart.prognosis.services.Configuration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate


@RestController
@RequestMapping("/api/usertasks")
class UsertasksController @Autowired constructor(
    val reportBuilder: UsertasksReportBuilder,
    val rescheduleHelper: RescheduleHelper,
    val authentication: AuthenticationFacade,
    val config: Configuration
) {

    @GetMapping("/{login}")
    fun getWorkloadPageState(
        @PathVariable login: String,
        @RequestParam query: String?
    ): ResponseEntity<CommonPageState> {
        val queryToUse = if (query.isNullOrEmpty())
            config.queryIssues
        else query

        val context = PageContext(
            username = authentication.getUsername(),
            youtrackUrl = config.youtrackUrl
        )
        val report =
            CommonPageState(
                queryToUse, reportBuilder.gather(
                    login,
                    queryToUse
                ), context
            )
        return ResponseEntity.ok(report)
    }

    @GetMapping("/{issueId}/reschedule")
    fun getRescheduledIssueState(
        @PathVariable issueId: String,
        @RequestParam(name = "start_from") startFrom: String
    ): ResponseEntity<RescheduledIssue> {
        val workload = rescheduleHelper.rescheduleIssue(
            issueId,
            newStartDate = LocalDate.parse(startFrom)
        )

        val issue = RescheduledIssue(issueId, workload)
        return ResponseEntity.ok(issue)
    }
}