package com.xcart.prognosis.reports.projects

import com.xcart.prognosis.domain.User

data class ProjectGroup (val manager: User, val projects: List<Project>)