package com.xcart.prognosis.repositories

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.Parameters
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.interceptors.LogResponseInterceptor
import com.github.kittinunf.fuel.jackson.responseObject
import com.github.kittinunf.result.Result
import com.xcart.prognosis.domain.Issue
import com.xcart.prognosis.domain.User
import com.xcart.prognosis.errors.YouTrackError
import com.xcart.prognosis.services.Configuration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class YouTrack @Autowired constructor(config: Configuration) {
    private val baseUrl: String = "https://xcart.myjetbrains.com/youtrack"
    private val permToken: String = config.youtrackToken
    private val issueFields: String = "id,idReadable,created,isDraft,summary,customFields(id,name,value(id,isResolved,login,minutes,name,fullName,text)),reporter(id,login,fullName)"
    private val userFields: String = "id,login,fullName,email,name,jabberAccount,online,avatarUrl,banned,tags(id,name,untagOnResolve,updateableBy(id,name),visibleFor(name,id),owner(id,login))"

//    init {
//        FuelManager.instance.addResponseInterceptor(LogResponseInterceptor)
//    }

    fun fetchIssues(query: String): List<Issue> {
        return performRequest("/api/issues", listOf(
                "fields" to issueFields,
                "query" to query
        ))
    }

    fun fetchUsers(): List<User> {
        return performRequest("/api/users", listOf(
                "fields" to userFields,
                "\$top" to 100
        ))
    }

    private final inline fun <reified T : Any> performRequest(url: String, params: Parameters): T {
        var request = Fuel.get(baseUrl + url, params).configure(permToken)
        try {
            return request.processResult()
        } catch (ex: Exception) {
            throw YouTrackError("Error during communication with YouTrack API", ex)
        }
    }

}

fun Request.configure(token: String): Request {
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
    when (result) {
        is Result.Failure -> {
            throw result.getException()
        }
        else -> return result.get()
    }
}