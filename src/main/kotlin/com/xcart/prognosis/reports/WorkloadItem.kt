package com.xcart.prognosis.reports

import com.xcart.prognosis.domain.IssueInfo
import java.time.LocalDate

data class WorkloadItem (val date: LocalDate, val workload: Float, val issues: List<IssueInfo>)