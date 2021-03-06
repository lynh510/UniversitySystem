package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.system.entity.Person;

public class QAManagerManagement {

	public Person getQAManagerSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession(false);
		if (session.getAttribute("QAManager") == null) {
			throw new NullPointerException("Have to login first");
		} else {
			return (Person) session.getAttribute("QAManager");
		}
	}

	public String change_password(int qa_id, String old_password, String new_password) {
		String message = "";
		String sqlQuery = "DECLARE @responseMessage NVARCHAR(250);\r\n"
				+ "exec change_password_qamanager ?,?,?, @responseMessage output;\r\n"
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
