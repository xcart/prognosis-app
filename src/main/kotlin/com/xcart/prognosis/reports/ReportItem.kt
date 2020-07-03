package com.xcart.prognosis.reports

import com.xcart.prognosis.domain.User

data class ReportItem (val user: User, val items: List<WorkloadItem>)