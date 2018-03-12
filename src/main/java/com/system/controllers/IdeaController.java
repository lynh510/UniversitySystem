package com.system.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.system.entity.*;
import com.system.models.*;;

@Controller
@RequestMapping("/idea")
public class IdeaController {
	private IdeaManagement im;

	public IdeaController() {
		im = new IdeaManagement();
	}

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

	@PostMapping("/submit")
	public ModelAndView submit_idea(@RequestParam("title") String title, @RequestParam("content") String content,
			@RequestParam("tag") List<Integer> tags, @RequestParam("input-file-preview") List<MultipartFile> files,
			@RequestParam("mode") int mode, Model model, RedirectAttributes redirectAttributes) {
		String project_path = System.getProperty("user.dir");
		Person p = new Person();
		p.setId(1);
		Idea idea = new Idea(0, title, content, p, null, new Date(), mode, 0, 0);
		int idea_id = im.insert_idea(idea);
		idea.setId(idea_id);
		insert_tags(tags, idea);
		if (files.get(0).isEmpty()) {
			System.out.println("file is null");
		} else {
			insert_attachfiles(files, idea);
		}
		ModelAndView modelnv = new ModelAndView("redirect:/student/submit_idea");
		redirectAttributes.addFlashAttribute("message", "Well done!! Idea posted successfully");
		return modelnv;
	}

	private void insert_tags(List<Integer> tags, Idea idea) {
		for (Integer tag : tags) {
			Idea_Tag it = new Idea_Tag(0, idea, new Tag(tag, ""));
			im.insert_Idea_tags(it);
		}
	}

	private void insert_attachfiles(List<MultipartFile> files, Idea idea) {
		for (MultipartFile multipartFile : files) {
			String fileName = multipartFile.getOriginalFilename();
			try {
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				Thread.sleep(1);
				String extension = getExtension(fileName);
				Idea_attachfiles ia = new Idea_attachfiles(0, idea, extension, c.getTimeInMillis() + "." + extension);
				im.insert_Idea_attachfiles(ia);
				save_file(multipartFile, c.getTimeInMillis() + "." + extension);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

	private void save_file(MultipartFile file, String name) {
		String auto_path_project = System.getProperty("user.dir");
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = file.getInputStream();
			// edit path when use
			File newFile = new File(auto_path_project + "\\src\\main\\resources\\uploads_document\\" + name);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getExtension(String filename) {
		String[] parts = filename.split("\\.");
		return parts[1];
	}
}
