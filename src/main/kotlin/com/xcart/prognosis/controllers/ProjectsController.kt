package com.xcart.prognosis.controllers

import com.xcart.prognosis.presentation.CommonPageState
import com.xcart.prognosis.reports.ProjectsReportBuilder
import com.xcart.prognosis.reports.UsertasksReportBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/projects")
class ProjectsController @Autowired constructor(val reportBuilder: ProjectsReportBuilder) {

    @GetMapping("")
    fun getWorkloadPageState(@RequestParam query: String?): ResponseEntity<CommonPageState> {
        val queryToUse = if (query.isNullOrEmpty())
            "Project: WD State: New, Open, Waiting, {In progress}"
        else query

        val report = CommonPageState(queryToUse, reportBuilder.gather(queryToUse))
        return ResponseEntity.ok(report);
    }
}