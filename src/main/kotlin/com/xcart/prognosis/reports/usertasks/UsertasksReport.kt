package com.xcart.prognosis.reports.usertasks

import com.xcart.prognosis.domain.User
import com.xcart.prognosis.reports.Report
import com.xcart.prognosis.reports.ReportType
import com.xcart.prognosis.reports.workload.TaskWorkload

data class UsertasksReport (val tasks: List<TaskWorkload>, val duration: Number, val user: User?, val login: String, val type: ReportType = ReportType.Usertasks) : Report