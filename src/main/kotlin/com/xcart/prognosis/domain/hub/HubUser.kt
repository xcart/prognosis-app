package com.xcart.prognosis.domain.hub

import com.xcart.prognosis.domain.Team

data class HubUser(
        val id: String = "",
        val login: String = "",
        val name: String = "",
        val profile: HubUserProfile? = null,
        val banned: Boolean = false,
        val groups: List<HubUserGroup> = emptyList()
) {

    val team = {
        when {
            groups.find { it.name.contains("Alive Team") } != null -> Team.Alive
            groups.find { it.name.contains("Joy Team") } != null -> Team.Joy
            groups.find { it.name.contains("Mccornic Team") } != null -> Team.Mccornic
            else -> Team.NoTeam
        }
    }()
}
