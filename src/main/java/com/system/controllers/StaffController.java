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
import com.system.models.StaffManagement;

@Controller
@RequestMapping("/staff")
public class StaffController {
	private AuthorizationManagement am;
	private ExternalLoginManagement elm;
	private Helper helper;
	private PersonManagement pm;
	private StaffManagement sm;

	public StaffController() {
		am = new AuthorizationManagement();
		elm = new ExternalLoginManagement();
		helper = new Helper();
		pm = new PersonManagement();
		sm = new StaffManagement();
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
			return new ApiResponse().send(HttpStatus.ACCEPTED, "/home");
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
				return new ApiResponse().send(HttpStatus.ACCEPTED, "/home");
			}
		} else {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR,
					"Your staff account doesn't exist, contact administrator for more technical supports");
		}
	}

	// http://localhost:8080/staff/edit_account
	@GetMapping("/edit_account")
	public ModelAndView edit_profile() {
		try {
			Person staff = pm.getUserSession();
			Person p = pm.getPerson(staff.getId());
			ModelAndView model = new ModelAndView("edit_account");
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
			String[] str = String.valueOf(p.getBirthdate().toString()).split("-");
			model.addObject("year_of_user", str[0]);
			model.addObject("month_of_user", str[1]);
			model.addObject("day_of_user", str[2]);
			model.addObject("days", days);
			model.addObject("months", months);
			model.addObject("years", years);
			model.addObject("user_id", helper.encryptID(p.getId() + ""));
			model.addObject("welcome", p);
			model.addObject("navbar", "staff_navbar.jsp");
			return model;
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/admin/login");
		}
	}

	@RequestMapping("/change_password")
	public ModelAndView changePassword() {
		ModelAndView model = new ModelAndView("edit_password");
		try {
			Person staff = pm.getUserSession();
			model.addObject("role", "staff");
			model.addObject("displayName", "Staff");
			model.addObject("user", staff);
			model.addObject("welcome", staff);
			model.addObject("navbar", "staff_navbar.jsp");
			return model;
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/staff/login");
		}
	}

	@PostMapping("/change_password")
	public ResponseEntity<ApiResponse> get_contributions(@RequestParam("old_password") String old_password,
			@RequestParam("new_password") String new_password,
			@RequestParam("confirm_password") String confirm_password, @RequestParam("id") String id) {
		try {
			if (old_password.equals(new_password)) {
				return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR,
						"old password and new password can't be the same");
			} else if (!new_password.equals(confirm_password)) {
				return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR,
						"new password and confirm password don't match");
			} else {
				return new ApiResponse().send(HttpStatus.ACCEPTED,
						sm.change_password(helper.decodeID(id), old_password, new_password));
			}
		} catch (Exception e) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@GetMapping("/logout")
	public ModelAndView logout(RedirectAttributes redirectAttributes) {
		ModelAndView model = new ModelAndView("redirect:/staff/login");
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession(false);
			session.removeAttribute("user");
		} catch (Exception e) {
			return model;
		}
		return model;
	}

}
