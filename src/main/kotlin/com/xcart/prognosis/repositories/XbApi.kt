package com.xcart.prognosis.repositories

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.xcart.prognosis.domain.VacationInfo
import com.xcart.prognosis.domain.VacationPeriod
import com.xcart.prognosis.errors.ExternalServiceError
import com.xcart.prognosis.services.Configuration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*

@Service
class XbApi @Autowired constructor(config: Configuration) {
    private val baseUrl = config.xbUrl
    private val xbPassword = config.xbPassword

    @Cacheable("getVacationsInfo")
    fun getVacationsInfo(): List<VacationPeriod> {
        val params = listOf(
                "target" to "vacations_info",
                "password" to xbPassword
        )
        try {
            val (_, _, result) = Fuel.get(baseUrl, params).responseString()
            return when (result) {
                is Result.Failure -> throw result.getException()
                else -> deserializeVacations(result.get())
            }
        } catch (ex: Exception) {
            throw ExternalServiceError("Error during communication with XB API", ex)
        }
    }

    @CacheEvict("getVacationsInfo")
    @Scheduled(fixedDelay = 3600000)
    fun cacheEvict() {}

    fun deserializeVacations(json: String): List<VacationPeriod> {
        // Fix for XB api inconsistency
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+4:00"))
        val mapper = ObjectMapper().registerKotlinModule()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        val result = mapper.readValue(json, VacationInfo::class.java)
        // Return TimeZone back
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        return result.users
    }
}