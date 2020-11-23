package com.xcart.prognosis.domain

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer
import java.sql.Timestamp
import java.time.LocalDate
import java.util.concurrent.TimeUnit

data class VacationPeriod(
        val email: String,
        val status: VacationStatus,
        @JsonDeserialize(using = TimestampToDate::class) @JsonProperty("start_period") val startPeriod: LocalDate,
        @JsonDeserialize(using = TimestampToDate::class) @JsonProperty("end_period") val endPeriod: LocalDate
)

enum class VacationStatus(@JsonValue val code: String) {
    Paid("7"), Nonpaid("8"), MaternityLeave("12")
}

internal class TimestampToDate : StdScalarDeserializer<LocalDate>(LocalDate::class.java) {
    override fun deserialize(parser: JsonParser, context: DeserializationContext): LocalDate {
        if (parser.hasToken(JsonToken.VALUE_STRING)) {
            val string = parser.text.trim()
            val long = TimeUnit.MILLISECONDS.convert(string.toLong(), TimeUnit.SECONDS)
            val timestamp = Timestamp(long)
            return timestamp.toLocalDateTime().toLocalDate()
        }

        return LocalDate.MIN
    }
}