package com.xcart.prognosis.controllers

import com.xcart.prognosis.presentation.CommonPageState
import com.xcart.prognosis.presentation.PageContext
import com.xcart.prognosis.reports.ProjectReportBuilder
import com.xcart.prognosis.reports.ProjectsReportBuilder
import com.xcart.prognosis.services.AuthenticationFacade
import com.xcart.prognosis.services.Configuration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/projects")
class ProjectsController @Autowired constructor(
    val projectsReportBuilder: ProjectsReportBuilder,
    val projectReportBuilder: ProjectReportBuilder,
    val authentication: AuthenticationFacade,
    val config: Configuration
) {

    @GetMapping("")
    fun getWorkloadPageState(@RequestParam query: String?): ResponseEntity<CommonPageState> {
        val queryToUse = if (query.isNullOrEmpty())
            config.queryProjects
        else query

        val context = PageContext(
            username = authentication.getUsername(),
            youtrackUrl = config.youtrackUrl
        )
        val report =
            CommonPageState(
                queryToUse, projectsReportBuilder.gather(queryToUse), context
            )
        return ResponseEntity.ok(report)
    }

    @GetMapping("/{client}")
    fun getWorkloadPageState(
        @PathVariable client: String,
        @RequestParam query: String?
    ): ResponseEntity<CommonPageState> {
        val queryToUse = if (query.isNullOrEmpty())
            config.queryProjects
        else query

        val context = PageContext(
            username = authentication.getUsername(),
            youtrackUrl = config.youtrackUrl
        )
        val report =
            CommonPageState(
                queryToUse, projectReportBuilder.gather(
                    client,
                    queryToUse
                ), context
            )
        return ResponseEntity.ok(report)
    }
}