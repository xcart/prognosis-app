package com.xcart.prognosis.reports

import com.xcart.prognosis.repositories.YouTrack
import com.xcart.prognosis.services.WorkloadAnalysis
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class Workload @Autowired constructor (val youTrack: YouTrack) {
    fun gatherReport(): Map<String, ReportItem> {
        val users = youTrack.fetchUsers().filter { !it.banned }
        val issues = youTrack.fetchIssues("Project: WD State: Open, Waiting, {In progress} sort by: created")
        val analysis = WorkloadAnalysis(issues)
        return users.fold(mutableMapOf()) { acc, user ->
            acc[user.login] = ReportItem(user, analysis.getDailyWorkloadForUser(user, LocalDate.now()))
            acc
        }
    }
}