package com.system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.system.entity.Department;
import com.system.entity.Person;
import com.system.entity.Tag;
import com.system.models.*;

@Controller
@RequestMapping("/tag")
public class TagController {
	private TagManagement tm;
	private QAManagerManagement qmm;
	private DepartmentManagement dm;

	public TagController() {
		tm = new TagManagement();
		qmm = new QAManagerManagement();
		dm = new DepartmentManagement();
	}

	@RequestMapping("/listCategory")
	public ModelAndView listTag() {
		ModelAndView model = new ModelAndView("list_category");
		try {
			Person qamanager = qmm.getQAManagerSession();
			model.addObject("tags", tm.getTags());
			model.addObject("qaManager", qamanager);
		} catch (NullPointerException e) {
			model = new ModelAndView("redirect:/qamanager/login");
		}
		return model;
	}

	@RequestMapping("/addCategory")
	public ModelAndView addCategory() {
		ModelAndView model = new ModelAndView("add_category");
		try {
			Person qamanager = qmm.getQAManagerSession();
			model.addObject("departments", dm.getDepartmentByStatus(0));
			model.addObject("qaManager", qamanager);
		} catch (NullPointerException e) {
			model = new ModelAndView("redirect:/qamanager/login");
		}
		return model;
	}

	@PostMapping("/add")
	public ModelAndView add_tag(@RequestParam("tag_name") String tag_name, @RequestParam("dept_id") int dept_id) {
		ModelAndView model = new ModelAndView("add_category");
		model.addObject("departments", dm.getDepartmentByStatus(0));
		if (!tm.chec_exist(tag_name)) {
			Tag t = new Tag(tag_name, new Department(dept_id), 0);
			tm.insert_tag(t);
			model.addObject("error", "Tag has been added into database");
		} else {

			model.addObject("error", "Tag is existed");
		}
		return model;
	}

	@RequestMapping("/delete/{tag_id}")
	public ModelAndView delete_tag(@PathVariable("tag_id") int id, RedirectAttributes attributes) {
		ModelAndView model = new ModelAndView("redirect:/tag/listCategory");
		try {
			TagManagement im = new TagManagement();
			if (im.delete_tag(id) == false) {
				attributes.addFlashAttribute("message", "This categoriy is being used, cannot be deleted");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

}
