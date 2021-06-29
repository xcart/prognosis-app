package com.xcart.prognosis.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Component

@Component
class AuthenticationUserService @Autowired constructor(
    val config: AppConfiguration
) :
    OidcUserService() {

    override fun loadUser(userRequest: OidcUserRequest): OidcUser {
        val user = super.loadUser(userRequest)

        for (domain in config.authorizedDomains) {
            if (user.email.endsWith(domain)) {
                return user
            }
        }

        throw OAuth2AuthenticationException(
            OAuth2Error(
                "invalid_domain", "Not from Seller Labs Group domain",
                ""
            )
        );
    }
}