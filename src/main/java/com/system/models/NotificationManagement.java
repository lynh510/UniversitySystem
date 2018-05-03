package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.system.entity.*;

public class NotificationManagement {
	public void insert_notifycation(Notification n) {
		String sqlQuery = "insert into _Notification values(0,?,?,?,?,?,getdate())";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, n.getSender().getId());
			statement.setInt(2, n.getRecipient().getId());
			statement.setInt(3, n.getActivity().getId());
			statement.setString(4, n.getDescription());
			statement.setString(5, n.getUrl());
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
