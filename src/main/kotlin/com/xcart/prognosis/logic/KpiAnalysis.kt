package com.xcart.prognosis.logic

import com.xcart.prognosis.domain.Issue
import com.xcart.prognosis.domain.User
import com.xcart.prognosis.reports.StatValue
import com.xcart.prognosis.reports.StatValueKey

class KpiAnalysis(private val issues: List<Issue>) {
    fun getUserStats(user: User) : List<StatValue> {
        return listOf<StatValue>(
            StatValue(StatValueKey.HoursWorkingOn, getHoursWorkingOn(user), "Working on", 1, "h"),
            StatValue(StatValueKey.HoursInQa, getHoursInQa(user), "Working on", 1, "h"),
            StatValue(StatValueKey.HoursNonClosed, getHoursNonClosed(user), "Working on", 1, "h"),
            StatValue(StatValueKey.QuarterTarget, getQuarterTarget(user), "Working on", 1, "h"),
            StatValue(StatValueKey.QuarterCompleted, getQuarterCompleted(user), "Working on", 1, "h"),
            StatValue(StatValueKey.PerformanceIndex, getPerformanceIndex(user), "Working on", 1, "%")
        )
    }

    private fun getHoursWorkingOn(user: User): Float {
    }

    private fun getHoursInQa(user: User): Float {

    }

    private fun getHoursNonClosed(user: User): Float {

    }

    private fun getQuarterTarget(user: User): Float {

    }

    private fun getQuarterCompleted(user: User): Float {

    }

    private fun getPerformanceIndex(user: User): Float {

    }
}