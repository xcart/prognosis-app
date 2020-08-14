package com.xcart.prognosis.controllers

import com.xcart.prognosis.presentation.WorkloadPageState
import com.xcart.prognosis.reports.WorkloadReportBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/workload")
class WorkloadController @Autowired constructor(val reportBuilder: WorkloadReportBuilder) {

    @GetMapping("")
    fun getWorkloadPageState(@RequestParam query: String?): ResponseEntity<WorkloadPageState> {
        val queryToUse = if (query.isNullOrEmpty())
            "Project: WD State: Open, Waiting, {In progress} sort by: created"
        else query

        val report = WorkloadPageState(queryToUse, reportBuilder.gather(queryToUse))
        return ResponseEntity.ok(report);
    }
}