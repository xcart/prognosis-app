package com.xcart.prognosis.repositories

import com.xcart.prognosis.domain.User
import com.xcart.prognosis.domain.VacationPeriod
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class DayOff @Autowired constructor(val api: XbApi) {

    fun getHolidays(): List<LocalDate> {
        return listOf(
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
    }

    fun getUserVacations(user: User): List<VacationPeriod> {
        return getAllVacations().filter {
            it.email == user.email || it.email == (user.login + "@x-cart.com")
        }
    }

    fun getAllVacations(): List<VacationPeriod> {
        return api.getVacationsInfo()
    }
}
