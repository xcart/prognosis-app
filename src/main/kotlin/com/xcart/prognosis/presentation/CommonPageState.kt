package com.xcart.prognosis.presentation

import com.xcart.prognosis.reports.Report

data class CommonPageState(
    val query: String,
    val report: Report,
    val context: PageContext
)