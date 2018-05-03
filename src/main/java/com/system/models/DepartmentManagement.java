package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.system.entity.*;

public class DepartmentManagement {
	private AcademicYearManagement aym;

	public DepartmentManagement() {
		aym = new AcademicYearManagement();
	}

	public List<Department> getDepartmentsByAcademicYear(int academic_id) {
		List<Department> departments = new ArrayList<Department>();
		String sqlQuery = "Select * from Department where academic_year_id = " + academic_id;
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Department d = new Department();
				d.setId(rs.getInt("dept_id"));
				d.setDept_name(rs.getString("dept_name"));
				d.setAcademic_year(aym.get(rs.getInt("academic_year_id")));
				d.setStatus(rs.getInt("dept_status"));
				departments.add(d);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return departments;
	}

	public List<Department> getDepartments(int status) {
		List<Department> departments = new ArrayList<Department>();
		String sqlQuery = "Select * from Department where dept_status = " + status;
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Department d = new Department();
				d.setId(rs.getInt("dept_id"));
				d.setDept_name(rs.getString("dept_name"));
				d.setAcademic_year(aym.get(rs.getInt("academic_year_id")));
				d.setStatus(rs.getInt("dept_status"));
				departments.add(d);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return departments;
	}

	public void addDepartment(Department dept) {
		String sqlQuery = "insert into Department values (?,?,0)";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, dept.getDept_name());
			statement.setInt(2, dept.getAcademic_year().getId());
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Department getDepartment(int dept_id) {
		Department d = new Department();
		String sqlQuery = "select * from Department where dept_id = " + dept_id;
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				d.setId(rs.getInt("dept_id"));
				d.setDept_name(rs.getString("dept_name"));
				d.setAcademic_year(aym.get(rs.getInt("academic_year_id")));
				d.setStatus(rs.getInt("dept_status"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}

	public boolean check_duplicate_department(Department d) {
		String sqlQuery = "select * from Department where dept_name = ? and academic_year_id = ?";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, d.getDept_name());
			statement.setInt(2, d.getAcademic_year().getId());
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	public void remove_department(int dept_id) {
		String sqlQuery = "delete from Department where dept_id = ?";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, dept_id);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
