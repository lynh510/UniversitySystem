package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.system.entity.*;

public class StudentManagement {
	private ExternalLoginManagement elm;

	public StudentManagement() {
		elm = new ExternalLoginManagement();
	}

	// returns 0 is for registration successful, 1 is fail due to error ,
	public int studentRegistration(Student s) {
		String insertSQuery = "insert into Student values (?,?,?)";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(insertSQuery);
			int person_id = insert_person(s.getStudent_id());
			statement.setInt(1, person_id);
			statement.setString(2, s.getUsername());
			statement.setString(3, s.getStudent_password());
			statement.executeUpdate();
			ExternalUser eu = new ExternalUser(person_id, s.getStudent_id().getEmail());
			elm.insert_external_user(eu);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}

	public int insert_person(Person p) {
		String insertQuery = "insert into Person values (?,?,?,?,?,?,?,getDate(),?,?,?);" + " SELECT SCOPE_IDENTITY()";
		int pid = 0;
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(insertQuery);
			statement.setString(1, p.getPerson_picture());
			statement.setString(2, p.getPerson_name());
			statement.setInt(3, p.getPerson_role());
			statement.setDate(4, p.getBirthdate());
			statement.setInt(5, p.getGender());
			statement.setInt(6, p.getStatus());
			statement.setString(7, p.getPhone());
			statement.setString(8, p.getAddress());
			statement.setString(9, p.getEmail());
			statement.setString(10, p.getDescription());
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				pid = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pid;
	}

	public boolean isUserNameExisted(String username) {
		return checkExisten(username, "Student", "username");
	}

	public boolean isEmailExist(String email) {
		return checkExisten(email, "Person", "email");
	}

	private boolean checkExisten(String param, String entity, String condition) {
		boolean flag = false;
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection
					.prepareStatement("Select * from " + entity + " where " + condition + " = ?");
			statement.setString(1, param);
			ResultSet rs = statement.executeQuery();
			if (!rs.next()) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public int check_login(Student s) {
		String sqlQuery = "Select * from Student where username = ? and student_password = ?";
		int result = 0;
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, s.getUsername());
			statement.setString(2, s.getStudent_password());
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
