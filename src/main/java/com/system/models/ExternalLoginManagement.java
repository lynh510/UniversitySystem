package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.system.entity.ExternalUser;

public class ExternalLoginManagement {
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

	public boolean isExist(String email) {
		String sqlQuery = "select * from UserExternalLogin where email = ?";
		boolean isExist = false;
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, email);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				isExist = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isExist;
	}
}
