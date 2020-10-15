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

    val team: Team
        get() {
            return when (login) {
                "alive" -> Team.Alive
                "aster" -> Team.Alive
                "phill" -> Team.Alive
                "savage" -> Team.Alive
                "tito" -> Team.Alive
                "joy" -> Team.Joy
                "anakonda" -> Team.Joy
                "mixon" -> Team.Joy
                "lehach" -> Team.Joy
                "albert" -> Team.Mccornic
                "tekton" -> Team.Mccornic
                "sarta" -> Team.Mccornic
                "nogard" -> Team.Mccornic
                "vjk" -> Team.Mccornic
                "mccornic" -> Team.Mccornic
                "luke" -> Team.Mccornic
                else -> Team.NoTeam
            }
        }
}
