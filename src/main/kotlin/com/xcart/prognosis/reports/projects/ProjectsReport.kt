package com.xcart.prognosis.reports.projects

import com.xcart.prognosis.reports.Report
import com.xcart.prognosis.reports.ReportType

data class ProjectsReport (val projects: List<Project>, val type: ReportType = ReportType.Usertasks) : Report {

}