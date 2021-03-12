package com.xcart.prognosis.domain

import com.xcart.prognosis.repositories.DayOff
import com.xcart.prognosis.services.ContextUtil
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Stream
import kotlin.streams.toList

object LocalDateExtensions {

    fun LocalDate.isBusinessDay(): Boolean {
        return !this.isHoliday() && !this.isWeekend()
    }

    fun LocalDate.isWeekend(): Boolean {
        return (this.dayOfWeek === DayOfWeek.SATURDAY || this.dayOfWeek === DayOfWeek.SUNDAY)
    }

    fun LocalDate.isHoliday(): Boolean {
        val dayOff = ContextUtil.getBean(DayOff::class.java)
        return dayOff.getHolidays().contains(this)
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
        if (this > endDate) {
            return emptyList()
        }
        return Stream.iterate(this, { date -> date.plusDays(1) })
                .limit(daysBetween + 1)
                .toList()
    }

    fun LocalDate.countDaysUntil(endDate: LocalDate): Int {
        return ChronoUnit.DAYS.between(this, endDate).toInt()
    }
}