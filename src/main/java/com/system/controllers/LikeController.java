package com.system.controllers;

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

import com.system.ApiResponse;
import com.system.entity.*;
import com.system.models.LikeManagement;

@Controller
@RequestMapping("/like")
public class LikeController {
	private LikeManagement lm;

	public LikeController() {
		lm = new LikeManagement();
	}

	@PostMapping("/like")
	@ResponseBody
	public ResponseEntity<ApiResponse> thumb_up(@RequestParam("like") int like, @RequestParam("idea") int idea_id) {
		try {
			Emoji e = null;
			Idea idea = new Idea();
			idea.setId(idea_id);
			e = new Emoji(like, "Like");
			Person p = getUserSession();
			Idea_Emoji idea_Emoji = new Idea_Emoji(0, e, idea, p);
			int check_is_liked = lm.isLiked(p.getId(), idea_id);
			if (check_is_liked == 1) {
				if (like == 1) {
					lm.unlike(like, idea_id, p.getId());
				} else {
					lm.update_like(like, idea_id, p.getId());
				}
			} else if (check_is_liked == 2) {
				if (like == 2) {
					lm.unlike(like, idea_id, p.getId());
				} else {
					lm.update_like(like, idea_id, p.getId());
				}
			} else if (check_is_liked == 0) {
				lm.insert_like(idea_Emoji);
			}
			return new ApiResponse().send(HttpStatus.ACCEPTED, "Liked");
		} catch (Exception e) {
			return new ApiResponse().send(HttpStatus.FORBIDDEN, "You have to login first");
		}

	}

	private Person getUserSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession(false);
		return (Person) session.getAttribute("user");
	}

}
