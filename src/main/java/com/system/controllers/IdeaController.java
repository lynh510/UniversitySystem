package com.system.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.system.MailApi;
import com.system.entity.*;
import com.system.models.*;;

@Controller
@RequestMapping("/idea")
public class IdeaController {
	private IdeaManagement im;
	private PersonManagement pm;

	public IdeaController() {
		im = new IdeaManagement();
		pm = new PersonManagement();
	}

	// http://localhost:8080/idea/page/1
	@GetMapping("/page/{numberOfPage}")
	public ModelAndView getPage(@PathVariable("numberOfPage") String page) {
		im = new IdeaManagement();
		ModelAndView model = new ModelAndView("display_idea");
		int currentPage = Integer.parseInt(page);
		int recordsPerPage = 5;
		int noOfRecords = im.noOfRecords(0);
		int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		List<Idea> listIdea = im.getIdeasPerPage(currentPage, recordsPerPage);
		for (Idea idea : listIdea) {
			if (idea.getMode() == 0) {
				idea.getPerson().setPerson_name("Anonymous");
				idea.getPerson().setPerson_picture("/uploads/default_avatar.png");
			}
		}
		try {
			model.addObject("welcom", pm.getUserSession());
		} catch (NullPointerException e) {
			Person p = new Person();
			p.setPerson_picture("/uploads/default_avatar.png");
			model.addObject("welcom", p);
		}
		model.addObject("ideas", listIdea);
		model.addObject("noOfPages", noOfPages);
		model.addObject("currentPage", currentPage);
		return model;
	}

	@GetMapping("/")
	public ModelAndView get() {
		ModelAndView model = new ModelAndView("redirect:/idea/page/1");
		return model;
	}

	@GetMapping("/{idea_id}")
	public ModelAndView getIdea(@PathVariable("idea_id") Integer idea_id) {
		im = new IdeaManagement();
		ModelAndView model = new ModelAndView("display_idea");
		List<Idea> listIdea = new ArrayList();
		listIdea.add(im.get_Idea(idea_id));
		model.addObject("ideas", listIdea);
		model.addObject("noOfPages", 1);
		model.addObject("currentPage", 1);
		return model;
	}

	@PostMapping("/submit")
	@ResponseBody
	public ResponseEntity<ApiResponse> submit_idea(@RequestParam("title") String title,
			@RequestParam("content") String content, @RequestParam("tag") List<Integer> tags,
			@RequestParam("input-file-preview") List<MultipartFile> files, @RequestParam("mode") int mode,
			HttpServletRequest request) {
		try {
			String baseUrl = String.format("%s://%s:%d/", request.getScheme(), request.getServerName(),
					request.getServerPort());
			Idea idea = new Idea(0, title, content, pm.getUserSession(), null, new Date(), mode, 0, 0);
			int idea_id = im.insert_idea(idea);
			idea.setId(idea_id);
			insert_tags(tags, idea);
			MailApi m = new MailApi();

			if (files.get(0).isEmpty()) {
				System.out.println("no file is selected");
			} else {
				insert_attachfiles(files, idea);
			}
			m.sendHtmlEmail("universityofu23.coordinator@gmail.com", "Idea submission",
					"<a href=\"" + baseUrl + "/idea/" + idea_id + "\">Click here to see</a>\""
							+ "\n This is an automatic mail, Please do not reply");
			return new ApiResponse().send(HttpStatus.ACCEPTED, "Well done!! Your idea is posted successfully");
		} catch (NullPointerException e) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
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
				Idea_attachfiles ia = new Idea_attachfiles(0, idea, fileName, c.getTimeInMillis() + "." + extension,
						extension, "/file/");
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
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = file.getInputStream();
			// edit path when use
			File newFile = new File("file/" + name);
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

	@PostMapping("/edit")
	public ModelAndView edit_idea(@RequestParam("idea_id") int idea_id, @RequestParam("title") String title,
			@RequestParam("content") String content) {
		ModelAndView model = new ModelAndView("redirect:/idea/page/1");
		Idea idea = new Idea(idea_id, title, content);
		im.eidt_idea(idea);
		return model;
	}

	@RequestMapping("/delete/{idea_id}")
	public ModelAndView delete_idea(@PathVariable(value = "idea_id") int id) {
		ModelAndView model = new ModelAndView("redirect:/idea/page/1");
		im.delete_idea(id);
		return model;
	}

}
