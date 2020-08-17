package com.xcart.prognosis.reports.workload

import com.xcart.prognosis.reports.Report
import com.xcart.prognosis.reports.ReportType

data class WorkloadReport (val teams: List<TeamWorkload>, val duration: Number, val type: ReportType = ReportType.Workload) : Report