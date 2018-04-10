package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.system.entity.*;

public class DepartmentManagement {
	public List<Department> getDepartments() {
		List<Department> departments = new ArrayList();
		String sqlQuery = "Select * from Department";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Department d = new Department();
				d.setId(rs.getInt("dept_id"));
				d.setDept_name(rs.getString("dept_name"));
				departments.add(d);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return departments;
	}
}
