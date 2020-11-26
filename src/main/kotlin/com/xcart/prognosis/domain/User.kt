package com.xcart.prognosis.domain

import com.xcart.prognosis.domain.hub.HubUser
import java.util.*

data class User(
        val id: String = "",
        val login: String = "",
        val fullName: String = "",
        val email: String? = null,
        val avatarUrl: String? = null,
        val banned: Boolean = false,
        val _team: Team? = null
) {
    constructor(data: HashMap<*, *>) : this(
            id = data["id"] as String,
            login = data["login"] as String,
            fullName = data["fullName"] as String
    )

    constructor(user: HubUser) : this(
            id = user.id,
            login = user.login,
            fullName = user.name,
            email = user.profile?.email?.email,
            avatarUrl = user.profile?.avatar?.url,
            banned = user.banned,
            _team = user.team
    )

    val team = {
        _team ?: Team.NoTeam
    }()
}
