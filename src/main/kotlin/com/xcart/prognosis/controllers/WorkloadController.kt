package com.xcart.prognosis.controllers

import com.xcart.prognosis.presentation.WorkloadPageState
import com.xcart.prognosis.reports.Workload
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/workload")
class WorkloadController @Autowired constructor(val report: Workload) {

    @GetMapping("/")
    fun getWorkloadReport(@RequestParam query: String?): ResponseEntity<WorkloadPageState> {
        return try {
            val report = WorkloadPageState(report.gatherReport())
            ResponseEntity.ok(report);
        } catch (e: Exception) {
            ResponseEntity.status(500).build()
        }
    }
}