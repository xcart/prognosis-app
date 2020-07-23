package com.xcart.prognosis.services

import com.natpryce.konfig.*
import com.natpryce.konfig.ConfigurationProperties.Companion.systemProperties
import org.springframework.stereotype.Service

@Service
class Configuration {
    private val instance = {
        systemProperties() overriding
                EnvironmentVariables() overriding
                ConfigurationProperties.fromOptionalResource("user.properties") overriding
                ConfigurationProperties.fromOptionalResource("application.properties")
    }()

    fun getYoutrackToken(): String {
        return instance[Key("youtrack.token", stringType)]
    }

    fun getAuthUser(): String {
        return instance[Key("auth.user", stringType)]
    }

    fun getAuthPassword(): String {
        return instance[Key("auth.password", stringType)]
    }
}

private fun ConfigurationProperties.Companion.fromOptionalResource(resourceName: String) =
        if (ClassLoader.getSystemResource(resourceName) != null) fromResource(resourceName)
        else EmptyConfiguration