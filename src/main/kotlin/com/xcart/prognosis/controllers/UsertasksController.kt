package com.xcart.prognosis.controllers

import com.xcart.prognosis.presentation.CommonPageState
import com.xcart.prognosis.presentation.PageContext
import com.xcart.prognosis.reports.UsertasksReportBuilder
import com.xcart.prognosis.services.AppConfiguration
import com.xcart.prognosis.services.AuthenticationFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/usertasks")
class UsertasksController @Autowired constructor(
    val reportBuilder: UsertasksReportBuilder,
    val authentication: AuthenticationFacade,
    val config: AppConfiguration
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
            youtrackUrl = config.youtrackUrl,
            canChangeIssues = authentication.canChangeIssues()
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
}