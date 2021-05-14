package com.xcart.prognosis.repositories

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Parameters
import com.xcart.prognosis.domain.hub.HubUser
import com.xcart.prognosis.domain.hub.UsersPage
import com.xcart.prognosis.errors.ExternalServiceError
import com.xcart.prognosis.services.Configuration
import com.xcart.prognosis.transport.configure
import com.xcart.prognosis.transport.processResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Repository

@Repository
class YouTrackHub @Autowired constructor(config: Configuration) {
    private val baseUrl: String = config.youtrackUrl + "/hub/api/rest"
    private val permToken: String = config.youtrackToken
    private val userFields: String = "id,login,name,profile(avatar(url),email(email)),banned,groups(id,name)"

    @Cacheable("getUsersFromYoutrackHub")
    fun fetchUsers(): List<HubUser> {
        return fetchUserPage().users
    }

    fun fetchUser(login: String): HubUser? {
        val list = fetchUserPage("login: $login").users
        if (list.isEmpty()) {
            return null
        }
        return list.first()
    }

    fun fetchUserPage(query: String = "not is: banned"): UsersPage {
        return performRequest("/users", listOf(
                "fields" to userFields,
                "query" to query
        ))
    }

    @CacheEvict("getUsersFromYoutrackHub")
    @Scheduled(fixedDelay = 300000)
    fun cacheEvict() {}

    private final inline fun <reified T : Any> performRequest(url: String, params: Parameters): T {
        val request = Fuel.get(baseUrl + url, params).configure(permToken)
        try {
            return request.processResult()
        } catch (ex: Exception) {
            throw ExternalServiceError("Error during communication with YouTrack Hub API", ex)
        }
    }

}