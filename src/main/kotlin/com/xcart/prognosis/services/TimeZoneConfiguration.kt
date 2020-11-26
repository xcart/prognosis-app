package com.xcart.prognosis.services

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class TimeZoneConfiguration {
    @Bean
    fun timeZone(): TimeZone {
        // Temporary timezone fix
        val defaultTimeZone = TimeZone.getTimeZone("UTC")
        TimeZone.setDefault(defaultTimeZone)
        return defaultTimeZone
    }
}