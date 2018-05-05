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

import com.system.ApiResponse;
import com.system.Helper;
import com.system.MailApi;
import com.system.entity.*;
import com.system.models.AcademicYearManagement;
import com.system.models.AdminManagement;
import com.system.models.AuthorizationManagement;
import com.system.models.DepartmentManagement;
import com.system.models.PersonManagement;
import com.system.models.ReportManagement;
import com.system.models.StaffManagement;
import com.system.models.StudentManagement;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private AuthorizationManagement am;
	private AcademicYearManagement aym;
	private DepartmentManagement dm;
	private PersonManagement pm;
	private Helper helper;
	private ReportManagement rm;
	private AdminManagement adminmanager;

	public AdminController() {
		am = new AuthorizationManagement();
		aym = new AcademicYearManagement();
		dm = new DepartmentManagement();
		pm = new PersonManagement();
		helper = new Helper();
		rm = new ReportManagement();
		adminmanager = new AdminManagement();
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
		try {
			Person admin = getAdminSession();
			AcademicYear ay = aym.get(helper.decodeID(academicYear));
			ModelAndView model = new ModelAndView("add_academicYear");
			model.addObject("admin", admin);
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

	@RequestMapping("/change_password")
	public ModelAndView changePassword() {
		ModelAndView model = new ModelAndView("edit_password");
		try {
			Person admin = getAdminSession();
			model.addObject("role", "admin");
			model.addObject("displayName", "admin");
			model.addObject("user", admin);
			model.addObject("welcome", admin);
			model.addObject("admin", admin);
			model.addObject("navbar", "admin_navbar.jsp");
			return model;
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/admin/login");
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
						adminmanager.change_password(helper.decodeID(id), old_password, new_password));
			}
		} catch (Exception e) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
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
			model.addObject("message", "of the whole system");
			model.addObject("navbar", "admin_navbar.jsp");
			model.addObject("academicYear", aym.getAcademicYear());
			model.addObject("numberOfIdeas", rm.NummberOfIdeas(0));
			model.addObject("numberOfContributor", rm.NummberOfContributor(0));
			model.addObject("percentageOfIdeas", rm.PercentageOfIdeas(0));
			model.addObject("ideasWithoutComment", rm.IdeasWithoutComment(0));
			model.addObject("anonymousIdeaAndComment", rm.anonymousIdeaAndComment(0));
			return model;
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/admin/login");
		}
	}

	@GetMapping("/statistic/{year_id}")
	public ModelAndView statistic_by_academicYear(@PathVariable("year_id") String year_id) {
		try {
			Person admin = getAdminSession();
			ModelAndView model = new ModelAndView("statistic");
			int academic_year = helper.decodeID(year_id);
			AcademicYear ay = aym.get(academic_year);
			model.addObject("admin", admin);
			model.addObject("navbar", "admin_navbar.jsp");
			model.addObject("message", "Academic year: " + ay.getSeason() + " - " + ay.getYear());
			model.addObject("academicYear", aym.getAcademicYear());
			model.addObject("numberOfIdeas", rm.NummberOfIdeas(academic_year));
			model.addObject("numberOfContributor", rm.NummberOfContributor(academic_year));
			model.addObject("percentageOfIdeas", rm.PercentageOfIdeas(academic_year));
			model.addObject("ideasWithoutComment", rm.IdeasWithoutComment(academic_year));
			model.addObject("anonymousIdeaAndComment", rm.anonymousIdeaAndComment(academic_year));
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

	@GetMapping("/delete/{role}/{person_id}")
	public ModelAndView delete(@PathVariable("role") int role, @PathVariable("person_id") String person_id) {
		try {
			Person admin = getAdminSession();
			ModelAndView model = new ModelAndView("redirect:/admin/manage_user");
			int p_id = helper.decodeID(person_id);
			pm.delete_external_login(p_id);
			pm.delete_user(role, p_id);
			pm.delete_person(p_id);
			return model;
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/admin/login");
		}
	}

	@GetMapping("/active/{role}/{person_id}")
	public ModelAndView active(@PathVariable("role") int role, @PathVariable("person_id") String person_id) {
		try {
			Person admin = getAdminSession();
			ModelAndView model = new ModelAndView("redirect:/admin/manage_user");
			int p_id = helper.decodeID(person_id);
			pm.manageUser(0, p_id);
			return model;
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/admin/login");
		}
	}

	@GetMapping("/resend_active/{role}/{person_id}")
	public ModelAndView resendActivationMail(@PathVariable("role") int role,
			@PathVariable("person_id") String person_id, HttpServletRequest request) {
		try {
			Person admin = getAdminSession();
			String baseUrl = String.format("%s://%s:%d/", request.getScheme(), request.getServerName(),
					request.getServerPort());
			ModelAndView model = new ModelAndView("redirect:/admin/manage_user");
			int p_id = helper.decodeID(person_id);
			Person p = pm.getPerson(p_id);
			MailApi mail = new MailApi();
			mail.sendHtmlEmail(p.getEmail(), "University Activation", "Please <a href=\"" + baseUrl + "account/active/"
					+ role + "/" + person_id + "\">click here</a> to active your account ");
			return model;
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/admin/login");
		}
	}

	@GetMapping("/edit_account")
	public ModelAndView edit_profile() {
		try {
			Person admin = getAdminSession();
			Person p = pm.getPerson(admin.getId());
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
			model.addObject("admin", admin);
			model.addObject("welcome", p);
			model.addObject("navbar", "admin_navbar.jsp");
			return model;
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/admin/login");
		}
	}

	@GetMapping("/addAccounts")
	public ModelAndView registration() {
		DepartmentManagement dm = new DepartmentManagement();
		try {
			Person admin = getAdminSession();
			ModelAndView model = new ModelAndView("student_registration_form");
			List<Integer> months = new ArrayList<Integer>();
			List<Integer> days = new ArrayList<Integer>();
			List<Integer> years = new ArrayList<Integer>();
			List<Department> listDeparment = dm.getDepartments(0);
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
			model.addObject("deparments", listDeparment);
			// model.addObject("admin", admin);
			return model;
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/admin/login");
		}
	}

	// for submitting data to backend
	@PostMapping("/registration")
	@ResponseBody
	public ResponseEntity<ApiResponse> student_registration(@RequestParam("first_name") String first_name,
			@RequestParam("last_name") String last_name, @RequestParam("user_name") String user_name,
			@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("day") String day, @RequestParam("month") String month, @RequestParam("year") String year,
			@RequestParam("gender") int gender, @RequestParam("profilepic") MultipartFile profilepic,
			@RequestParam("phone") String phone, @RequestParam("userrole") int userrole,
			@RequestParam("address") String address, @RequestParam("deparment") int deparment_id,
			HttpServletRequest request) {
		try {
			String baseUrl = String.format("%s://%s:%d/", request.getScheme(), request.getServerName(),
					request.getServerPort());
			StudentManagement sm = new StudentManagement();
			StaffManagement staff_m = new StaffManagement();
			if (userrole == 0) {
				if (!sm.isUserNameExisted(user_name)) {
					return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Username is already existed!");
				} else if (!sm.isEmailExist(email)) {
					return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Email address is existed!");
				} else {
					String full_name = first_name + " " + last_name;
					// get birthday

					String format_date = year + "-" + month + "-" + day;
					Calendar c = Calendar.getInstance();
					c.setTime(new Date());
					SimpleDateFormat simple_date = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date = simple_date.parse(format_date);
					java.sql.Date birthday = new java.sql.Date(date.getTime());
					Person p = null;
					Department dp = new Department(deparment_id);
					p = new Person("", full_name, userrole, birthday, gender, 1, phone, address, email, "", dp);
					if (profilepic.isEmpty()) {
						p.setPerson_picture("default_avatar.png");
					} else {
						p.setPerson_picture(c.getTimeInMillis() + ".png");
					}
					int person_id = sm.insert_person(p);
					p.setId(person_id);
					Student s = new Student(p, user_name, password);
					if (sm.studentRegistration(s).equalsIgnoreCase("Success")) {
						if (!profilepic.isEmpty()) {
							save_image(profilepic, p.getPerson_picture());
						}
						MailApi m = new MailApi();
						m.sendHtmlEmail(email, "Your account",
								"Your account to use the system: <br/> Username:" + "<b>" + user_name + "</b>"
										+ "<br/> Password: " + "<b>" + password + "</b>" + "<br/> Please <a href=\""
										+ baseUrl + "account/active/" + userrole + "/"
										+ helper.encryptID(person_id + "") + "\">click here</a> to active your account "
										+ "<br/> This is an automatic email, Please do not reply");
						return new ApiResponse().send(HttpStatus.ACCEPTED, "Registration successfully");
					} else {
						return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Error in system");
					}
				}
			} else if (userrole == 1) {
				if (!staff_m.isUserNameExisted(user_name)) {
					return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Username is already existed!");
				} else if (!sm.isEmailExist(email)) {
					return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Email address is existed!");
				} else {
					String full_name = first_name + " " + last_name;
					// get birthday

					String format_date = year + "-" + month + "-" + day;
					Calendar c = Calendar.getInstance();
					c.setTime(new Date());
					SimpleDateFormat simple_date = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date = simple_date.parse(format_date);
					java.sql.Date birthday = new java.sql.Date(date.getTime());
					Person p = null;
					p = new Person("", full_name, userrole, birthday, gender, 1, phone, address, email, "", null);
					if (profilepic.isEmpty()) {
						p.setPerson_picture("default_avatar.png");
					} else {
						p.setPerson_picture(c.getTimeInMillis() + ".png");
					}
					int person_id = staff_m.insert_person(p);
					p.setId(person_id);
					Staff s = new Staff(p, user_name, password);
					if (staff_m.staffRegistration(s).equalsIgnoreCase("Success")) {
						if (!profilepic.isEmpty()) {
							save_image(profilepic, p.getPerson_picture());
						}
						MailApi m = new MailApi();
						m.sendHtmlEmail(email, "Your account",
								"Your account to use the system: \n Username:" + "<b>" + user_name + "</b>"
										+ "\n Password: " + "<b>" + password + "</b>" + "\n Please <a href=\"" + baseUrl
										+ "account/active/" + userrole + "/" + helper.encryptID(person_id + "")
										+ "\">click here</a> to active your account "
										+ "\n This is an automatic email, Please do not reply");
						return new ApiResponse().send(HttpStatus.ACCEPTED, "Registration successfully");
					} else {
						return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Error in system");
					}

				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return null;
	}

	private void save_image(MultipartFile profilepic, String filename) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = profilepic.getInputStream();
			File newFile = new File("/file/" + filename);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			inputStream.close();
			outputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
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

	@GetMapping("/edit_account/{user_id}")
	public ModelAndView edit_account(@PathVariable(value = "user_id") String person_id) {
		ModelAndView model = new ModelAndView("edit_account");
		try {
			model.addObject("admin", getAdminSession());
			Person p2 = pm.getPerson(helper.decodeID(person_id));
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
			model.addObject("gender", p2.getGender());
			model.addObject("days", days);
			model.addObject("months", months);
			model.addObject("years", years);
			model.addObject("user_id", helper.encryptID(p2.getId() + ""));
			model.addObject("navbar", "admin_navbar.jsp");
			model.addObject("welcome", p2);

		} catch (NullPointerException e) {
			model = new ModelAndView("redirect:/student/login");
		}
		return model;
	}
}
