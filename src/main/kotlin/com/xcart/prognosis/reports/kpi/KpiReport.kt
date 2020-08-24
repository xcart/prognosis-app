package com.xcart.prognosis.reports.kpi

import com.xcart.prognosis.reports.Report
import com.xcart.prognosis.reports.ReportType
import java.time.LocalDate

data class KpiReport(val startDate: LocalDate, val endDate: LocalDate, val teams: List<TeamKpiStats>, val type: ReportType = ReportType.Kpi) : Report {
}