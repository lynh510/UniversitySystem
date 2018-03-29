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
import com.system.entity.Comment;
import com.system.entity.Idea;
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
			@RequestParam("text") String comment_text) {
		try {
			Comment c = cm
					.getComment(cm.insertComment(new Comment(new Idea(idea_id), pm.getUserSession(), comment_text)));
			Idea idea = im.get_Idea(idea_id);
			MailApi mail = new MailApi();
			if (pm.getUserSession().getId() != idea.getPerson().getId()) {
				mail.sendHtmlEmail(idea.getPerson().getEmail(),
						pm.getUserSession().getPerson_name() + " commented on your idea",
						"<a href=\"" + "http://localhost:8080/idea/" + idea_id + "\">Click here to see</a>\"");
			}
			return new ApiResponse().sendData(c, HttpStatus.ACCEPTED, "Well done!! Commnet is successfully posted");
		} catch (NullPointerException e) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@GetMapping("/moreComments/{idea_id}/{no_comment}")
	@ResponseBody
	public ResponseEntity<ApiResponse> get_more_comments(@PathVariable("idea_id") int idea_id,
			@PathVariable("no_comment") int no_comment) {
		try {
			// calculate how many time have to click on more comments
			// int noOfComments = (int) Math.ceil(cm.countCommentsPerIdea(idea_id) * 1.0 /
			// 5);
			return new ApiResponse().sendData(cm.getComments(idea_id, no_comment), HttpStatus.ACCEPTED, "");
		} catch (NullPointerException e) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
