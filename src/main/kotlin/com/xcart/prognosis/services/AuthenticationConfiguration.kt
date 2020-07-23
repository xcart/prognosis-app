package com.xcart.prognosis.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class AuthenticationConfiguration @Autowired constructor(val config: com.xcart.prognosis.services.Configuration)
    : WebSecurityConfigurerAdapter() {

    val passwordEncoder = BCryptPasswordEncoder()

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
            .inMemoryAuthentication()
                .passwordEncoder(passwordEncoder)
                .withUser(config.getAuthUser())
                    .password(passwordEncoder.encode(config.getAuthPassword()))
                    .roles("USER")
    }

    override fun configure(web: WebSecurity) {
        web
            .ignoring()
            .antMatchers("/resources/**")
    }

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
                .antMatchers("/api").permitAll()
                .anyRequest().authenticated()
                .and()
            .httpBasic()
    }
}