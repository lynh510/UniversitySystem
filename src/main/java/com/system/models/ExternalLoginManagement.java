package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.system.entity.*;

public class ExternalLoginManagement {
	private PersonManagement pm;
	private DepartmentManagement dm;

	public ExternalLoginManagement() {
		pm = new PersonManagement();
		dm = new DepartmentManagement();
	}

	public void insert_external_user(ExternalUser eu) {
		String sqlQuery = "insert into UserExternalLogin values (?,?)";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, eu.getUser_id());
			statement.setString(2, eu.getEmail());
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Person isExist(String email) {
		String sqlQuery = "select * from UserExternalLogin where email = ?";
		Person p = new Person();
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, email);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				p = getPerson(rs.getInt(1));
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}

	private Person getPerson(int id) {
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
				p.setDepartment(dm.getDepartment(rs.getInt("dept_id")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return p;
	}

}
