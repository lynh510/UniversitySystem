package com.system.controllers;

import java.util.ArrayList;
import java.util.List;

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
import com.system.Helper;
import com.system.MailApi;
import com.system.entity.Idea;
import com.system.entity.Person;
import com.system.models.AuthorizationManagement;
import com.system.models.ExternalLoginManagement;
import com.system.models.IdeaManagement;
import com.system.models.PersonManagement;
import com.system.models.QACoordinatorManagement;

@Controller
@RequestMapping("/qacoordinator")
public class QACoordinatorController {
	private AuthorizationManagement am;
	private ExternalLoginManagement elm;
	private IdeaManagement im;
	private MailApi mail;
	private Helper helper;
	private QACoordinatorManagement qacm;
	private PersonManagement pm;

	public QACoordinatorController() {
		am = new AuthorizationManagement();
		elm = new ExternalLoginManagement();
		im = new IdeaManagement();
		mail = new MailApi();
		helper = new Helper();
		qacm = new QACoordinatorManagement();
		pm = new PersonManagement();

	}

	@RequestMapping("/dashboard")
	public ModelAndView departmentPage() {
		try {
			ModelAndView model = new ModelAndView("departments");
			Person qa_coordinator = getQACoordinatorSession();
			model.addObject("ideas", im.getIdeasByStatus(0));
			model.addObject("qaCoordinator", qa_coordinator);
			return model;
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/qacoordinator/login");
		}
	}

	@GetMapping("/login")
	public ModelAndView admin_login() {
		ModelAndView model = new ModelAndView("login");
		model.addObject("role", "qacoordinator");
		model.addObject("displayName", "QA Coordinator");
		return model;
	}

	@GetMapping("/edit_account")
	public ModelAndView edit_profile() {
		try {
			Person qaCoor = getQACoordinatorSession();
			Person p = pm.getPerson(qaCoor.getId());
			ModelAndView model = new ModelAndView("edit_account");
			List<Integer> months = new ArrayList<Integer>();
			List<Integer> days = new ArrayList<Integer>();
			List<Integer> years = new ArrayList<Integer>();
			for (int month = 1; month < 13; month++) {
				months.add(month);
			}
			for (int day = 1; day < 32; day++) {
				days.add(day);
			}
			for (int year = 1990; year < 2007; year++) {
				years.add(year);
			}
			String[] str = String.valueOf(p.getBirthdate().toString()).split("-");
			model.addObject("year_of_user", str[0]);
			model.addObject("month_of_user", str[1]);
			model.addObject("day_of_user", str[2]);
			model.addObject("days", days);
			model.addObject("months", months);
			model.addObject("years", years);
			model.addObject("user_id", helper.encryptID(p.getId() + ""));
			model.addObject("qaCoordinator", qaCoor);
			model.addObject("welcome", p);
			model.addObject("navbar", "qacoordinator_navbar.jsp");
			return model;
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/qacoordinator/login");
		}
	}

	@RequestMapping("/change_password")
	public ModelAndView changePassword() {
		ModelAndView model = new ModelAndView("edit_password");
		try {
			Person qa_coordinator = getQACoordinatorSession();
			model.addObject("role", "qacoordinator");
			model.addObject("displayName", "QA Coordinator");
			model.addObject("user", qa_coordinator);
			model.addObject("qaCoordinator", qa_coordinator);
			model.addObject("navbar", "qacoordinator_navbar.jsp");
			return model;
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/qacoordinator/login");
		}
	}

	@PostMapping("/change_password")
	public ResponseEntity<ApiResponse> get_contributions(@RequestParam("old_password") String old_password,
			@RequestParam("new_password") String new_password,
			@RequestParam("confirm_password") String confirm_password, @RequestParam("id") String id) {
		try {
			if (old_password.equals(new_password)) {
				return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR,
						"old password and new password can't be the same");
			} else if (!new_password.equals(confirm_password)) {
				return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR,
						"new password and confirm password don't match");
			} else {
				return new ApiResponse().send(HttpStatus.ACCEPTED,
						qacm.change_password(helper.decodeID(id), old_password, new_password));
			}
		} catch (Exception e) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@PostMapping("/approve")
	@ResponseBody
	public ResponseEntity<ApiResponse> approve_idea(@RequestParam("idea_id") int idea_id,
			@RequestParam("action") int action, @RequestParam("content") String content, HttpServletRequest request) {
		try {
			if (action == 1) {
				im.approve_idea(idea_id);
				String baseUrl = String.format("%s://%s:%d/", request.getScheme(), request.getServerName(),
						request.getServerPort());
				Idea i = im.get_Idea(idea_id);
				mail.sendHtmlEmail(i.getPerson().getEmail(), "Idea approval",
						content + "" + "<br/><a href=\"" + baseUrl + "/idea/" + helper.encryptID(idea_id + "")
								+ "\">Click here to see your idea</a>\""
								+ "<br/> This is an automatic email, Please do not reply");
				return new ApiResponse().send(HttpStatus.ACCEPTED, "The idea has been approved");
			} else {
				im.denied_idea(idea_id);
				Idea i = im.get_Idea(idea_id);
				String baseUrl = String.format("%s://%s:%d/", request.getScheme(), request.getServerName(),
						request.getServerPort());
				mail.sendHtmlEmail(i.getPerson().getEmail(), "Idea denial",
						content + "<br/><a href=\"" + baseUrl + "/idea/" + helper.encryptID(idea_id + "")
								+ "\">Click here to see your idea</a>\""
								+ "<br/> This is an automatic email, Please do not reply");
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

	private Person getQACoordinatorSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession(false);
		if (session.getAttribute("QACoordinator") == null) {
			throw new NullPointerException("Have to login first");
		} else {
			return (Person) session.getAttribute("QACoordinator");
		}
	}

	@GetMapping("/logout")
	public ModelAndView logout(RedirectAttributes redirectAttributes) {
		ModelAndView model = new ModelAndView("redirect:/qacoordinator/login");
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession(false);
			session.removeAttribute("QACoordinator");
		} catch (Exception e) {
			return model;
		}
		return model;
	}

	@RequestMapping("/send_email")
	public ModelAndView send_email() {
		ModelAndView model = new ModelAndView("sendEmail");
		try {
			Person qa_coordinator = getQACoordinatorSession();
			model.addObject("qaCoordinator", qa_coordinator);
			model.addObject("students", pm.getStudent());
			return model;
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/qacoordinator/login");
		}
	}

	@PostMapping("/send_email")
	public ResponseEntity<ApiResponse> send_email_post(
			@RequestParam(value = "emails", required = false) List<String> emails, @RequestParam("title") String title,
			@RequestParam("content") String content) {
		try {
			for (String string : emails) {
				mail.sendHtmlEmail(string, title, content);
			}
			return new ApiResponse().send(HttpStatus.ACCEPTED, "Send mail succesfully");

		} catch (NullPointerException e) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Please select a student");
		}

	}
}
