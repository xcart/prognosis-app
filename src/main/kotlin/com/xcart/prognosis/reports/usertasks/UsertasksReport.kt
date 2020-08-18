package com.xcart.prognosis.reports.usertasks

import com.xcart.prognosis.reports.Report
import com.xcart.prognosis.reports.ReportType

data class UsertasksReport (val tasks: List<TaskWorkload>, val duration: Number, val login: String, val type: ReportType = ReportType.Usertasks) : Report