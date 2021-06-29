package com.xcart.prognosis.repositories

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Parameters
import com.github.kittinunf.fuel.jackson.objectBody
import com.xcart.prognosis.domain.Issue
import com.xcart.prognosis.domain.IssueCustomField
import com.xcart.prognosis.domain.UpdateIssueFieldsRequestBody
import com.xcart.prognosis.domain.User
import com.xcart.prognosis.errors.ExternalServiceError
import com.xcart.prognosis.services.AppConfiguration
import com.xcart.prognosis.transport.configure
import com.xcart.prognosis.transport.processResult
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class YouTrack @Autowired constructor(config: AppConfiguration, val logger:
Logger) {
    private val baseUrl: String = config.youtrackUrl + "/youtrack/api"
    private val permToken: String = config.youtrackToken
    private val issueFields: String = "id,idReadable,created,isDraft,summary," +
        "customFields(id,name,value(id,isResolved,login,minutes,name," +
        "fullName,text,avatarUrl)),reporter(id,login,fullName)"
    private val userFields: String =
        "id,login,fullName,email,name,jabberAccount,online,avatarUrl,banned,tags(id,name,untagOnResolve,updateableBy(id,name),visibleFor(name,id),owner(id,login))"

    fun fetchIssues(query: String): List<Issue> {
        return performRequest(
            "/issues", listOf(
                "fields" to issueFields,
                "query" to query
            )
        )
    }

    fun fetchIssue(issueId: String): Issue {
        return performRequest(
            "/issues/$issueId", listOf(
                "fields" to issueFields
            )
        )
    }

    fun updateIssueFields(issueId: String, fields: List<IssueCustomField>): Issue {
        return performRequest(
            url = "/issues/$issueId",
            params = listOf(
                "fields" to issueFields
            ),
            method = Method.POST,
            body = UpdateIssueFieldsRequestBody(fields)
        )
    }

    fun fetchUsers(): List<User> {
        return performRequest(
            "/users", listOf(
                "fields" to userFields,
                "\$top" to 100
            )
        )
    }

    private inline fun <reified T : Any> performRequest(
        url: String,
        params: Parameters,
        method: Method = Method.GET,
        body: Any? = null
    ): T {
        val request = Fuel.request(method, baseUrl + url, params).configure(permToken)
        if (body !== null) {
            request.objectBody(body)
        }

        try {
            return request.processResult()
        } catch (ex: Exception) {
            throw ExternalServiceError(
                "Error during communication with YouTrack API: " + ex.message,
                ex
            )
        }
    }
}