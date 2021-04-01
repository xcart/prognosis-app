package com.xcart.prognosis.services

import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component

interface IAuthenticationFacade {
    fun getAuthentication(): Authentication;
}

@Component
class AuthenticationFacade : IAuthenticationFacade {
    override fun getAuthentication(): Authentication {
        return SecurityContextHolder.getContext().authentication;
    }

    fun getUser(): OAuth2User? {
        val authentication = getAuthentication()

        if (authentication !is AnonymousAuthenticationToken) {
            return (authentication.principal as OAuth2User)
        }

        return null
    }

    fun getUsername(): String {
        val user = getUser()
        return if (user != null) user.name else "Unknown"
    }
}