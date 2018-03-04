package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.system.entity.*;

public class IdeaManagement {
	public List<Idea> getIdeasPerPage(int currentPage, int itemPerPage) {
		List<Idea> ideaList = new ArrayList<>();
		int offset = itemPerPage * (currentPage - 1);
		String sqlQuery = "SELECT * FROM Idea ORDER BY post_date OFFSET " + offset + " ROWS FETCH NEXT " + itemPerPage
				+ " ROWS ONLY";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Idea idea = new Idea();
				idea.setId(rs.getInt("idea_id"));
				idea.setDescription(rs.getString("idea_des"));
				idea.setPerson(null);
				idea.setPost_date(rs.getDate("post_date"));
				idea.setClose_date(rs.getDate("close_date"));
				idea.setViews(rs.getInt("idea_views"));
				idea.setStatus(rs.getInt("_status"));
				ideaList.add(idea);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ideaList;
	}

	public int noOfRecords() {
		int result = 0;
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement("select count(*) from Idea");
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
	}

	public int insert_idea(Idea idea) {
		String sqlQuery = "Insert into Idea values(?,?,getdate(),?,?,?)";
		int id = 0;
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, idea.getDescription());
			statement.setInt(2, idea.getPerson().getId());
			statement.setDate(3, new java.sql.Date(idea.getClose_date().getTime()));
			statement.setInt(4, idea.getViews());
			statement.setInt(5, idea.getStatus());
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			id = 0;
		}
		return id;
	}

}
