package com.system.controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.system.ApiResponse;
import com.system.entity.Person;
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

	@PostMapping("/add")
	public ModelAndView add_tag(@RequestParam("tag_name") String tag_name) {
		ModelAndView model = new ModelAndView("redirect:/tag/listCategory");
		long millis=System.currentTimeMillis();  
		java.sql.Date date_now=new java.sql.Date(millis); 
		Calendar c = Calendar.getInstance();
		c.set(date_now.getDate(), date_now.getMonth(), date_now.getYear());
		java.sql.Date closed_time = (Date) c.getTime();
		if (tm.chec_exist(tag_name)) {
			Tag t = new Tag(tag_name,date_now,closed_time,1);
			tm.insert_tag(t);
		}
		else {
			model.addObject("error","Tag is existed");
		}
		return model;
	}
	
	
}
