package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.system.entity.Person;

public class PersonManagement {
	public Person getPerson(int id) {
		String sqlQuery = "select * from Person where person_id = " + id;
		Person p = new Person();
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				p.setId(id);
				p.setPerson_picture("/uploads/" + rs.getString(2));
				p.setPerson_name(rs.getString(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return p;
	}

	public Person getUserSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession(false);
		if (session.getAttribute("user") == null) {
			throw new NullPointerException("Have to login first");
		} else {
			return (Person) session.getAttribute("user");
		}

	}
}
