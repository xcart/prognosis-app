package com.xcart.prognosis.repositories

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.xcart.prognosis.domain.User
import com.xcart.prognosis.domain.VacationInfoRef
import com.xcart.prognosis.domain.VacationPeriod
import com.xcart.prognosis.errors.ExternalServiceError
import com.xcart.prognosis.services.Configuration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class DayOff @Autowired constructor(config: Configuration) {
    val baseUrl = config.xbUrl
    val xbPassword = config.xbPassword

    fun getHolidays(): List<LocalDate> {
        return listOf(
                LocalDate.of(2020, 12, 31),
                LocalDate.of(2021, 1, 1),
                LocalDate.of(2021, 1, 2),
                LocalDate.of(2021, 1, 3),
                LocalDate.of(2021, 1, 7),
                LocalDate.of(2021, 2, 23),
                LocalDate.of(2021, 3, 8),
                LocalDate.of(2021, 7, 1),
                LocalDate.of(2021, 11, 4),
                LocalDate.of(2021, 12, 31)
        )
    }

    fun getUserVacations(user: User): List<VacationPeriod> {
        return emptyList()
    }

    fun getAllVacations(): List<VacationPeriod> {
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

    fun deserializeVacations(json: String): List<VacationPeriod> {
        val mapper = ObjectMapper().registerKotlinModule()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        val result = mapper.readValue(json, VacationInfoRef())
        return result.map { it.value }
    }
}
