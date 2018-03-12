package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.system.entity.Person;

public class PersonManagement {
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
