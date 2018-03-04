package com.system.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/student")
public class StudentController {
	
	//First run the java file in com.system then parse url below into your browser
	//http://localhost:8080/student/registration
	@GetMapping("/registration")
	public ModelAndView registration() {
		ModelAndView model = new ModelAndView("student_registration_form");
		List<Integer> months = new ArrayList();
		List<Integer> days = new ArrayList();
		List<Integer> years = new ArrayList();
		for (int month = 1; month < 13; month++) {
			months.add(month);
		}
		for (int day = 1; day < 32; day++) {
			days.add(day);
		}
		for (int year = 1990; year < 2007; year++) {
			years.add(year);
		}
		model.addObject("days", days);
		model.addObject("months", months);
		model.addObject("years", years);
		return model ;
	}

	//http://localhost:8080/student/login
	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("student_login");
	}
	
	//for submitting data to backend
	@PostMapping("/registration")
	public ModelAndView sub(@RequestParam("name") String name, Model model) {
		ModelAndView mode = new ModelAndView("student_registration_form");
		mode.addObject("message", name);
		return mode;
	}
	
	//http://localhost:8080/student/submit_idea
	@GetMapping("/submit_idea")
	public ModelAndView submit_idea() {
		return new ModelAndView("student_submit_idea");
	}
}
