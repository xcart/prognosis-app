package com.xcart.prognosis.controllers

import com.xcart.prognosis.presentation.CommonPageState
import com.xcart.prognosis.reports.ProjectsReportBuilder
import com.xcart.prognosis.reports.Report
import com.xcart.prognosis.reports.UsertasksReportBuilder
import com.xcart.prognosis.reports.WorkloadReportBuilder
import com.xcart.prognosis.services.Configuration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView


@Controller
class PageController @Autowired constructor(
        val workload: WorkloadReportBuilder,
        val usertasks: UsertasksReportBuilder,
        val projects: ProjectsReportBuilder,
        val config: Configuration
) {

    @RequestMapping("/")
    fun workload(@RequestParam query: String?): ModelAndView {
        val queryToUse = if (query.isNullOrEmpty())
            config.queryIssues
        else query
        return buildReportMav(queryToUse, workload.gather(queryToUse))
    }

    @RequestMapping("/tasks/{login}")
    fun tasks(@PathVariable login: String, @RequestParam query: String?): ModelAndView {
        val queryToUse = if (query.isNullOrEmpty())
             config.queryIssues
        else query
        return buildReportMav(queryToUse, usertasks.gather(login, queryToUse))
    }

    @RequestMapping("/projects")
    fun projects(@RequestParam query: String?): ModelAndView {
        val queryToUse = if (query.isNullOrEmpty())
             config.queryProjects
        else query
        return buildReportMav(queryToUse, projects.gather(queryToUse))
    }

    private fun buildReportMav(query: String, report: Report): ModelAndView {
        val mav = ModelAndView("index")
        val state = CommonPageState(query, report)
        mav.addObject("state", state)
        return mav
    }
}