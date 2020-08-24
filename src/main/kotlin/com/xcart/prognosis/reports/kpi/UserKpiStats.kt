package com.xcart.prognosis.reports.kpi

import com.xcart.prognosis.domain.User
import com.xcart.prognosis.reports.StatValue

data class UserKpiStats(val user: User, val stats: List<StatValue>) {
}