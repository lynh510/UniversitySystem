package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.system.entity.*;

public class IdeaManagement {
	private PersonManagement pm;

	public IdeaManagement() {
		pm = new PersonManagement();
	}

	public List<Idea> getIdeasPerPage(int currentPage, int itemPerPage) {
		List<Idea> ideaList = new ArrayList<>();
		int offset = itemPerPage * (currentPage - 1);
		
		String sqlQuery = "SELECT * FROM Idea Where _status = 0 ORDER BY post_date OFFSET " + offset + " ROWS FETCH NEXT " + itemPerPage
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
				idea.setMode(rs.getInt("mode"));
				ideaList.add(idea);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ideaList;
	}

	public List<Idea> getIdeasPerPageByPersonal(int currentPage, int itemPerPage,int person_id) {
		List<Idea> ideaList = new ArrayList<>();
		int offset = itemPerPage * (currentPage - 1);
		String sqlQuery = "SELECT * FROM Idea Where person_id = ? and _status = 0 ORDER BY post_date DESC";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, person_id);
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
		String sqlQuery = "Insert into Idea_attachfiles values(?,?,?,?,?)";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, ia.getIdea().getId());
			statement.setString(2, ia.getOld_name());
			statement.setString(3, ia.getNew_name());
			statement.setString(4, ia.getFile_type());
			statement.setString(5, ia.getLink());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean check_idea_belong(int user_id) {
		if (getUserSession().getId() == user_id) {
			return true;
		} else {
			return false;
		}
	}

	private Person getUserSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession(false);
		if (session.getAttribute("user") == null) {
			//throw new NullPointerException("Have to login first");
			return new Person();
		} else {
			return (Person) session.getAttribute("user");
		}
	}

	public void eidt_idea(Idea idea) {
		String sqlQuery = "Update Idea Set idea_tile = ?, idea_content = ? where idea_id = ?";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, idea.getTitle());
			statement.setString(2, idea.getContent());
			statement.setInt(3, idea.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete_idea(int idea_id) {
		String sqlQuery = "Update Idea Set _status = ? where idea_id = ?";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, 2);
			statement.setInt(2, idea_id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
