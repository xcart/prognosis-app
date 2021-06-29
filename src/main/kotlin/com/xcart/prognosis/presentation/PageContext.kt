package com.xcart.prognosis.presentation

data class PageContext(
    val username: String, val youtrackUrl: String, val
    canChangeIssues: Boolean = false
)
