package com.xcart.prognosis.controllers

import com.xcart.prognosis.presentation.CommonPageState
import com.xcart.prognosis.presentation.PageContext
import com.xcart.prognosis.reports.UsertasksReportBuilder
import com.xcart.prognosis.services.AuthenticationFacade
import com.xcart.prognosis.services.Configuration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/usertasks")
class UsertasksController @Autowired constructor(
    val reportBuilder: UsertasksReportBuilder,
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
            CommonPageState(queryToUse, reportBuilder.gather(login,
                                                             queryToUse), context)
        return ResponseEntity.ok(report)
    }
}