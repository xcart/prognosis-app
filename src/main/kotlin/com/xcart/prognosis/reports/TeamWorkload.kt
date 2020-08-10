package com.xcart.prognosis.reports

import com.xcart.prognosis.domain.Team

data class TeamWorkload (val teamName: Team, val users: List<UserWorkload>, val stats: List<StatValue>)