package com.system.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
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
import com.system.entity.Idea;
import com.system.entity.Person;
import com.system.entity.Student;
import com.system.models.*;

@Controller
@RequestMapping("/student")
public class StudentController {

	// First run the java file in com.system then parse url below into your browser
	// http://localhost:8080/student/registration
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
		return model;
	}

	// http://localhost:8080/student/login
	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("student_login");
	}

	// for submitting data to backend
	@PostMapping("/registration")
	@ResponseBody
	public ResponseEntity<ApiResponse> student_registration(@RequestParam("first_name") String first_name,
			@RequestParam("last_name") String last_name, @RequestParam("user_name") String user_name,
			@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("day") String day, @RequestParam("month") String month, @RequestParam("year") String year,
			@RequestParam("gender") int gender, @RequestParam("profilepic") MultipartFile profilepic,
			@RequestParam("phone") String phone, @RequestParam("address") String address, Model model) {
		StudentManagement sm = new StudentManagement();
		if (!sm.isUserNameExisted(user_name)) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Username is already existed!");
		} else if (!sm.isEmailExist(email)) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Email address is existed!");
		} else {
			String full_name = first_name + " " + last_name;
			// get birthday
			try {
				String format_date = year + "-" + month + "-" + day;
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				SimpleDateFormat simple_date = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = simple_date.parse(format_date);
				java.sql.Date birthday = new java.sql.Date(date.getTime());
				Person p = new Person("", full_name, 0, birthday, gender, 0, phone, address, email, "");
				if (profilepic.isEmpty()) {
					p.setPerson_picture("default_avatar.png");
				} else {
					p.setPerson_picture(c.getTimeInMillis() + ".png");
				}
				Student s = new Student(p, user_name, password);
				if (sm.studentRegistration(s).equalsIgnoreCase("Success")) {
					String auto_path_project = System.getProperty("user.dir");
					if (!profilepic.isEmpty()) {
						save_image(auto_path_project, profilepic, p.getPerson_picture());
					}
					return new ApiResponse().send(HttpStatus.ACCEPTED, "Registration successfully");

				} else {
					return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Error in system");
				}
			} catch (ParseException e) {
				e.printStackTrace();
				return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
			}
		}
	}

	private void save_image(String path, MultipartFile profilepic, String filename) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = profilepic.getInputStream();
			File newFile = new File(path + "\\src\\main\\resources\\uploads\\" + filename);
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

	// http://localhost:8080/student/submit_idea
	@GetMapping("/submit_idea")
	public ModelAndView submit_idea(RedirectAttributes redirectAttributes) {
		ModelAndView mnv = new ModelAndView("student_submit_idea");
		TagManagement tm = new TagManagement();
		mnv.addObject("tags", tm.getTags());
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession(false);
		if (session.getAttribute("user") == null) {
			mnv = new ModelAndView("redirect:/student/login");
		} else {
			Person p = (Person) session.getAttribute("user");
			mnv.addObject("welcom", p);
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
			@RequestParam("password") String password) {
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
			p.setPerson_picture("/uploads/" + p.getPerson_picture());
			session.setAttribute("user", p);
			return new ApiResponse().send(HttpStatus.ACCEPTED, "Thank for your contribution");
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
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession(true);
			session.setAttribute("user", p);
			return new ApiResponse().send(HttpStatus.ACCEPTED, "Login successfully");
		} else {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR,
					"Your account doesn't exist, contact administrator for more technical supports");
		}
	}

	@GetMapping("/logout")
	public ModelAndView logout(RedirectAttributes redirectAttributes) {
		ModelAndView model = new ModelAndView("redirect:/student/login");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession(false);
		session.removeAttribute("user");
		return model;
	}

	// http://localhost:8080/student/activities/1
	@GetMapping("/activities/{person_id}/{numberOfPage}")
	public ModelAndView activities(@PathVariable(value = "person_id") int person_id,
			@PathVariable("numberOfPage") String page) {
		ModelAndView model = new ModelAndView("student_wall");
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession(false);
			if (session.getAttribute("user") != null) {
				Person p = (Person) session.getAttribute("user");
				IdeaManagement im = new IdeaManagement();
				int currentPage = Integer.parseInt(page);
				int recordsPerPage = 5;
				int noOfRecords = im.noOfRecords();
				int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
				model.addObject("ideas", im.getIdeasPerPageByPersonal(currentPage, recordsPerPage, person_id));
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

}
