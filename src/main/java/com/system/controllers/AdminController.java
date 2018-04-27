package com.system.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.system.ApiResponse;
import com.system.Helper;
import com.system.entity.*;
import com.system.models.AcademicYearManagement;
import com.system.models.AuthorizationManagement;
import com.system.models.DepartmentManagement;
import com.system.models.PersonManagement;
import com.system.models.ReportManagement;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private AuthorizationManagement am;
	private AcademicYearManagement aym;
	private DepartmentManagement dm;
	private PersonManagement pm;
	private Helper helper;
	private ReportManagement rm;

	public AdminController() {
		am = new AuthorizationManagement();
		aym = new AcademicYearManagement();
		dm = new DepartmentManagement();
		pm = new PersonManagement();
		helper = new Helper();
		rm = new ReportManagement();
	}

	@GetMapping("/view_department/{academic_year}")
	public ModelAndView add_department(@PathVariable("academic_year") String year_id) {
		ModelAndView model = new ModelAndView("addDepartment");
		try {
			Person admin = getAdminSession();
			int academic_year_id = helper.decodeID(year_id);
			model.addObject("academicYear", aym.get(academic_year_id));
			model.addObject("departments", dm.getDepartmentsByAcademicYear(academic_year_id));
			model.addObject("admin", admin);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		} catch (NullPointerException e) {
			model = new ModelAndView("redirect:/admin/login");
		}
		return model;
	}

	@GetMapping("/add_academic_year")
	public ModelAndView add_academic_year() {
		try {
			Person admin = getAdminSession();
			ModelAndView model = new ModelAndView("add_academicYear");
			AcademicYear ay = new AcademicYear(0, new Date(), new Date(), new Date(), 0, "");
			model.addObject("academicYear", ay);
			model.addObject("action", "add");
			model.addObject("admin", admin);
			return model;
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/admin/login");
		}

	}

	@GetMapping("/academic_year")
	public ModelAndView academic_year() {
		try {
			Person p = getAdminSession();
			ModelAndView model = new ModelAndView("academicYear");
			model.addObject("academicYear", aym.getAcademicYear());
			model.addObject("admin", p);
			return model;
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/admin/login");
		}

	}

	@PostMapping("/addDepartment")
	@ResponseBody
	public ResponseEntity<ApiResponse> insert_department(@RequestParam("dept_name") String dept_name,
			@RequestParam("academic_year_id") int academic_year_id) {
		Department d = new Department(0, dept_name, new AcademicYear(academic_year_id));
		if (dm.check_duplicate_department(d)) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Duplicate Department");
		} else {
			dm.addDepartment(d);
			return new ApiResponse().send(HttpStatus.ACCEPTED, "Department has been added to the database");
		}
	}

	@PostMapping("/removeDepartment")
	@ResponseBody
	public ResponseEntity<ApiResponse> remove_department(@RequestParam("dept_id") String dept_id) {
		dm.remove_department(helper.decodeID(dept_id));
		return new ApiResponse().send(HttpStatus.ACCEPTED, "Department has been deleted from the database");

	}

	@PostMapping("/addAcademicYear")
	@ResponseBody
	public ResponseEntity<ApiResponse> insert_academicYear(@RequestParam("start_date") String start_date,
			@RequestParam("end_date") String end_date, @RequestParam("final_date") String final_date,
			@RequestParam("year") int year, @RequestParam("season") String season) {
		try {
			Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(start_date);
			Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(end_date);
			Date finalDate = new SimpleDateFormat("yyyy-MM-dd").parse(final_date);
			if (endDate.before(startDate) || endDate.equals(startDate)) {
				return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR,
						"Error: start date need to be before end date");
			} else if (finalDate.before(endDate)) {
				return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR,
						"Error: final date need to be after end date");
			} else {
				AcademicYear ay = new AcademicYear(0, startDate, endDate, finalDate, year, season);
				aym.addAcademicYear(ay);
				return new ApiResponse().send(HttpStatus.ACCEPTED, "Academic year has been added to the database");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
		}
	}

	@PostMapping("/editAcademicYear")
	@ResponseBody
	public ResponseEntity<ApiResponse> edit_academicYear(@RequestParam("start_date") String start_date,
			@RequestParam("end_date") String end_date, @RequestParam("final_date") String final_date,
			@RequestParam("year") int year, @RequestParam("season") String season, @RequestParam("id") String id) {
		try {
			Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(start_date);
			Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(end_date);
			Date finalDate = new SimpleDateFormat("yyyy-MM-dd").parse(final_date);
			if (endDate.before(startDate) || endDate.equals(startDate)) {
				return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR,
						"Error: start date need to be before end date");
			} else if (finalDate.before(endDate)) {
				return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR,
						"Error: final date need to be after end date");
			} else {
				AcademicYear ay = new AcademicYear(helper.decodeID(id), startDate, endDate, finalDate, year, season);
				aym.updateAcademicYear(ay);
				return new ApiResponse().send(HttpStatus.ACCEPTED, "Academic year has been updated to the database");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
		}

	}

	@PostMapping("/removeAcademicYear")
	@ResponseBody
	public ResponseEntity<ApiResponse> remove_academic_year(@RequestParam("id") String academicYear) {
		if (aym.deleteAcademicYear(helper.decodeID(academicYear))) {
			return new ApiResponse().send(HttpStatus.ACCEPTED, "Academic year has been deleted from the database");
		} else {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error while deleting, The academic year is opening");
		}
	}

	@GetMapping("/edit_academic_year/{academic_year}")
	public ModelAndView edit_academic_year(@PathVariable("academic_year") String academicYear) {
		getAdminSession();
		try {
			AcademicYear ay = aym.get(helper.decodeID(academicYear));
			ModelAndView model = new ModelAndView("add_academicYear");
			model.addObject("academicYear", ay);
			model.addObject("action", "edit");
			return model;
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/admin/login");
		}

	}

	@GetMapping("/login")
	public ModelAndView admin_login() {
		ModelAndView model = new ModelAndView("login");
		model.addObject("role", "admin");
		model.addObject("displayName", "Admin");
		return model;
	}

	@PostMapping("/authorization")
	@ResponseBody
	public ResponseEntity<ApiResponse> check_login(@RequestParam("username") String user_name,
			@RequestParam("password") String password) {
		Person p = new Person();
		p = am.AdminLogin(user_name, password);
		if (user_name.isEmpty() && password.isEmpty()) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Please fill all fields");
		} else if (p.getId() != 0) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession(true);
			p.setPerson_picture("/image/" + p.getPerson_picture());
			session.setAttribute("Admin", p);
			return new ApiResponse().send(HttpStatus.ACCEPTED, "/admin/dashboard");
		} else {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid username and password!");
		}
	}

	@GetMapping("/manage_user")
	public ModelAndView manage_user() {
		try {
			Person admin = getAdminSession();
			ModelAndView model = new ModelAndView("manageUser");
			model.addObject("persons", pm.getPerson());
			model.addObject("admin", admin);
			return model;
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/admin/login");
		}

	}

	@GetMapping("/logout")
	public ModelAndView logout() {
		ModelAndView model = new ModelAndView("redirect:/admin/login");
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession(false);
			session.removeAttribute("Admin");
		} catch (Exception e) {
			return model;
		}
		return model;
	}

	@GetMapping("/statistic")
	public ModelAndView statistic() {
		try {
			Person admin = getAdminSession();
			ModelAndView model = new ModelAndView("statistic");
			model.addObject("admin", admin);
			model.addObject("numberOfIdeas", rm.NummberOfIdeas());
			model.addObject("numberOfContributor", rm.NummberOfContributor());
			model.addObject("percentageOfIdeas", rm.PercentageOfIdeas());
			model.addObject("ideasWithoutComment", rm.IdeasWithoutComment());
			model.addObject("anonymousIdeaAndComment", rm.anonymousIdeaAndComment());
			return model;
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/admin/login");
		}
	}

	@GetMapping("/dashboard")
	public ModelAndView adminPage() {
		try {
			Person admin = getAdminSession();
			ModelAndView model = new ModelAndView("academicYear");
			model.addObject("academicYear", aym.getAcademicYear());
			model.addObject("admin", admin);
			return model;
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/admin/login");
		}

	}

	@GetMapping("/addStudents")
	public ModelAndView registration() {
		try {
			ModelAndView model = new ModelAndView("student_registration_form");
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
			model.addObject("days", days);
			model.addObject("months", months);
			model.addObject("years", years);
			return model;
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/admin/login");
		}

	}

	private Person getAdminSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession(false);
		if (session.getAttribute("Admin") == null) {
			throw new NullPointerException("Have to login first");
		} else {
			return (Person) session.getAttribute("Admin");
		}

	}
}
