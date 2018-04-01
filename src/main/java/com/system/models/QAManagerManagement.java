package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.system.entity.Person;

public class QAManagerManagement {

	public Person QAManagerLogin(String username, String password) {
		String sqlQuery = "exec QAManager_login ?, ?";
		Person p = new Person();
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, username);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				p.setId(rs.getInt("person_id"));
				p.setPerson_picture(rs.getString("person_picture"));
				p.setPerson_name(rs.getString("person_name"));
				p.setPerson_role(rs.getInt("person_role"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
}
