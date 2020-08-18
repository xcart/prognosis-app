package com.xcart.prognosis.reports.workload

import com.xcart.prognosis.domain.User
import com.xcart.prognosis.reports.StatValue

data class UserWorkload (val user: User, val swimlane: List<DailyWorkloadItem>, val stats: List<StatValue>)