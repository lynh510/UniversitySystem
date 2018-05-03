package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
				ay.setStatus(rs.getInt("_status"));
				academicYear.add(ay);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return academicYear;
	}

	public void addAcademicYear(AcademicYear ay) {
		String sqlQuery = "insert into AcademicYear values (?,?,?,?,?,0)";
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
		String sqlQuery = "select * from AcademicYear where academic_year_id = " + id;
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
				ay.setStatus(rs.getInt("_status"));
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
		String sqlQuery = "update AcademicYear set academic_year_start_date = ? , " + "academic_year_end_date = ? , "
				+ "academic_year_final_date = ? , " + "academic_year = ? , season = ? " + "where academic_year_id = ?";
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

	public void check_close_date() {
		Date now = new Date();
		try {
			String sqlQuery = "select * from AcademicYear where _status = 0";
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getDate("academic_year_end_date").before(now)) {
					int academic_year = rs.getInt("academic_year_id");
					close_academic_year(1, academic_year);
					close_department(1, academic_year);
					System.out.println("Closing academic year: " + rs.getInt("academic_year_id"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void check_final_close_date() {
		Date now = new Date();
		try {
			String sqlQuery = "select * from AcademicYear where _status = 1";
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getDate("academic_year_final_date").before(now)) {
					int academic_year = rs.getInt("academic_year_id");
					close_academic_year(2, academic_year);
					close_department(2, academic_year);
					close_idea_by_academicyear(2, academic_year);
					System.out.println("Closed academic year: " + rs.getInt("academic_year_id"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void close_academic_year(int status, int academic_year) {
		String sqlQuery = "update AcademicYear set _status = ? where academic_year_id = ?";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, status);
			statement.setInt(2, academic_year);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void close_department(int status, int academic_year) {
		String sqlQuery = "update Department set dept_status = ? where academic_year_id = ?";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, status);
			statement.setInt(2, academic_year);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void close_idea_by_academicyear(int status, int academic_year) {
		String sqlQuery = "update Idea set _status = ?  from Idea i join Person p on p.person_id =  i.person_id join Department d on d.dept_id = p.dept_id join AcademicYear ay on ay.academic_year_id = d.academic_year_id  where i._status = 1 and ay.academic_year_id =  ?";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, status);
			statement.setInt(2, academic_year);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// public void run() {
	// Timer timer = new Timer();
	// TimerTask hourlyTask = new TimerTask() {
	// @Override
	// public void run() {
	// System.out.println("Checking academic year");
	// }
	// };
	// timer.schedule(hourlyTask, 24, 1000 * 60 * 60);
	// }
}
