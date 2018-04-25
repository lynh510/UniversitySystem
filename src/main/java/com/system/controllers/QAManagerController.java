package com.system.controllers;

import java.util.Map.Entry;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.system.ApiResponse;
import com.system.entity.*;
import com.system.models.*;

@Controller
@RequestMapping("/qamanager")
public class QAManagerController {
	private AuthorizationManagement am;
	private ExternalLoginManagement elm;
	private ReportManagement rm;

	public QAManagerController() {
		elm = new ExternalLoginManagement();
		am = new AuthorizationManagement();
		rm = new ReportManagement();
	}

	@RequestMapping("/login")
	public ModelAndView staffLogin() {
		ModelAndView model = new ModelAndView("login");
		model.addObject("role", "qamanager");
		model.addObject("displayName", "QA Manager");
		return model;
	}

	@PostMapping("/authorization")
	@ResponseBody
	public ResponseEntity<ApiResponse> check_login(@RequestParam("username") String user_name,
			@RequestParam("password") String password) {
		Person p = new Person();
		p = am.QAManagerLogin(user_name, password);
		if (user_name.isEmpty() && password.isEmpty()) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Please fill all fields");
		} else if (p.getId() != 0) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession(true);
			p.setPerson_picture("/image/" + p.getPerson_picture());
			session.setAttribute("QAManager", p);
			return new ApiResponse().send(HttpStatus.ACCEPTED, "/tag/listCategory");
		} else {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid username and password!");
		}
	}

	@RequestMapping("/chart")
	public ModelAndView chartPage() {
		ModelAndView mv = new ModelAndView("chart");
		mv.addObject("numberOfIdeas", NummberOfIdeas());
		mv.addObject("numberOfContributor", NummberOfContributor());
		mv.addObject("percentageOfIdeas", PercentageOfIdeas());
		mv.addObject("ideasWithoutComment", IdeasWithoutComment());
		mv.addObject("anonymousIdeaAndComment",anonymousIdeaAndComment());
		return mv;
	}

	private String NummberOfIdeas() {
		String value = "[";
		for (Entry<Department, Integer> entry : rm.NumberOfIdeas().entrySet()) {
			Department d = entry.getKey();
			Integer i = entry.getValue();
			value += "{\"name\": \"" + d.getDept_name() + "\",\"y\":" + i + ",\"drilldown\":\"" + d.getDept_name()
					+ "\"},";
		}
		return value + "]";
	}

	private String NummberOfContributor() {
		String value = "[";
		for (Entry<Department, Integer> entry : rm.numberOfContributer().entrySet()) {
			Department d = entry.getKey();
			Integer i = entry.getValue();
			value += "{\"name\": \"" + d.getDept_name() + "\",\"y\":" + i + ",\"drilldown\":\"" + d.getDept_name()
					+ "\"},";
		}
		return value + "]";
	}

	private String PercentageOfIdeas() {
		String value = "[";
		for (Entry<Department, Float> entry : rm.PercentageOfIdeas().entrySet()) {
			Department d = entry.getKey();
			Float i = entry.getValue();
			value += "{\"name\": \"" + d.getDept_name() + "\",\"y\":" + i + ",\"drilldown\":\"" + d.getDept_name()
					+ "\"},";
		}
		return value + "]";
	}

	private String IdeasWithoutComment() {
		String value = "[";
		for (Entry<Department, Integer> entry : rm.ideaWithoutComment().entrySet()) {
			Department d = entry.getKey();
			Integer i = entry.getValue();
			value += "{\"name\": \"" + d.getDept_name() + "\",\"y\":" + i + ",\"drilldown\":\"" + d.getDept_name()
					+ "\"},";
		}
		return value + "]";
	}

	private String anonymousIdeaAndComment() {
		String value = "[";
		for (Entry<Department, Integer[]> entry : rm.numberOfAnonymousIdeaAndComment().entrySet()) {
			Department d = entry.getKey();
			Integer ideas = entry.getValue()[0];
			Integer comments = entry.getValue()[1];
			value += "{name: '" + d.getDept_name() + "',data: [" + ideas + ", " + comments + "]},";
		}
		return value + "]";
	}

	@PostMapping("/external_login")
	@ResponseBody
	public ResponseEntity<ApiResponse> external_login(@RequestParam("email") String email,
			RedirectAttributes redirectAttributes) {
		Person p = elm.isExist(email);
		if (p.getId() != 0) {
			if (p.getPerson_role() != 3) {
				return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR,
						"Your QA Manager account doesn't exist, contact administrator for more technical supports");
			} else {
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
						.getRequest();
				HttpSession session = request.getSession(true);
				session.setAttribute("QAManager", p);
				return new ApiResponse().send(HttpStatus.ACCEPTED, "/tag/listCategory");
			}
		} else {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR,
					"Your QA Manager account doesn't exist, contact administrator for more technical supports");
		}
	}
}
