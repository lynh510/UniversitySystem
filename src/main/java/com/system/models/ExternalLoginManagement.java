package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.system.entity.*;

public class ExternalLoginManagement {
	private PersonManagement pm;

	public ExternalLoginManagement() {
		pm = new PersonManagement();
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
				p = pm.getPerson(rs.getInt(1));
			}
		} catch (NullPointerException e) {

		} catch (SQLException e) {

		}
		return p;
	}

}
