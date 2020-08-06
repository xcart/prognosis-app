package com.xcart.prognosis.reports

import com.xcart.prognosis.domain.Issue
import com.xcart.prognosis.domain.Team
import com.xcart.prognosis.domain.User
import com.xcart.prognosis.repositories.YouTrack
import com.xcart.prognosis.logic.WorkloadAnalysis
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class WorkloadReportBuilder @Autowired constructor(val youTrack: YouTrack) {
    fun gather(query: String): WorkloadReport {
        val users = youTrack.fetchUsers().filter { !it.banned }
        val issues = youTrack.fetchIssues(query)
        val teams = getTeamsWorkload(users, issues)
        return WorkloadReport(teams)
    }

    fun getTeamsWorkload(users: List<User>, issues: List<Issue>): Map<String, TeamWorkload> {
        val teams = users.fold(mutableMapOf<Team, MutableList<User>>()) { acc, user ->
            if (acc[user.team] != null)
                acc[user.team]?.add(user)
            else acc[user.team] = mutableListOf(user)
            acc
        }

        return teams.entries.associateTo(mutableMapOf()) {
            it.key.name to TeamWorkload(it.key, getUsersWorkload(it.value, issues), emptyList())
        }
    }

    fun getUsersWorkload(users: List<User>, issues: List<Issue>): Map<String, UserWorkload> {
        val analysis = WorkloadAnalysis(issues)
        return users.fold(mutableMapOf()) { acc, user ->
            acc[user.login] = UserWorkload(user, analysis.getDailyWorkloadForUser(user, LocalDate.now()), emptyList())
            acc
        }
    }
}