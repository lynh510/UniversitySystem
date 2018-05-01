package com.system.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.system.ApiResponse;
import com.system.Helper;
import com.system.entity.*;
import com.system.models.*;

@Controller
@RequestMapping("/student")
public class StudentController {
	private PersonManagement pm;
	private TagManagement tm;
	private Helper helper;

	public StudentController() {
		pm = new PersonManagement();
		tm = new TagManagement();
		helper = new Helper();
	}

	// http://localhost:8080/student/login
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView model = new ModelAndView("student_login");
		return model;
	}

	// http://localhost:8080/student/submit_idea
	@GetMapping("/submit_idea")
	public ModelAndView submit_idea(RedirectAttributes redirectAttributes) {
		ModelAndView mnv = new ModelAndView();
		try {
			Person p = (Person) pm.getUserSession();
			if (p.getPerson_role() != 0) {
				mnv = new ModelAndView("redirect:/idea/page/1");
			} else {
				mnv = new ModelAndView("student_submit_idea");
				mnv.addObject("tags", tm.getTagsByDepartment(p.getDepartment().getId()));
				mnv.addObject("user_id", helper.encryptID(p.getId() + ""));
				mnv.addObject("welcom", p);
			}
		} catch (Exception e) {
			mnv = new ModelAndView("redirect:/student/login");
		}
		return mnv;
	}

	// http://localhost:8080/student/terms
	@GetMapping("/terms")
	public ModelAndView terms() {
		return new ModelAndView("student_terms");
	}

	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<ApiResponse> check_login(@RequestParam("user_name") String user_name,
			@RequestParam("password") String password,ModelAndView model) {
		StudentManagement sm = new StudentManagement();
		Student s = new Student();
		s.setUsername(user_name);
		s.setStudent_password(password);
		Person p = sm.check_login(s);
		if (user_name.isEmpty() && password.isEmpty()) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "You have to input data");
		} else if (p.getId() != 0) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession(true);
			p.setPerson_picture("/image/" + p.getPerson_picture());
			session.setAttribute("user", p);
			return new ApiResponse().send(HttpStatus.ACCEPTED, "Login successfully");
		} else {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid username and password!");
		}
	}

	@PostMapping("/external_login")
	@ResponseBody
	public ResponseEntity<ApiResponse> external_login(@RequestParam("email") String email,
			RedirectAttributes redirectAttributes) {
		ExternalLoginManagement elm = new ExternalLoginManagement();
		Person p = elm.isExist(email);
		if (p.getId() != 0) {
			if (p.getPerson_role() != 0) {
				return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR,
						"Your account doesn't exist, contact administrator for more technical supports");
			} else {
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
						.getRequest();
				HttpSession session = request.getSession(true);
				session.setAttribute("user", p);
				return new ApiResponse().send(HttpStatus.ACCEPTED, "Login successfully");
			}
		} else {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR,
					"Your account doesn't exist, contact administrator for more technical supports");
		}
	}

	@GetMapping("/logout")
	public ModelAndView logout(RedirectAttributes redirectAttributes) {
		ModelAndView model = new ModelAndView("redirect:/student/login");
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

	// http://localhost:8080/student/activities/1
	@GetMapping("/activities/{person_id}/{numberOfPage}")
	public ModelAndView activities(@PathVariable(value = "person_id") String person_id,
			@PathVariable("numberOfPage") String page) {
		ModelAndView model = new ModelAndView("student_wall");
		int user_id = helper.decodeID(person_id);
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession(false);
			if (session.getAttribute("user") != null) {
				Person p = (Person) session.getAttribute("user");
				IdeaManagement im = new IdeaManagement();
				int currentPage = Integer.parseInt(page);
				int recordsPerPage = 5;
				int noOfRecords = im.noOfRecords(user_id);
				int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
				model.addObject("ideas", im.getIdeasPerPageByPersonal(currentPage, recordsPerPage, user_id));
				model.addObject("noOfPages", noOfPages);
				model.addObject("currentPage", currentPage);
				model.addObject("welcom", p);
			} else {
				model = new ModelAndView("redirect:/student/login");
			}
		} catch (NullPointerException e) {
			model = new ModelAndView("redirect:/student/login");
		}

		return model;
	}

	@PostMapping("/edit")
	public ModelAndView edit_idea(@RequestParam("idea_id") int idea_id, @RequestParam("person_id") int person_id,
			@RequestParam("title") String title, @RequestParam("content") String content) {
		ModelAndView model = new ModelAndView("redirect:/student/activities/" + person_id + "/1");
		IdeaManagement im = new IdeaManagement();
		Idea idea = new Idea(idea_id, title, content);
		im.eidt_idea(idea);
		return model;
	}

	@RequestMapping("/delete/{person_id}/{idea_id}")
	public ModelAndView delete_idea(@PathVariable(value = "idea_id") int id,
			@PathVariable(value = "person_id") int person_id) {
		ModelAndView model = new ModelAndView("redirect:/student/activities/" + person_id + "/1");
		IdeaManagement im = new IdeaManagement();
		im.delete_idea(id);
		return model;
	}

	// http://localhost:8080/student/edit_account
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
				model = new ModelAndView("redirect:/student/login");
			} 
		} catch (NullPointerException e) {
			model = new ModelAndView("redirect:/student/login");
		}
		return model;
	}

	
}
