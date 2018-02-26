package com.system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/student")
public class StudentController {
	
	//First run the java file in com.system the parse this into browser
	//http://localhost:8080/student/registration
	@RequestMapping("/registration")
	public ModelAndView registration() {
		return new ModelAndView("student_registration_form");
	}
	//First run the java file in com.system the parse this into browser
	//http://localhost:8080/student/login
	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("student_login");
	}
}
