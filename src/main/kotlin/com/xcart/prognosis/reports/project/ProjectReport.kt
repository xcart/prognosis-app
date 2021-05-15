package com.xcart.prognosis.reports.project

import com.xcart.prognosis.domain.User
import com.xcart.prognosis.reports.Report
import com.xcart.prognosis.reports.ReportType
import com.xcart.prognosis.reports.workload.TaskWorkload

data class ProjectReport(
    val tasks: List<TaskWorkload>, val duration:
    Number, val manager: User?, val client: String, val type: ReportType =
        ReportType.Project
) : Report