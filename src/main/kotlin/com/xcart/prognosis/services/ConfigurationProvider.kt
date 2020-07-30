package com.xcart.prognosis.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class Configuration {

    @Value("\${youtrack.token}")
    lateinit var youtrackToken: String

    @Value("\${auth.user}")
    lateinit var authUser: String

    @Value("\${auth.password}")
    lateinit var authPassword: String
}