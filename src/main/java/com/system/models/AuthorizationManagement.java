package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.system.entity.Person;

public class AuthorizationManagement {
	public Person QAManagerLogin(String username, String password) {
		String sqlQuery = "exec QAManager_login ?, ?";
		Person p = new Person();
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, username);
			statement.setString(2, password);
			p = getPerson(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}

	public Person StaffLogin(String username, String password) {
		String sqlQuery = "exec staff_login ?, ?";
		Person p = new Person();
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, username);
			statement.setString(2, password);
			p = getPerson(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}

	public Person AdminLogin(String username, String password) {
		String sqlQuery = "exec admin_login ?, ?";
		Person p = new Person();
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, username);
			statement.setString(2, password);
			p = getPerson(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}

	public Person QACoordinatorLogin(String username, String password) {
		String sqlQuery = "exec QACoordinator_login ?, ?";
		Person p = new Person();
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, username);
			statement.setString(2, password);
			p = getPerson(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}

	private Person getPerson(PreparedStatement statement) throws SQLException {
		Person p = new Person();
		ResultSet rs = statement.executeQuery();
		if (rs.next()) {
			p.setId(rs.getInt("person_id"));
			p.setPerson_picture(rs.getString("person_picture"));
			p.setPerson_name(rs.getString("person_name"));
			p.setPerson_role(rs.getInt("person_role"));
			p.setBirthdate(rs.getDate("birthdate"));
			p.setGender(rs.getInt("gender"));
			p.setPhone(rs.getString("phone_number"));
			p.setAddress(rs.getString("_address"));
			p.setEmail(rs.getString("email"));
		}
		return p;
	}

}
