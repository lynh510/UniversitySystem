package com.system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.system.entity.Tag;
import com.system.models.*;

@Controller
@RequestMapping("/tag")
public class TagController {
	private TagManagement tm;

	public TagController() {
		tm = new TagManagement();
	}

	@RequestMapping("/listCategory")
	public ModelAndView listTag() {
		ModelAndView model = new ModelAndView("list_category");
		model.addObject("tags", tm.getTags());
		return model;
	}

	@RequestMapping("/addCategory")
	public ModelAndView addCategory() {
		return new ModelAndView("add_category");
	}

	@PostMapping("/add")
	public ModelAndView add_tag(@RequestParam("tag_name") String tag_name) {
		ModelAndView model = new ModelAndView("redirect:/tag/listCategory");
		if (!tm.chec_exist(tag_name)) {
			Tag t = new Tag(tag_name);
			tm.insert_tag(t);
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
