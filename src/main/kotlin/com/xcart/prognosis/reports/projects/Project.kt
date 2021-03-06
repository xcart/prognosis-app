package com.xcart.prognosis.reports.projects

import com.xcart.prognosis.domain.IssueInfo
import com.xcart.prognosis.domain.User
import java.time.LocalDate

data class Project(val client: String,
                   val team: List<User>,
                   val tasks: List<IssueInfo>,
                   val estimation: Int,
                   val duration: Int?,
                   val offset: Int,
                   val manager: User,
                   val startDate: LocalDate? = null,
                   val endDate: LocalDate? = null)