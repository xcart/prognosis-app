package com.xcart.prognosis.services

import com.xcart.prognosis.repositories.YouTrack
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class YouTrackTest {

    @Test
    fun fetchIssues() {
        var issues = YouTrack().fetchIssues("")
        var first = issues[0]
        var assignee = first.assignee
        var estimation = first.estimation
        assertNotNull(assignee)
        assertNotNull(estimation)
    }

    @Test
    fun fetchUsers() {
        var users = YouTrack().fetchUsers()
        assertTrue(true)
    }
}