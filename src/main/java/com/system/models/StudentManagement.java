package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.system.entity.*;

public class StudentManagement {
	private ExternalLoginManagement elm;
	private DepartmentManagement dm;

	public StudentManagement() {
		elm = new ExternalLoginManagement();
		dm = new DepartmentManagement();
	}

	// returns string message from database,
	public String studentRegistration(Student s) {
		String insertSQuery = "DECLARE @responseMessage NVARCHAR(250);\r\n"
				+ "Exec add_student ?,?,?, @responseMessage output;\r\n"
				+ "select @responseMessage as N'@responseMessage'";
		String message = "";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(insertSQuery);
			// int person_id = insert_person(s.getStudent_id());
			statement.setInt(1, s.getStudent_id().getId());
			statement.setString(2, s.getUsername());
			statement.setString(3, s.getStudent_password());
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				message = resultSet.getString(1);
			}
			ExternalUser eu = new ExternalUser(s.getStudent_id().getId(), s.getStudent_id().getEmail());
			elm.insert_external_user(eu);
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			return message;
		}
	}

	public int insert_person(Person p) {
		String insertQuery = "insert into Person values (?,?,?,?,?,?,?,getDate(),?,?,?,?);"
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
			statement.setInt(11, p.getDepartment().getId());
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

	public Person check_login(Student s) {
		String sqlQuery = "Exec student_login ?,?";
		Person p = new Person();
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, s.getUsername());
			statement.setString(2, s.getStudent_password());
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				p.setId(rs.getInt("person_id"));
				p.setPerson_picture(rs.getString("person_picture"));
				p.setPerson_name(rs.getString("person_name"));
				p.setPerson_role(rs.getInt("person_role"));
				p.setStatus(rs.getInt("_status"));
				p.setDepartment(dm.getDepartment(rs.getInt("dept_id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}

	public String change_password(int qa_id, String old_password, String new_password) {
		String message = "";
		String sqlQuery = "DECLARE @responseMessage NVARCHAR(250);\r\n"
				+ "exec change_password_student ?,?,?, @responseMessage output;\r\n"
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
