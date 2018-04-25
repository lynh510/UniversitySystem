package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.system.entity.Person;

public class PersonManagement {
	private DepartmentManagement dm;

	public PersonManagement() {
		dm = new DepartmentManagement();
	}

	public Person getPerson(int id) {
		String sqlQuery = "select * from Person where person_id = " + id;
		Person p = new Person();
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				p.setId(id);
				p.setPerson_picture("/image/" + rs.getString(2));
				p.setPerson_name(rs.getString("person_name"));
				p.setPerson_role(rs.getInt("person_role"));
				p.setBirthdate(rs.getDate("birthdate"));
				p.setGender(rs.getInt("gender"));
				p.setStatus(rs.getInt("_status"));
				p.setPhone(rs.getString("phone_number"));
				p.setEnroll_date(rs.getDate("enroll_date"));
				p.setEmail(rs.getString("email"));
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

	public List<Person> getPerson() {
		List<Person> users = new ArrayList<>();
		try {
			String sqlQuery = "select * from Person";
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Person p = new Person();
				p.setId(rs.getInt("person_id"));
				p.setPerson_picture("/image/" + rs.getString(2));
				p.setPerson_name(rs.getString("person_name"));
				p.setPerson_role(rs.getInt("person_role"));
				p.setBirthdate(rs.getDate("birthdate"));
				p.setGender(rs.getInt("gender"));
				p.setStatus(rs.getInt("_status"));
				p.setPhone(rs.getString("phone_number"));
				p.setEnroll_date(rs.getDate("enroll_date"));
				p.setAddress(rs.getString("_address"));
				p.setEmail(rs.getString("email"));
				p.setDepartment(dm.getDepartment(rs.getInt("dept_id")));
				users.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
}
