package com.system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/report")
public class ReportController {

	@RequestMapping("/view")
	public ModelAndView staffLogin() {
		return new ModelAndView("view_report");
	}
	
}
