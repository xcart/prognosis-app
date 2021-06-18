package com.xcart.prognosis.presentation

import com.xcart.prognosis.reports.workload.TaskWorkload

data class RescheduledIssue (
    val id: String,
    val data: TaskWorkload?,
    val errorMessage: String? = ""
)