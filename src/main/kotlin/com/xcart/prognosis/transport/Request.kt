package com.xcart.prognosis.transport

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.jackson.responseObject
import com.github.kittinunf.result.Result

fun Request.configure(token: String): Request {
    return this
            .authentication()
            .bearer(token)
            .header(Headers.ACCEPT, "application/json")
            .header(Headers.CACHE_CONTROL, "no-cache")
            .header(Headers.CONTENT_TYPE, "application/json")
}

inline fun <reified T : Any> Request.processResult(): T {
    val mapper = ObjectMapper().registerKotlinModule()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    mapper.propertyNamingStrategy = PropertyNamingStrategy.LOWER_CAMEL_CASE
    val (request, response, result) = this.responseObject<T>(mapper)
    when (result) {
        is Result.Failure -> {
            throw result.getException()
        }
        else -> return result.get()
    }
}