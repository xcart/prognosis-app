package com.xcart.prognosis.domain.hub

import com.xcart.prognosis.domain.Team
import com.xcart.prognosis.domain.VacationPeriod
import java.util.HashMap

data class HubUser(
        val id: String = "",
        val login: String = "",
        val name: String = "",
        val profile: HubUserProfile? = null,
        val banned: Boolean = false,
        val groups: List<HubUserGroup> = emptyList()
) {
    constructor(data: HashMap<*, *>) : this(
            id = data["id"] as String,
            login = data["login"] as String,
            name = data["fullName"] as String
    )

    val team: Team
        get() {
            return when {
                groups.find { it.name.contains("Alive Team") } != null -> Team.Alive
                groups.find { it.name.contains("Joy Team") } != null -> Team.Joy
                groups.find { it.name.contains("Mccornic Team") } != null -> Team.Mccornic
                else -> Team.NoTeam
            }
        }
}
