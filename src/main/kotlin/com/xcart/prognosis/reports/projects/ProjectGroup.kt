package com.xcart.prognosis.reports.projects

import com.xcart.prognosis.domain.User

data class ProjectGroup (val manager: User, val estimation: Int, val duration: Int, val projects: List<Project>, val spans: List<WorkloadSpan>)