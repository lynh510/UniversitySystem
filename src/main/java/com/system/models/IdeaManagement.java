package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.system.entity.*;

public class IdeaManagement {
	private PersonManagement pm;

	public IdeaManagement() {
		pm = new PersonManagement();
	}

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
				idea.setTitle(rs.getString("idea_tile"));
				idea.setContent(rs.getString("idea_content"));
				idea.setPerson(pm.getPerson(rs.getInt("person_id")));
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
		String sqlQuery = "Insert into Idea values(?,?,?,getdate(),?,?,?,?); SELECT SCOPE_IDENTITY()";
		int id = 0;
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, idea.getTitle());
			statement.setString(2, idea.getContent());
			statement.setInt(3, idea.getPerson().getId());
			statement.setDate(4, new java.sql.Date(idea.getClose_date().getTime()));
			statement.setInt(5, idea.getViews());
			statement.setInt(6, idea.getMode());
			statement.setInt(7, idea.getStatus());
			statement.executeUpdate();
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

	public void insert_Idea_tags(Idea_Tag it) {
		String sqlQuery = "Insert into Idea_tags values(?,?)";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, it.getIdea().getId());
			statement.setInt(2, it.getTag().getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insert_Idea_attachfiles(Idea_attachfiles ia) {
		String sqlQuery = "Insert into Idea_attachfiles values(?,?,?)";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, ia.getIdea().getId());
			statement.setString(2, ia.getFile_type());
			statement.setString(3, ia.getLink());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
