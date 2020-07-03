package com.xcart.prognosis.domain

import java.util.HashMap

data class User(
        val id: String = "",
        val login: String = "",
        val fullName: String = "",
        val email: String? = null,
        val banned: Boolean = false
) {
    constructor(data: HashMap<*, *>) : this(
            id = data["id"] as String,
            login = data["login"] as String,
            fullName = data["fullName"] as String
    )
}