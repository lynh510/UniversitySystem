package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.system.entity.ExternalUser;
import com.system.entity.Person;

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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
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
				p.setPerson_picture("/uploads/" + rs.getString(2));
				p.setPerson_name(rs.getString(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return p;
	}
}
