package com.xcart.prognosis.repositories

import com.xcart.prognosis.domain.User
import com.xcart.prognosis.domain.VacationPeriod
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class DayOff @Autowired constructor(val api: XbApi) {

    fun getUserVacations(user: User): List<VacationPeriod> {
        return getAllVacations().filter {
            it.email == user.email || it.email == (user.login + "@x-cart.com")
        }
    }

    fun getAllVacations(): List<VacationPeriod> {
        return api.getVacationsInfo()
    }
}
