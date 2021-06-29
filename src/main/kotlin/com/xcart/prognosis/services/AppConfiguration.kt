package com.xcart.prognosis.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class AppConfiguration {
    @Value("\${xb.url}")
    lateinit var xbUrl: String

    @Value("\${xb.password}")
    lateinit var xbPassword: String

    @Value("\${youtrack.query.issues}")
    lateinit var queryIssues: String

    @Value("\${youtrack.query.projects}")
    lateinit var queryProjects: String

    @Value("\${youtrack.url}")
    lateinit var youtrackUrl: String

    @Value("\${youtrack.token}")
    lateinit var youtrackToken: String

    @Value("\${auth.require:true}")
    var authRequire: Boolean = true

    @Value("\${auth.domains}")
    lateinit var authorizedDomains: List<String>
}