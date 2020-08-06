package com.xcart.prognosis.controllers

import com.xcart.prognosis.presentation.WorkloadPageState
import com.xcart.prognosis.reports.WorkloadReportBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class PageController @Autowired constructor(val reportBuilder: WorkloadReportBuilder) {

    @RequestMapping("/")
    fun index(): ModelAndView {
        val mav = ModelAndView("index")
        val query = "Project: WD State: Open, Waiting, {In progress} sort by: created"
        val state = WorkloadPageState(reportBuilder.gather(query))
        mav.addObject("state", state)
        return mav
    }
}