package com.xcart.prognosis.services

import com.natpryce.konfig.*
import com.natpryce.konfig.ConfigurationProperties.Companion.systemProperties
import org.springframework.stereotype.Service

@Service
class Configuration {
    private val youtrackToken = Key("youtrack.token", stringType)

    private val instance = {
        systemProperties() overriding
                EnvironmentVariables() overriding
                ConfigurationProperties.fromOptionalResource("application.properties") overriding
                ConfigurationProperties.fromOptionalResource("application.defaults.properties")
    }()

    fun getYoutrackToken(): String {
        return instance[youtrackToken]
    }
}

private fun ConfigurationProperties.Companion.fromOptionalResource(resourceName: String) =
        if (ClassLoader.getSystemResource(resourceName) != null) fromResource(resourceName)
        else EmptyConfiguration