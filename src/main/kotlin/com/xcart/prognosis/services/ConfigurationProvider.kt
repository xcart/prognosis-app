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
                ConfigurationProperties.fromResource("application.properties")
    }()

    fun getYoutrackToken(): String {
        return instance[youtrackToken]
    }
}