package com.xcart.prognosis.reports.projects

import com.xcart.prognosis.reports.Report
import com.xcart.prognosis.reports.ReportType

data class ProjectsReport (val groups: List<ProjectGroup>, val type: ReportType = ReportType.Projects) : Report {

}