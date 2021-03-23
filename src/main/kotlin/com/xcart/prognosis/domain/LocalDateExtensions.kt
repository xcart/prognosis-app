package com.xcart.prognosis.domain

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Stream
import kotlin.streams.toList

object LocalDateExtensions {
    private val holidays = listOf(
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

    fun LocalDate.isBusinessDay(): Boolean {
        return !this.isHoliday() && !this.isWeekend()
    }

    fun LocalDate.isWeekend(): Boolean {
        return (this.dayOfWeek === DayOfWeek.SATURDAY || this.dayOfWeek === DayOfWeek.SUNDAY)
    }

    fun LocalDate.isHoliday(): Boolean {
        return holidays.contains(this)
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