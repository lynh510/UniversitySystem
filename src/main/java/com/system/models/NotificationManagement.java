package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.system.entity.Tag;

public class NotificationManagement {
	public void insert_tag(Tag t) {
		String sqlQuery = "insert into Tag values(?,?,0)";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, t.getDescription());
			statement.setInt(2, t.getDepartment().getId());
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
