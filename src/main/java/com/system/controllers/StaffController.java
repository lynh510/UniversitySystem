package com.system.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.system.ApiResponse;
import com.system.Helper;
import com.system.entity.Person;
import com.system.models.AuthorizationManagement;
import com.system.models.ExternalLoginManagement;
import com.system.models.PersonManagement;

@Controller
@RequestMapping("/staff")
public class StaffController {
	private AuthorizationManagement am;
	private ExternalLoginManagement elm;
	private Helper helper;
	private PersonManagement pm;
	
	public StaffController() {
		am = new AuthorizationManagement();
		elm = new ExternalLoginManagement();
		helper = new Helper();
		pm = new PersonManagement();
	}

	@GetMapping("/login")
	public ModelAndView admin_login() {
		ModelAndView model = new ModelAndView("login");
		model.addObject("role", "staff");
		model.addObject("role_user", "staff");
		model.addObject("displayName", "Staff");
		return model;
	}

	@PostMapping("/authorization")
	@ResponseBody
	public ResponseEntity<ApiResponse> check_login(@RequestParam("username") String user_name,
			@RequestParam("password") String password) {
		Person p = new Person();
		p = am.StaffLogin(user_name, password);
		if (user_name.isEmpty() && password.isEmpty()) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Please fill all fields");
		} else if (p.getId() != 0) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession(true);			
			p.setPerson_picture("/image/" + p.getPerson_picture());
			session.setAttribute("user", p);
			return new ApiResponse().send(HttpStatus.ACCEPTED, "/idea/page/1");
		} else {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid username and password!");
		}
	}

	@PostMapping("/external_login")
	@ResponseBody
	public ResponseEntity<ApiResponse> external_login(@RequestParam("email") String email,
			RedirectAttributes redirectAttributes) {
		Person p = elm.isExist(email);
		if (p.getId() != 0) {
			if (p.getPerson_role() != 1) {
				return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR,
						"Your staff account doesn't exist, contact administrator for more technical supports");
			} else {
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
						.getRequest();
				HttpSession session = request.getSession(true);
				session.setAttribute("user", p);
				return new ApiResponse().send(HttpStatus.ACCEPTED, "/idea/page/1");
			}

		} else {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR,
					"Your staff account doesn't exist, contact administrator for more technical supports");
		}
	}
	
	// http://localhost:8080/staff/edit_account
		@GetMapping("/edit_account")
		public ModelAndView edit_account() {
			ModelAndView model = new ModelAndView("edit_account");
			try {
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
						.getRequest();
				HttpSession session = request.getSession(false);
				if (session.getAttribute("user") != null) {
					Person p = (Person) session.getAttribute("user");
					Person p2 = pm.getPerson(p.getId());
					List<Integer> months = new ArrayList<Integer>();
					List<Integer> days = new ArrayList<Integer>();
					List<Integer> years = new ArrayList<Integer>();
					for (int month = 1; month < 13; month++) {
						months.add(month);
					}
					for (int day = 1; day < 32; day++) {
						days.add(day);
					}
					for (int year = 1990; year < 2007; year++) {
						years.add(year);
					}
					String[] str = String.valueOf(p2.getBirthdate().toString()).split("-");
					model.addObject("year_of_user", str[0]);
					model.addObject("month_of_user", str[1]);
					model.addObject("day_of_user", str[2]);
					model.addObject("gender", p.getGender());
					model.addObject("days", days);
					model.addObject("months", months);
					model.addObject("years", years);
					model.addObject("user_id", helper.encryptID(p.getId() + ""));
					model.addObject("welcom", p2);
				} else {
					model = new ModelAndView("redirect:/staff/login");
				} 
			} catch (NullPointerException e) {
				model = new ModelAndView("redirect:/staff/login");
			}
			return model;
		}

}
