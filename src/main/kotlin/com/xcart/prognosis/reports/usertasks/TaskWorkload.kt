package com.xcart.prognosis.reports.usertasks

import com.xcart.prognosis.domain.IssueInfo

data class TaskWorkload (val swimlane: List<TaskDailyWorkloadItem>, val issue: IssueInfo)