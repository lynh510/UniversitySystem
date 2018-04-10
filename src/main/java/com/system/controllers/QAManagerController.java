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
@RequestMapping("/qa")
public class QAManagerController {
	@RequestMapping("/login")
	public ModelAndView staffLogin() {
		return new ModelAndView("login");
	}

	@PostMapping("/authorization")
	@ResponseBody
	public ResponseEntity<ApiResponse> check_login(@RequestParam("username") String user_name,
			@RequestParam("password") String password) {
		QAManagerManagement qmm = new QAManagerManagement();
		String role = "";
		boolean found = false;
		Person p = new Person();
		p = qmm.QAManagerLogin(user_name, password);
		if (p.getId() != 0) {
			role = "QAManager";
			found = true;
		}
		if (found == false) {
			p = qmm.QACoordinatorLogin(user_name, password);
			if (p.getId() != 0) {
				role = "QACoordinator";
				found = true;
			}
		}
		if (found == false) {
			p = qmm.AdminLogin(user_name, password);
			if (p.getId() != 0) {
				role = "Admin";
				found = true;
			}
		}
		if (found == false) {
			p = qmm.StaffLogin(user_name, password);
			if (p.getId() != 0) {
				role = "Staff";
				found = true;
			}
		}
		if (user_name.isEmpty() && password.isEmpty()) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Please fill all fields");
		} else if (found == true) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession(true);
			p.setPerson_picture("/image/" + p.getPerson_picture());
			session.setAttribute(role, p);
			if (role.equals("QAManager")) {
				return new ApiResponse().send(HttpStatus.ACCEPTED, "/tag/listCategory");
			} else if (role.equals("QACoordinator")) {
				return new ApiResponse().send(HttpStatus.ACCEPTED, "/qaCoordinator");
			} else if (role.equals("Admin")) {
				return new ApiResponse().send(HttpStatus.ACCEPTED, "/admin");
			} else if (role.equals("Staff")) {
				return new ApiResponse().send(HttpStatus.ACCEPTED, "/idea/page/1");
			}
			return new ApiResponse().send(HttpStatus.ACCEPTED, "/qa/login");
		} else {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid username and password!");
		}

	}
}
