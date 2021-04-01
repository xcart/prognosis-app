package com.xcart.prognosis.controllers

import com.xcart.prognosis.presentation.CommonPageState
import com.xcart.prognosis.presentation.PageContext
import com.xcart.prognosis.reports.ProjectsReportBuilder
import com.xcart.prognosis.services.AuthenticationFacade
import com.xcart.prognosis.services.Configuration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/projects")
class ProjectsController @Autowired constructor(
    val reportBuilder: ProjectsReportBuilder,
    val authentication: AuthenticationFacade,
    val config: Configuration
) {

    @GetMapping("")
    fun getWorkloadPageState(@RequestParam query: String?): ResponseEntity<CommonPageState> {
        val queryToUse = if (query.isNullOrEmpty())
            config.queryProjects
        else query

        val context = PageContext(username = authentication.getUsername())
        val report =
            CommonPageState(queryToUse, reportBuilder.gather(queryToUse), context)
        return ResponseEntity.ok(report)
    }
}