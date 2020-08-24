package com.xcart.prognosis.reports

import com.xcart.prognosis.domain.Issue
import com.xcart.prognosis.domain.Team
import com.xcart.prognosis.domain.User
import com.xcart.prognosis.logic.KpiAnalysis
import com.xcart.prognosis.reports.kpi.KpiReport
import com.xcart.prognosis.reports.kpi.TeamKpiStats
import com.xcart.prognosis.reports.kpi.UserKpiStats
import com.xcart.prognosis.repositories.YouTrack
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class KpiReportBuilder @Autowired constructor(val youTrack: YouTrack) {
    fun gather(startDate: LocalDate, endDate: LocalDate): KpiReport {
        val query = ""
        val users = youTrack.fetchUsers().filter { !it.banned }
        val issues = youTrack.fetchIssues(query)
        val teams = getTeamsKpi(users, issues)

        return KpiReport(startDate, endDate, teams)
    }

    fun getTeamsKpi(users: List<User>, issues: List<Issue>): List<TeamKpiStats> {
        val teams = users.fold(mutableMapOf<Team, MutableList<User>>()) { acc, user ->
            if (acc[user.team] != null)
                acc[user.team]?.add(user)
            else acc[user.team] = mutableListOf(user)
            acc
        }

        return teams.map {
            val skip = it.key == Team.NoTeam
            val teamStats = emptyList<StatValue>()
            TeamKpiStats(it.key, getUsersKpi(it.value, issues, skip), teamStats)
        }.sortedBy { it.team }
    }

    fun getUsersKpi(users: List<User>, issues: List<Issue>, skip: Boolean): List<UserKpiStats> {
        val analysis = KpiAnalysis(issues)
        return users.fold(mutableListOf()) { acc, user ->
            if (!skip) {
                acc.add(UserKpiStats(user, analysis.getUserStats(user)))
            }
            acc
        }
    }
}