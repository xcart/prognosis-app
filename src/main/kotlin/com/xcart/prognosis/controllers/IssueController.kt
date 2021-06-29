package com.xcart.prognosis.controllers

import com.xcart.prognosis.presentation.RescheduledIssue
import com.xcart.prognosis.reports.RescheduleHelper
import com.xcart.prognosis.services.AppConfiguration
import com.xcart.prognosis.services.AuthenticationFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/issue")
class IssueController @Autowired constructor(
    val rescheduleHelper: RescheduleHelper,
    val authentication: AuthenticationFacade,
    val config: AppConfiguration
) {
    @GetMapping("/{issueId}/reschedule")
    fun getRescheduledIssueState(
        @PathVariable issueId: String,
        @RequestParam(name = "shift_amount") shiftAmount: String
    ): ResponseEntity<RescheduledIssue> {
        val workload = rescheduleHelper.rescheduleIssue(
            issueId,
            shiftAmount = shiftAmount.toInt()
        )

        val issue = RescheduledIssue(issueId, shiftAmount.toInt(), workload)
        return ResponseEntity.ok(issue)
    }

    @PostMapping("/persist")
    fun persistRescheduling(
        @RequestBody data: List<RescheduledIssue>
    ): ResponseEntity<Boolean> {
        if (!authentication.canChangeIssues()) {
            return ResponseEntity.status(403).body(false)
        }

        for (issue in data) {
            if (issue.data === null) {
                return ResponseEntity.badRequest().body(false)
            }

            try {
                rescheduleHelper.persistChange(
                    issue.id,
                    issue.data.startDate,
                    issue.data.verificationDate,
                    issue.data.endDate
                )
            } catch (e: Exception) {
                return ResponseEntity.status(500).body(false)
            }
        }

        return ResponseEntity.ok(true)
    }
}