package com.xcart.prognosis.repositories

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
internal class DayOffTest @Autowired constructor(val dayOff: DayOff) {

    @Test
    fun getHolidays() {
        val expected = listOf(
                LocalDate.of(2020, 12, 31),
                LocalDate.of(2021, 1, 1),
                LocalDate.of(2021, 1, 2),
                LocalDate.of(2021, 1, 3),
                LocalDate.of(2021, 1, 7),
                LocalDate.of(2021, 1, 8),
                LocalDate.of(2021, 2, 20),
                LocalDate.of(2021, 2, 23),
                LocalDate.of(2021, 3, 8),
                LocalDate.of(2021, 5, 3),
                LocalDate.of(2021, 5, 10),
                LocalDate.of(2021, 6, 14),
                LocalDate.of(2021, 11, 4),
                LocalDate.of(2021, 12, 31)
        )

        assertEquals(expected, dayOff.getHolidays())
    }
}