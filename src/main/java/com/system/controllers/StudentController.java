package com.system.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.system.entity.Person;
import com.system.entity.Student;
import com.system.models.StudentManagement;

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
	public ModelAndView sub(@RequestParam("first_name") String first_name, @RequestParam("last_name") String last_name,
			@RequestParam("user_name") String user_name, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("day") String day,
			@RequestParam("month") String month, @RequestParam("year") String year, @RequestParam("gender") int gender,
			@RequestParam("profilepic") MultipartFile profilepic, @RequestParam("phone") String phone,
			@RequestParam("address") String address, Model model) {
		ModelAndView mode = new ModelAndView("student_registration_form");
		StudentManagement sm = new StudentManagement();
		String errors = "";
		if (!sm.isUserNameExisted(user_name)) {
			errors = "Username is existed!";
			mode.addObject("errors", errors);
		} else if (!sm.isEmailExist(email)) {
			errors = "Email is existed!";
			mode.addObject("errors", errors);
		} else if (profilepic.isEmpty()) {
			errors = "You have to choose avatar!";
			mode.addObject("errors", errors);
		} else {
			String full_name = first_name + " " + last_name;
			// get birthday
			
			try {
				String format_date = year + "-" + month + "-" + day;
				SimpleDateFormat simple_date = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = simple_date.parse(format_date);
				java.sql.Date birthday = new java.sql.Date(date.getTime());			
				Person p = new Person(profilepic.getOriginalFilename(), full_name, 0, birthday, gender, 0, phone, address, email, "");	
				Student s = new Student(p,user_name, password);
				if (sm.studentRegistration(s) != 1) {
					String auto_path_project = System.getProperty("user.dir");
					InputStream inputStream = null;
					OutputStream outputStream = null;
					String fileName = profilepic.getOriginalFilename();
					try {
						inputStream = profilepic.getInputStream();
						// edit path when use
						File newFile = new File(auto_path_project + "\\src\\main\\resources\\uploads\\" + fileName);
						if (!newFile.exists()) {
							newFile.createNewFile();
						}
						outputStream = new FileOutputStream(newFile);
						int read = 0;
						byte[] bytes = new byte[1024];
						while ((read = inputStream.read(bytes)) != -1) {
							outputStream.write(bytes, 0, read);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				} else {
					errors = "Some error from system!";
					mode.addObject("errors", errors);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mode;
	}
	
	//http://localhost:8080/student/submit_idea
	@GetMapping("/submit_idea")
	public ModelAndView submit_idea() {
		return new ModelAndView("student_submit_idea");
	}
}
