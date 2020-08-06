package com.xcart.prognosis.reports

import com.xcart.prognosis.domain.Team

data class TeamWorkload (val team: Team, val users: Map<String, UserWorkload>, val stats: List<StatValue>)