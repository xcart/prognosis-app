package com.xcart.prognosis.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class AuthenticationConfiguration @Autowired constructor(
    val userService: AuthenticationUserService,
    val configuration: com.xcart.prognosis.services.Configuration
) :
    WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
            .inMemoryAuthentication()
    }

    override fun configure(web: WebSecurity) {
        web
            .ignoring()
            .antMatchers("/resources/**")
    }

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests {
                if (configuration.authRequire)
                    it.anyRequest().authenticated()
                else it.anyRequest().permitAll()
            }
            .exceptionHandling {
                it.defaultAuthenticationEntryPointFor(
                    HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                    AntPathRequestMatcher("/api/**")
                )
            }
            .oauth2Login { configurer ->
                configurer.userInfoEndpoint {
                    it.oidcUserService(userService)
                }
            }
    }
}