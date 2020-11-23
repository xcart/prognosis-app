package com.xcart.prognosis.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class Configuration {
    @Value("\${xb.url}")
    lateinit var xbUrl: String

    @Value("\${xb.password}")
    lateinit var xbPassword: String

    @Value("\${youtrack.url}")
    lateinit var youtrackUrl: String

    @Value("\${youtrack.token}")
    lateinit var youtrackToken: String

    @Value("\${auth.user}")
    lateinit var authUser: String

    @Value("\${auth.password}")
    lateinit var authPassword: String
}