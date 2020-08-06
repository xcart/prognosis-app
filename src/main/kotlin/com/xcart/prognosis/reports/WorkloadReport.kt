package com.xcart.prognosis.reports

import com.xcart.prognosis.repositories.YouTrack
import com.xcart.prognosis.logic.WorkloadAnalysis
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class WorkloadReport @Autowired constructor (val youTrack: YouTrack) {
    fun gatherReport(query: String): Map<String, ReportItem> {
        val users = youTrack.fetchUsers().filter { !it.banned }
        val issues = youTrack.fetchIssues(query)
        val analysis = WorkloadAnalysis(issues)
        return users.fold(mutableMapOf()) { acc, user ->
            acc[user.login] = ReportItem(user, analysis.getDailyWorkloadForUser(user, LocalDate.now()))
            acc
        }
    }
}