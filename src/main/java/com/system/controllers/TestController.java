package com.system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

	@RequestMapping("/hello.html")
	public ModelAndView firstPage() {
		return new ModelAndView("hello");
	}

}
