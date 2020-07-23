package com.xcart.prognosis.services

import com.xcart.prognosis.repositories.YouTrack
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class YouTrackTest @Autowired constructor(val youtrack: YouTrack) {

    @Test
    fun fetchIssues() {
    }

    @Test
    fun fetchUsers() {
    }
}