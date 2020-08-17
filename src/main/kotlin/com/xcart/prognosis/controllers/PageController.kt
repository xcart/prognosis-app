package com.xcart.prognosis.controllers

import com.xcart.prognosis.presentation.CommonPageState
import com.xcart.prognosis.reports.UsertasksReportBuilder
import com.xcart.prognosis.reports.WorkloadReportBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView


@Controller
class PageController @Autowired constructor(val workload: WorkloadReportBuilder, val usertasks: UsertasksReportBuilder) {

    @RequestMapping("/")
    fun index(@RequestParam query: String?): ModelAndView {
        val mav = ModelAndView("index")
        val queryToUse = if (query.isNullOrEmpty())
            "Project: WD State: Open, Waiting, {In progress} sort by: {Start Date} asc, created asc"
        else query
        val state = CommonPageState(queryToUse, workload.gather(queryToUse))
        mav.addObject("state", state)
        return mav
    }

    @RequestMapping("/tasks/{login}")
    fun index(@PathVariable login: String, @RequestParam query: String?): ModelAndView {
        val mav = ModelAndView("index")
        val queryToUse = if (query.isNullOrEmpty())
            "Project: WD State: Open, Waiting, {In progress} sort by: created"
        else query
        val state = CommonPageState(queryToUse, usertasks.gather(login, queryToUse))
        mav.addObject("state", state)
        return mav
    }
}