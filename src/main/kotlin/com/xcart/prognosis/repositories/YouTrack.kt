package com.xcart.prognosis.repositories

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Parameters
import com.xcart.prognosis.domain.Issue
import com.xcart.prognosis.domain.User
import com.xcart.prognosis.errors.ExternalServiceError
import com.xcart.prognosis.services.Configuration
import com.xcart.prognosis.transport.configure
import com.xcart.prognosis.transport.processResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class YouTrack @Autowired constructor(config: Configuration) {
    private val baseUrl: String = config.youtrackUrl + "/youtrack/api"
    private val permToken: String = config.youtrackToken
    private val issueFields: String = "id,idReadable,created,isDraft,summary,customFields(id,name,value(id,isResolved,login,minutes,name,fullName,text)),reporter(id,login,fullName)"
    private val userFields: String = "id,login,fullName,email,name,jabberAccount,online,avatarUrl,banned,tags(id,name,untagOnResolve,updateableBy(id,name),visibleFor(name,id),owner(id,login))"

    fun fetchIssues(query: String): List<Issue> {
        return performRequest("/issues", listOf(
                "fields" to issueFields,
                "query" to query
        ))
    }

    fun fetchUsers(): List<User> {
        return performRequest("/users", listOf(
                "fields" to userFields,
                "\$top" to 100
        ))
    }

    private final inline fun <reified T : Any> performRequest(url: String, params: Parameters): T {
        val request = Fuel.get(baseUrl + url, params).configure(permToken)
        try {
            return request.processResult()
        } catch (ex: Exception) {
            throw ExternalServiceError("Error during communication with YouTrack API", ex)
        }
    }
}