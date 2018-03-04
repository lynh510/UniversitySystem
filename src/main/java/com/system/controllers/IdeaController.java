package com.system.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.system.models.*;;

@Controller
@RequestMapping("/idea")
public class IdeaController {
	private IdeaManagement im;

	// http://localhost:8080/idea/page/1
	@GetMapping("/page/{numberOfPage}")
	public ModelAndView getPage(@PathVariable("numberOfPage") String page) {
		im = new IdeaManagement();
		ModelAndView model = new ModelAndView("display_idea");
		int currentPage = Integer.parseInt(page);
		int recordsPerPage = 5;
		int noOfRecords = im.noOfRecords();

		int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		model.addObject("ideas", im.getIdeasPerPage(currentPage, recordsPerPage));
		model.addObject("noOfPages", noOfPages);
		model.addObject("currentPage", currentPage);
		return model;
	}

	@PostMapping("/submitIdea")
	public ModelAndView submit_idea(@RequestParam("input-file-preview") MultipartFile document_file) {
		ModelAndView mode = new ModelAndView("student_submit_idea");
		String auto_path_project = System.getProperty("user.dir");
		InputStream inputStream = null;
		OutputStream outputStream = null;
		String fileName = document_file.getOriginalFilename();
		try {
			inputStream = document_file.getInputStream();
			// edit path when use
			File newFile = new File(auto_path_project + "\\src\\main\\resources\\uploads_document\\" + fileName);
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
		return mode;
	}
}
