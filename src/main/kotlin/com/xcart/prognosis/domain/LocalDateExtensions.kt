package com.xcart.prognosis.domain

import com.xcart.prognosis.repositories.DayOff
import com.xcart.prognosis.services.ContextUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Stream
import kotlin.streams.toList

object LocalDateExtensions {

    fun LocalDate.isBusinessDay(): Boolean {
        val dayOff = ContextUtil.getBean(DayOff::class.java)
        val isHoliday = { date: LocalDate -> dayOff.getHolidays().contains(date) }
        val isWeekend = { date: LocalDate ->
            (date.dayOfWeek === DayOfWeek.SATURDAY
                    || date.dayOfWeek === DayOfWeek.SUNDAY)
        }

        return !isHoliday(this) && !isWeekend(this)
    }

    fun LocalDate.isVacationDay(vacations: List<VacationPeriod>): Boolean {
        return vacations.any {
            this.isEqual(it.startDate)
                    || this.isEqual(it.endDate)
                    || (this.isAfter(it.startDate) && this.isBefore(it.endDate))
        }
    }

    fun LocalDate.listDaysUntil(endDate: LocalDate): List<LocalDate> {
        val daysBetween = ChronoUnit.DAYS.between(this, endDate)
        return Stream.iterate(this, { date -> date.plusDays(1) })
                .limit(daysBetween + 1)
                .toList()
    }

    fun LocalDate.countDaysUntil(endDate: LocalDate): Int {
        return ChronoUnit.DAYS.between(this, endDate).toInt()
    }
}