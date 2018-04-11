package com.system.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.system.ApiResponse;
import com.system.entity.Person;
import com.system.models.*;

@Controller
@RequestMapping("/qamanager")
public class QAManagerController {
	private QAManagerManagement qmm;
	private AuthorizationManagement am;

	public QAManagerController() {
		qmm = new QAManagerManagement();
		am = new AuthorizationManagement();
	}

	@RequestMapping("/login")
	public ModelAndView staffLogin() {
		ModelAndView model = new ModelAndView("login");
		model.addObject("role", "qamanager");
		model.addObject("displayName", "QA Manager");
		return model;
	}

	@PostMapping("/authorization")
	@ResponseBody
	public ResponseEntity<ApiResponse> check_login(@RequestParam("username") String user_name,
			@RequestParam("password") String password) {
		Person p = new Person();
		p = am.QAManagerLogin(user_name, password);
		if (user_name.isEmpty() && password.isEmpty()) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Please fill all fields");
		} else if (p.getId() != 0) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession(true);
			p.setPerson_picture("/image/" + p.getPerson_picture());
			session.setAttribute("QAManager", p);
			return new ApiResponse().send(HttpStatus.ACCEPTED, "/tag/listCategory");
		} else {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid username and password!");
		}
	}
}
