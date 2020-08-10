package com.xcart.prognosis.reports

import com.xcart.prognosis.domain.Issue
import com.xcart.prognosis.domain.Team
import com.xcart.prognosis.domain.User
import com.xcart.prognosis.logic.WorkloadAnalysis
import com.xcart.prognosis.repositories.YouTrack
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class WorkloadReportBuilder @Autowired constructor(val youTrack: YouTrack) {
    fun gather(query: String): WorkloadReport {
        val users = youTrack.fetchUsers().filter { !it.banned }
        val issues = youTrack.fetchIssues(query)
        val teams = getTeamsWorkload(users, issues)
        val duration = getReportDuration(teams)
        return WorkloadReport(teams, duration)
    }

    fun getTeamsWorkload(users: List<User>, issues: List<Issue>): List<TeamWorkload> {
        val teams = users.fold(mutableMapOf<Team, MutableList<User>>()) { acc, user ->
            if (acc[user.team] != null)
                acc[user.team]?.add(user)
            else acc[user.team] = mutableListOf(user)
            acc
        }

        return teams.map {
            val skipEmpty = it.key == Team.NoTeam
            TeamWorkload(it.key, getUsersWorkload(it.value, issues, skipEmpty), emptyList())
        }.sortedBy { it.teamName }
    }

    fun getUsersWorkload(users: List<User>, issues: List<Issue>, skipEmpty: Boolean): List<UserWorkload> {
        val analysis = WorkloadAnalysis(issues)
        return users.fold(mutableListOf()) { acc, user ->
            val swimlane = analysis.getDailyWorkloadForUser(user, LocalDate.now())
            val stats = listOf(
                StatValue(StatValueKey.SwimlaneDuration, swimlane.size)
            )
            if (swimlane.isNotEmpty() || !skipEmpty) {
                acc.add(UserWorkload(user, swimlane, stats))
            }
            acc
        }
    }

    fun getReportDuration(teams: List<TeamWorkload>): Number {
        return teams.fold (0) { max, team ->
            val duration = team.users.fold (0) { max, user ->
                val stat = user.stats.find { stat -> stat.key == StatValueKey.SwimlaneDuration }
                if (stat != null && (stat.value as Int) > max) stat.value else max
            }
            if (duration > max) duration else max
        }
    }
}