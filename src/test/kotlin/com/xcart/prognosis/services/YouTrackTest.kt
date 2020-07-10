package com.xcart.prognosis.services

import com.xcart.prognosis.repositories.YouTrack
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
internal class YouTrackTest @Autowired constructor(val youtrack: YouTrack) {

    @Test
    fun fetchIssues() {
        var issues = youtrack.fetchIssues("")
        var first = issues[0]
        var assignee = first.assignee
        var estimation = first.estimation
        assertNotNull(assignee)
        assertNotNull(estimation)
    }

    @Test
    fun fetchUsers() {
        var users = youtrack.fetchUsers()
        assertTrue(true)
    }
}