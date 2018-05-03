package com.system.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.system.ApiResponse;
import com.system.Helper;
import com.system.entity.Person;
import com.system.models.PersonManagement;

@Controller
@RequestMapping("/account")
public class AccountController {

	PersonManagement pm;

	public AccountController() {
		pm = new PersonManagement();
	}

	@PostMapping("/perform_edit_account")
	@ResponseBody
	public ResponseEntity<ApiResponse> perform_edit_account(@RequestParam("full_name") String full_name,
			@RequestParam("person_id") int person_id, @RequestParam("day") String day,
			@RequestParam("month") String month, @RequestParam("year") String year, @RequestParam("gender") int gender,
			@RequestParam("profilepic") MultipartFile profilepic, @RequestParam("phone") String phone,
			@RequestParam("address") String address) {
		try {
			String format_date = year + "-" + month + "-" + day;
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			SimpleDateFormat simple_date = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date = simple_date.parse(format_date);
			java.sql.Date birthday = new java.sql.Date(date.getTime());
			Person p = new Person(person_id, "", full_name, birthday, gender, phone, address);
			if (profilepic.isEmpty()) {
				p.setPerson_picture("default_avatar.png");
			} else {
				p.setPerson_picture(c.getTimeInMillis() + ".png");
			}
			if (pm.edit_account(p)) {
				if (!profilepic.isEmpty()) {
					save_image(profilepic, p.getPerson_picture());
				}
				return new ApiResponse().send(HttpStatus.ACCEPTED, "Edit account successfully");
			} else {
				return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Error in system");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
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

	@RequestMapping("/active/{role}/{person_id}")
	public ModelAndView active_account(@PathVariable("role") int role, @PathVariable("person_id") String person_id,
			RedirectAttributes redirectAttributes) {
		Helper helper = new Helper();
		PersonManagement pm = new PersonManagement();
		pm.manageUser(0, helper.decodeID(person_id));
		redirectAttributes.addFlashAttribute("message", "Your account has been activated");
		if (role == 0) {
			return new ModelAndView("redirect:/student/login");
		} else {
			return new ModelAndView("redirect:/staff/login");
		}
	}
}
