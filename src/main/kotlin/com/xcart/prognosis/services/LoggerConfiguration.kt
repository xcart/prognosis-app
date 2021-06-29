package com.xcart.prognosis.services

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LoggerConfiguration {

    @Bean
    fun logger(): org.slf4j.Logger {
        return LoggerFactory.getLogger("application")
    }
}