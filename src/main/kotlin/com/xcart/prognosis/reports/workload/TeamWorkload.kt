package com.xcart.prognosis.reports.workload

import com.xcart.prognosis.domain.Team
import com.xcart.prognosis.reports.StatValue

data class TeamWorkload (val teamName: Team, val users: List<UserWorkload>, val stats: List<StatValue>)