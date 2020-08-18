package com.xcart.prognosis.controllers

import com.xcart.prognosis.presentation.CommonPageState
import com.xcart.prognosis.reports.UsertasksReportBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/usertasks")
class UsertasksController @Autowired constructor(val reportBuilder: UsertasksReportBuilder) {

    @GetMapping("/{login}")
    fun getWorkloadPageState(@PathVariable login: String, @RequestParam query: String?): ResponseEntity<CommonPageState> {
        val queryToUse = if (query.isNullOrEmpty())
            "Project: WD State: Open, Waiting, {In progress}"
        else query

        val report = CommonPageState(queryToUse, reportBuilder.gather(login, queryToUse))
        return ResponseEntity.ok(report);
    }
}