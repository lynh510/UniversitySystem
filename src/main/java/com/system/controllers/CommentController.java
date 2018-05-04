package com.system.controllers;

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

import com.system.ApiResponse;
import com.system.MailApi;
import com.system.entity.*;
import com.system.models.*;

@Controller
@RequestMapping("/comment")
public class CommentController {
	private CommentManagement cm;
	private PersonManagement pm;
	private IdeaManagement im;

	public CommentController() {
		cm = new CommentManagement();
		pm = new PersonManagement();
		im = new IdeaManagement();
	}

	@PostMapping("/submit")
	@ResponseBody
	public ResponseEntity<ApiResponse> submit_comment(@RequestParam("idea_id") int idea_id,
			@RequestParam("text") String comment_text, @RequestParam(value = "mode", required = false) String mode,
			HttpServletRequest request) {
		try {
			Idea idea = im.get_Idea(idea_id);
			if (idea.getStatus() == 4) {
				return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "The idea has been closed");
			} else if (comment_text.equals("")) {
				return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Please fill-in something");
			}
			String baseUrl = String.format("%s://%s:%d/", request.getScheme(), request.getServerName(),
					request.getServerPort());
			Comment comment = new Comment();
			if (mode == null) {
				comment = new Comment(
						cm.insertComment(new Comment(new Idea(idea_id), pm.getUserSession(), comment_text, 0)), null,
						pm.getUserSession(), null, comment_text, 0);
			} else {
				comment = new Comment(
						cm.insertComment(new Comment(new Idea(idea_id), pm.getUserSession(), comment_text, 1)), null,
						pm.getUserSession(), null, comment_text, 1);
			}
			MailApi mail = new MailApi();
			if (mode == null) {
				if (pm.getUserSession().getId() != idea.getPerson().getId()
						&& pm.getUserSession().getPerson_role() == 0) {
					mail.sendHtmlEmail(idea.getPerson().getEmail(),
							pm.getUserSession().getPerson_name() + " commented on your idea",
							"<a href=\"" + baseUrl + "/idea/" + idea_id + "\">Click here to see</a>\""
									+ "<br/> This is an automatic mail, Please do not reply");
				}
			} else {
				mail.sendHtmlEmail(idea.getPerson().getEmail(), "An anonymous user just commented on your idea",
						"<a href=\"" + baseUrl + "/idea/" + idea_id + "\">Click here to see</a>\""
								+ "<br/> This is an automatic mail, Please do not reply");
			}

			return new ApiResponse().sendData(comment, HttpStatus.ACCEPTED,
					"Well done!! Commnet is successfully posted");
		} catch (NullPointerException e) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@PostMapping("/edit")
	@ResponseBody
	public ResponseEntity<ApiResponse> edit_comment(@RequestParam("idea_id") int idea_id,
			@RequestParam("text") String comment_text, @RequestParam(value = "mode", required = false) String mode,
			@RequestParam("comment_id") int comment_id, HttpServletRequest request) {
		try {
			cm.edit_commnent(comment_id, comment_text);
			return new ApiResponse().sendData("", HttpStatus.ACCEPTED, "Well done!! Commnet is successfully edited");
		} catch (NullPointerException e) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@GetMapping("/delete/{comment_id}")
	@ResponseBody
	public ResponseEntity<ApiResponse> delete_comment(@PathVariable("comment_id") int comment_id) {
		try {
			Person person = pm.getUserSession();
			Comment comment = cm.getComment(comment_id);
			if (person.getId() == comment.getPerson().getId()) {
				cm.delete_comment(comment_id);
				return new ApiResponse().sendData("", HttpStatus.ACCEPTED,
						"Well done!! Commnet is successfully deleted");
			} else {
				return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "You are the owner of the comment");
			}
		} catch (NullPointerException e) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@GetMapping("/moreComments/{idea_id}/{no_comment}")
	@ResponseBody
	public ResponseEntity<ApiResponse> get_more_comments(@PathVariable("idea_id") int idea_id,
			@PathVariable("no_comment") int no_comment) {
		try {
			Person p = pm.getUserSession();
			return new ApiResponse().sendData(cm.getComments(idea_id, no_comment, p.getPerson_role()),
					HttpStatus.ACCEPTED, "" + p.getId());
		} catch (NullPointerException e) {
			return new ApiResponse().sendData(cm.getComments(idea_id, no_comment, 0), HttpStatus.ACCEPTED, "0");
		}
	}

}
