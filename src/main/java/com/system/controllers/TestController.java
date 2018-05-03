package com.system.controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.system.entity.Idea;
import com.system.entity.Person;
import com.system.models.IdeaManagement;
import com.system.models.PersonManagement;

@Controller
public class TestController {

	@RequestMapping("/")
	public ModelAndView firstPage() {
		return new ModelAndView("redirect:/student/login");
	}

	@RequestMapping("/home")
	public ModelAndView chartPage() {
		try {
			IdeaManagement im = new IdeaManagement();
			PersonManagement pm = new PersonManagement();
			ModelAndView model = new ModelAndView("display_idea");
			int recordsPerPage = 5;
			int noOfRecords = im.noOfRecords(0);
			int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
			List<Idea> listIdea = im.MostViewedIdeas(1, recordsPerPage);
			try {
				Person p = pm.getUserSession();
				model.addObject("welcome", p);
				if (p.getPerson_role() == 1) {
					model.addObject("navbar", "staff_navbar.jsp");
				} else {
					model.addObject("navbar", "student_navbar.jsp");
				}
			} catch (NullPointerException e) {
				Person p = new Person();
				p.setPerson_picture("/uploads/default_avatar.png");
				p.setPerson_name("Guest");
				model.addObject("welcome", p);
				model.addObject("navbar", "navbar.jsp");
			}
			model.addObject("ideas", listIdea);
			model.addObject("noOfPages", noOfPages);
			model.addObject("currentPage", 1);
			return model;
		} catch (NumberFormatException e) {
			return new ModelAndView("redirect:/idea/page/1");
		}
	}

	@GetMapping("/image/{name:.+}")
	public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) throws IOException {
		try {
			File imgFile = new File("/file/" + name);
			byte[] bytes = Files.readAllBytes(imgFile.toPath());
			return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(bytes);
		} catch (NoSuchFileException e) {
			InputStream input = getClass().getResourceAsStream("/uploads/default_avatar.png");
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			int nRead;
			byte[] data = new byte[16384];

			while ((nRead = input.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}
			buffer.flush();
			byte[] bytes = buffer.toByteArray();
			return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(bytes);
		}

	}

}
