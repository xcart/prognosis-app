package com.xcart.prognosis.repositories

import com.xcart.prognosis.domain.VacationPeriod
import com.xcart.prognosis.domain.VacationStatus
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
internal class XbApiTest @Autowired constructor(val xbApi: XbApi) {

    @Test
    fun deserializeVacations() {
        val json = """
{
  "150242": {
    "email": "anakonda@x-cart.com",
    "status": "7",
    "start_period": "1605470400",
    "end_period": "1605902399"
  },
  "150248": {
    "email": "savage@x-cart.com",
    "status": "8",
    "start_period": "1606075200",
    "end_period": "1606247999"
  },
  "150249": {
    "email": "savage@x-cart.com",
    "status": "8",
    "start_period": "1606852800",
    "end_period": "1607025599"
  },
  "150250": {
    "email": "savage@x-cart.com",
    "status": "8",
    "start_period": "1607457600",
    "end_period": "1607543999"
  }
}
""".trimIndent()
        val actual = xbApi.deserializeVacations(json)
        val expected = listOf(
                VacationPeriod("anakonda@x-cart.com", VacationStatus.Paid, LocalDate.of(2020,11,16), LocalDate.of(2020,11,20)),
                VacationPeriod("savage@x-cart.com", VacationStatus.Nonpaid, LocalDate.of(2020,11,23), LocalDate.of(2020,11,24)),
                VacationPeriod("savage@x-cart.com", VacationStatus.Nonpaid, LocalDate.of(2020,12,2), LocalDate.of(2020,12,3)),
                VacationPeriod("savage@x-cart.com", VacationStatus.Nonpaid, LocalDate.of(2020,12,9), LocalDate.of(2020,12,9))
        )
        assertEquals(expected, actual)
    }
}