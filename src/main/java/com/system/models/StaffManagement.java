package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.system.entity.ExternalUser;
import com.system.entity.Person;
import com.system.entity.Staff;

public class StaffManagement {
	private ExternalLoginManagement elm;

	public StaffManagement() {
		elm = new ExternalLoginManagement();
	}

	// returns string message from database,
	public String staffRegistration(Staff s) {
		String insertSQuery = "DECLARE @responseMessage NVARCHAR(250);\r\n"
				+ "Exec add_staff ?,?,?, @responseMessage output;\r\n"
				+ "select @responseMessage as N'@responseMessage'";
		String message = "";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(insertSQuery);
			// int person_id = insert_person(s.getStaff_id());
			statement.setInt(1, s.getStaff_id().getId());
			statement.setString(2, s.getUsername());
			statement.setString(3, s.getStaff_password());
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				message = resultSet.getString(1);
			}
			ExternalUser eu = new ExternalUser(s.getStaff_id().getId(), s.getStaff_id().getEmail());
			elm.insert_external_user(eu);
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			return message;
		}
	}

	public int insert_person(Person p) {
		String insertQuery = "insert into Person values (?,?,?,?,?,?,?,getDate(),?,?,?,null);"
				+ " SELECT SCOPE_IDENTITY()";
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
		return checkExisten(username, "Staff", "username");
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

	public String change_password(int qa_id, String old_password, String new_password) {
		String message = "";
		String sqlQuery = "DECLARE @responseMessage NVARCHAR(250);\r\n"
				+ "exec change_password_staff ?,?,?, @responseMessage output;\r\n"
				+ "select @responseMessage as N'@responseMessage'";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, qa_id);
			statement.setString(2, old_password);
			statement.setString(3, new_password);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				message = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return message;
	}
}
