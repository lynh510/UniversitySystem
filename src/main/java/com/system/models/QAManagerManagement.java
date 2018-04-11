package com.system.models;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.system.entity.Person;

public class QAManagerManagement {

	public Person getQAManagerSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession(false);
		if (session.getAttribute("QAManager") == null) {
			throw new NullPointerException("Have to login first");
		} else {
			return (Person) session.getAttribute("QAManager");
		}

	}
}
