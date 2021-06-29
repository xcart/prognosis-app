package com.xcart.prognosis.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class IssueCustomField (
        val name: String = "",
        @JsonProperty("\$type") val type: String = "",
        val value: Any? = null
)