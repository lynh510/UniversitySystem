package com.system.controllers;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.system.ApiResponse;
import com.system.MailApi;
import com.system.entity.Idea;
import com.system.entity.Person;
import com.system.models.AuthorizationManagement;
import com.system.models.DepartmentManagement;
import com.system.models.ExternalLoginManagement;
import com.system.models.IdeaManagement;

@Controller
@RequestMapping("/qacoordinator")
public class QACoordinatorController {
	private AuthorizationManagement am;
	private ExternalLoginManagement elm;
	private DepartmentManagement dm;
	private IdeaManagement im;
	private MailApi mail;

	public QACoordinatorController() {
		am = new AuthorizationManagement();
		elm = new ExternalLoginManagement();
		dm = new DepartmentManagement();
		im = new IdeaManagement();
		mail = new MailApi();
	}

	@RequestMapping("/dashboard")
	public ModelAndView departmentPage() {
		ModelAndView model = new ModelAndView("departments");
		model.addObject("ideas", im.getIdeasByStatus(0));
		return model;
	}

	@GetMapping("/login")
	public ModelAndView admin_login() {
		ModelAndView model = new ModelAndView("login");
		model.addObject("role", "qacoordinator");
		model.addObject("displayName", "QA Coordinator");
		return model;
	}

	@PostMapping("/approve")
	@ResponseBody
	public ResponseEntity<ApiResponse> approve_idea(@RequestParam("idea_id") int idea_id,
			@RequestParam("action") int action, HttpServletRequest request) {
		try {
			if (action == 1) {
				im.approve_idea(idea_id);
				String baseUrl = String.format("%s://%s:%d/", request.getScheme(), request.getServerName(),
						request.getServerPort());
				Idea i = im.get_Idea(idea_id);
				mail.sendHtmlEmail(i.getPerson().getEmail(), "Idea approval",
						"Your idea has been approved. Please enjoy your time." + "\n<a href=\"" + baseUrl + "/idea/"
								+ idea_id + "\">Click here to see your idea</a>\""
								+ "\n This is an automatic email, Please do not reply");
				return new ApiResponse().send(HttpStatus.ACCEPTED, "The idea has been approved");
			} else {
				im.denied_idea(idea_id);
				Idea i = im.get_Idea(idea_id);
				String baseUrl = String.format("%s://%s:%d/", request.getScheme(), request.getServerName(),
						request.getServerPort());
				mail.sendHtmlEmail(i.getPerson().getEmail(), "Idea denial",
						"Your idea has been denied for some reasons below.\n"
								+ " - Inappropriate content.\n - Unrelarted to the topic\n" + "\n<a href=\"" + baseUrl
								+ "/idea/" + idea_id + "\">Click here to see your idea</a>\""
								+ "\n This is an automatic email, Please do not reply");
				return new ApiResponse().send(HttpStatus.ACCEPTED, "The idea has been denided");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Error");
		}
	}

	@PostMapping("/authorization")
	@ResponseBody
	public ResponseEntity<ApiResponse> check_login(@RequestParam("username") String user_name,
			@RequestParam("password") String password) {
		Person p = new Person();
		p = am.QACoordinatorLogin(user_name, password);
		if (user_name.isEmpty() && password.isEmpty()) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Please fill all fields");
		} else if (p.getId() != 0) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession(true);
			p.setPerson_picture("/image/" + p.getPerson_picture());
			session.setAttribute("QACoordinator", p);
			return new ApiResponse().send(HttpStatus.ACCEPTED, "/qacoordinator/dashboard");
		} else {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid username and password!");
		}
	}

	@PostMapping("/external_login")
	@ResponseBody
	public ResponseEntity<ApiResponse> external_login(@RequestParam("email") String email,
			RedirectAttributes redirectAttributes) {
		Person p = elm.isExist(email);
		if (p.getId() != 0) {
			if (p.getPerson_role() != 4) {
				return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR,
						"Your QA Coordinator account doesn't exist, contact administrator for more technical supports");
			} else {
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
						.getRequest();
				HttpSession session = request.getSession(true);
				session.setAttribute("QACoordinator", p);
				return new ApiResponse().send(HttpStatus.ACCEPTED, "/qacoordinator/dashboard");
			}
		} else {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR,
					"Your QA Coordinator account doesn't exist, contact administrator for more technical supports");
		}
	}
}
