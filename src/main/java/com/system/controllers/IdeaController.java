package com.system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.system.models.*;;

@Controller
@RequestMapping("/idea")
public class IdeaController {
	private IdeaManagement im;

	//http://localhost:8080/idea/page/1
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
}
