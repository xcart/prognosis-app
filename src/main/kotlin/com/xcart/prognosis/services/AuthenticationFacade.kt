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

    fun canChangeIssues(): Boolean {
        return when (getUserEmail()) {
            "daemos@x-cart.com",
            "xmir@x-cart.com",
            "alive@x-cart.com",
            "sarta@x-cart.com",
            "joy@x-cart.com",
            "mccornic@x-cart.com" -> true
            else -> false
        }
    }

    fun getUserEmail(): String? {
        return getUser()?.name
    }

    fun getUsername(): String {
        return getUserEmail() ?: "Unknown"
    }
}