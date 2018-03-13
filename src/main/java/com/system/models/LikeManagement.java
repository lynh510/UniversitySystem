package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.system.entity.Idea_Emoji;

public class LikeManagement {
	public void insert_like(Idea_Emoji ie) {
		try {
			String sqlQuery = "insert into Idea_emojis values (?,?,?)";
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, ie.getEmoji().getId());
			statement.setInt(2, ie.getIdea().getId());
			statement.setInt(3, ie.getPerson().getId());
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int isLiked(int user_id, int idea_id) {
		int found = 0;
		try {
			String sqlQuery = "select * from Idea_emojis where person_id = ? and idea_id = ?";
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, user_id);
			statement.setInt(2, idea_id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				found = rs.getInt(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return found;
	}

	public void update_like(int like_id, int idea_id,int user_id) {
		String sqlQuery = "update Idea_emojis set emo_id = ? where idea_id = ? and person_id = ?";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, like_id);
			statement.setInt(2, idea_id);
			statement.setInt(3, user_id);
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int count_like(int like_id, int idea_id) {
		int count = 0;
		try {
			String sqlQuery = "select count(*) from Idea_emojis where emo_id = ? and idea_id = ?";
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, like_id);
			statement.setInt(2, idea_id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return count;
	}
	public void unlike(int like_id, int idea_id,int user_id) {
		String sqlQuery = "delete from Idea_emojis where emo_id = ? and idea_id = ? and person_id = ?";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, like_id);
			statement.setInt(2, idea_id);
			statement.setInt(3, user_id);
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
