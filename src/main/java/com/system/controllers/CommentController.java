package com.system.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.system.ApiResponse;
import com.system.entity.Comment;
import com.system.entity.Idea;
import com.system.models.*;

@Controller
@RequestMapping("/comment")
public class CommentController {
	private CommentManagement cm;
	private PersonManagement pm;

	public CommentController() {
		cm = new CommentManagement();
		pm = new PersonManagement();
	}

	@PostMapping("/submit")
	@ResponseBody
	public ResponseEntity<ApiResponse> submit_comment(@RequestParam("idea_id") int idea_id,
			@RequestParam("text") String comment_text) {
		try {
			 cm.insertComment(new Comment(new Idea(idea_id), pm.getUserSession(),
			 comment_text));
			return new ApiResponse().send(HttpStatus.ACCEPTED, "Well done!! Commnet is successfully posted");
		} catch (NullPointerException e) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}

	}
}
