package com.system.controllers;

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
import com.system.entity.Person;
import com.system.models.AuthorizationManagement;
import com.system.models.ExternalLoginManagement;

@Controller
@RequestMapping("/staff")
public class StaffController {
	private AuthorizationManagement am;
	private ExternalLoginManagement elm;

	public StaffController() {
		am = new AuthorizationManagement();
		elm = new ExternalLoginManagement();
	}

	@GetMapping("/login")
	public ModelAndView admin_login() {
		ModelAndView model = new ModelAndView("login");
		model.addObject("role", "staff");
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
}
