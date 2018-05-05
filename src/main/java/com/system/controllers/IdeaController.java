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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.system.ApiResponse;
import com.system.Helper;
import com.system.MailApi;
import com.system.entity.*;
import com.system.models.*;;

@Controller
@RequestMapping("/idea")
public class IdeaController {
	private IdeaManagement im;
	private PersonManagement pm;
	private MailApi m;
	private Helper helper;

	public IdeaController() {
		im = new IdeaManagement();
		pm = new PersonManagement();
		m = new MailApi();
		helper = new Helper();
	}

	// http://localhost:8080/idea/page/1
	@GetMapping("/page/{numberOfPage}")
	public ModelAndView getPage(@PathVariable("numberOfPage") String page) {
		try {
			ModelAndView model = new ModelAndView("display_idea");
			int currentPage = Integer.parseInt(page);
			int recordsPerPage = 5;
			int noOfRecords = im.noOfRecords(0);
			int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
			List<Idea> listIdea = im.getIdeasPerPage(currentPage, recordsPerPage);
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
			model.addObject("currentPage", currentPage);
			return model;
		} catch (NumberFormatException e) {
			return new ModelAndView("redirect:/idea/page/1");
		}

	}

	@GetMapping("mostviewed/page/{numberOfPage}")
	public ModelAndView mostViewedIdeas(@PathVariable("numberOfPage") String page) {
		try {
			ModelAndView model = new ModelAndView("display_idea");
			int currentPage = Integer.parseInt(page);
			int recordsPerPage = 5;
			int noOfRecords = im.noOfRecords(0);
			int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
			List<Idea> listIdea = im.MostViewedIdeas(currentPage, recordsPerPage);
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
			model.addObject("currentPage", currentPage);
			return model;
		} catch (NumberFormatException e) {
			return new ModelAndView("redirect:/idea/page/1");
		}

	}

	@PostMapping("search")
	public ModelAndView post_search(@RequestParam("search") String search) {
		ModelAndView model = new ModelAndView("redirect:/idea/search/page/1/" + search + "/keywords_search");
		return model;
	}

	@GetMapping("search/page/{numberOfPage}/{keywords}/keywords_search")
	public ModelAndView search_idea(@PathVariable("numberOfPage") String page,
			@PathVariable("keywords") String keywords) {
		try {
			ModelAndView model = new ModelAndView("display_idea");
			int currentPage = Integer.parseInt(page);
			int recordsPerPage = 5;
			int noOfRecords = im.countSearch(keywords);
			int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
			List<Idea> listIdea = im.searchIdea(keywords, currentPage, recordsPerPage);
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
			model.addObject("currentPage", currentPage);
			return model;
		} catch (NumberFormatException e) {
			return new ModelAndView("redirect:/idea/page/1");
		}

	}

	@GetMapping("mostliked/page/{numberOfPage}")
	public ModelAndView mostLikedIdeas(@PathVariable("numberOfPage") String page) {
		try {
			ModelAndView model = new ModelAndView("display_idea");
			int currentPage = Integer.parseInt(page);
			int recordsPerPage = 5;
			int noOfRecords = im.noOfRecords(0);
			int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
			List<Idea> listIdea = im.MostLikedIdeas(currentPage, recordsPerPage);
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
			model.addObject("currentPage", currentPage);
			return model;
		} catch (NumberFormatException e) {
			return new ModelAndView("redirect:/idea/page/1");
		}

	}

	@GetMapping("/")
	public ModelAndView get() {
		ModelAndView model = new ModelAndView("redirect:/idea/page/1");
		return model;
	}

	@GetMapping("/{idea_id}")
	public ModelAndView getIdea(@PathVariable("idea_id") String idea_id) {
		im = new IdeaManagement();
		ModelAndView model = new ModelAndView("display_idea");
		List<Idea> listIdea = new ArrayList<Idea>();
		listIdea.add(im.get_Idea(helper.decodeID(idea_id)));
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
			Person p = pm.getUserSession();
			if (p.getDepartment().getStatus() == 0) {
				Idea idea = new Idea(0, title, content, p, null, mode, 0, 0);
				int idea_id = im.insert_idea(idea);
				idea.setId(idea_id);
				insert_tags(tags, idea);
				if (files.get(0).isEmpty()) {
					System.out.println("no file is selected");
				} else {
					insert_attachfiles(files, idea);
				}
				m.sendHtmlEmail("universityofu23.coordinator@gmail.com",
						"New Idea submission from " + p.getPerson_name(),
						"A new idea is submitted" + "<br/><a href=\"" + baseUrl + "/idea/"
								+ helper.encryptID(idea_id + "") + "\">Click here to see</a>\""
								+ "<br/> This is an automatic email, Please do not reply");
				return new ApiResponse().send(HttpStatus.ACCEPTED,
						"Well done!! Your idea is posted successfully. Please wait util your idea get approve ");
			} else if (p.getDepartment().getStatus() == 1) {
				return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR,
						"The Academic is closing, you cannot submit more ideas");
			} else {
				return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Academic year has been closed");
			}

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
			File newFile = new File("/file/" + name);
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
		String extension = "";
		int i = filename.lastIndexOf('.');
		if (i > 0) {
			extension = filename.substring(i + 1);
		}
		return extension;
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

	@GetMapping("/onview/{idea_id}")
	public ResponseEntity<ApiResponse> onView(@PathVariable(value = "idea_id") int idea_id) {
		im.updateView(idea_id);
		return new ApiResponse().send(HttpStatus.ACCEPTED, "");
	}

}
