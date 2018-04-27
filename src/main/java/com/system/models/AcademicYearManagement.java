package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.system.entity.*;

public class AcademicYearManagement {

	public List<AcademicYear> getAcademicYear() {
		List<AcademicYear> academicYear = new ArrayList<AcademicYear>();
		String sqlQuery = "Select * from AcademicYear";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				AcademicYear ay = new AcademicYear();
				ay.setId(rs.getInt("academic_year_id"));
				ay.setStart_date(rs.getDate("academic_year_start_date"));
				ay.setEnd_date(rs.getDate("academic_year_end_date"));
				ay.setFinal_date(rs.getDate("academic_year_final_date"));
				ay.setYear(rs.getInt("academic_year"));
				ay.setSeason(rs.getString("season"));
				academicYear.add(ay);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return academicYear;
	}

	public void addAcademicYear(AcademicYear ay) {
		String sqlQuery = "insert into AcademicYear values (?,?,?,?,?)";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setDate(1, new java.sql.Date(ay.getStart_date().getTime()));
			statement.setDate(2, new java.sql.Date(ay.getEnd_date().getTime()));
			statement.setDate(3, new java.sql.Date(ay.getFinal_date().getTime()));
			statement.setInt(4, ay.getYear());
			statement.setString(5, ay.getSeason());
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AcademicYear get(int id) {
		AcademicYear ay = new AcademicYear();
		String sqlQuery = "select * from AcademicYear";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				ay.setId(rs.getInt("academic_year_id"));
				ay.setStart_date(rs.getDate("academic_year_start_date"));
				ay.setEnd_date(rs.getDate("academic_year_end_date"));
				ay.setFinal_date(rs.getDate("academic_year_final_date"));
				ay.setYear(rs.getInt("academic_year"));
				ay.setSeason(rs.getString("season"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ay;
	}

	public boolean deleteAcademicYear(int academic_year) {
		String sqlQuery = "delete from AcademicYear where academic_year_id = " + academic_year;
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void updateAcademicYear(AcademicYear ay) {
		String sqlQuery = "update AcademicYear set academic_year_start_date = ? , "
				+ "academic_year_end_date = ? , " + "academic_year_final_date = ? , "
				+ "academic_year = ? , season = ? " + "where academic_year_id = ?";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setDate(1, new java.sql.Date(ay.getStart_date().getTime()));
			statement.setDate(2, new java.sql.Date(ay.getEnd_date().getTime()));
			statement.setDate(3, new java.sql.Date(ay.getFinal_date().getTime()));
			statement.setInt(4, ay.getYear());
			statement.setString(5, ay.getSeason());
			statement.setInt(6, ay.getId());
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
