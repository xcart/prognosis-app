package com.xcart.prognosis.reports.kpi

import com.xcart.prognosis.domain.Team
import com.xcart.prognosis.reports.StatValue

data class TeamKpiStats(val team: Team, val users: List<UserKpiStats>, val stats: List<StatValue>) {
}