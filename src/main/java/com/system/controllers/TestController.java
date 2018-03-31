package com.system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

	@RequestMapping("/")
	public ModelAndView firstPage() {
		return new ModelAndView("redirect:/student/login");
	}
	
	@RequestMapping("/login")
	public ModelAndView staffLogin() {
		return new ModelAndView("login");
	}
	
	@RequestMapping("/addCategory")
	public ModelAndView addCategory() {
		return new ModelAndView("add_category");
	}
	
	@RequestMapping("/listCategory")
	public ModelAndView listCategory() {
		return new ModelAndView("list_category");
	}

}
