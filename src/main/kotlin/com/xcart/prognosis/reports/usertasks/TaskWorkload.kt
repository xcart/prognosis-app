package com.xcart.prognosis.reports.usertasks

import com.xcart.prognosis.domain.IssueInfo
import java.time.LocalDate

data class TaskWorkload (val swimlane: List<TaskDailyWorkloadItem>, val issue: IssueInfo, val startDate: LocalDate)