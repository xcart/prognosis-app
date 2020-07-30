package com.xcart.prognosis.repositories

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.jackson.responseObject
import com.xcart.prognosis.domain.Issue
import com.xcart.prognosis.domain.User
import com.xcart.prognosis.services.Configuration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class YouTrack @Autowired constructor(config: Configuration) {
    private val baseUrl: String = "https://xcart.myjetbrains.com/youtrack"
    private val permToken: String = config.youtrackToken
    private val issueFields: String = "id,idReadable,created,isDraft,summary,customFields(id,name,value(id,isResolved,login,minutes,name,fullName,text))"
    private val userFields: String = "id,login,fullName,email,name,jabberAccount,online,avatarUrl,banned,tags(id,name,untagOnResolve,updateableBy(id,name),visibleFor(name,id),owner(id,login))"

    fun fetchIssues(query: String): List<Issue> {
        val params = listOf(
                "fields" to issueFields,
                "query" to query
        )
        var request = Fuel.get("$baseUrl/api/issues", params)
        return request.configure(permToken).processResult()
    }

    fun fetchUsers(): List<User> {
        val params = listOf(
                "fields" to userFields
        )
        var request = Fuel.get("$baseUrl/api/users", params)
        return request.configure(permToken).processResult()
    }

}

inline fun Request.configure(token: String): Request {
    return this
            .authentication()
            .bearer(token)
            .header(Headers.ACCEPT, "application/json")
            .header(Headers.CACHE_CONTROL, "no-cache")
            .header(Headers.CONTENT_TYPE, "application/json")
}

inline fun <reified T : Any> Request.processResult(): T {
    val mapper = ObjectMapper().registerKotlinModule()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    mapper.propertyNamingStrategy = PropertyNamingStrategy.LOWER_CAMEL_CASE
    val (request, response, result) = this.responseObject<T>(mapper)
    return result.get()
}