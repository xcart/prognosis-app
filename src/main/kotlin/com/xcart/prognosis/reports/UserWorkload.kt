package com.xcart.prognosis.reports

import com.xcart.prognosis.domain.User

data class UserWorkload (val user: User, val swimlane: List<DailyWorkloadItem>, val stats: List<StatValue>)