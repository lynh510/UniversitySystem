package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.system.entity.*;;

public class CommentManagement {
	private PersonManagement pm;

	public CommentManagement() {
		pm = new PersonManagement();
	}

	public List<Comment> getCommentByIdea(int idea_id) {
		List<Comment> comments = new ArrayList();
		String sqlQuery = "Select * from Comments where idea_id = " + idea_id;
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Comment c = new Comment();
				c.setComment_id(rs.getInt("comment_id"));
				c.setPerson(pm.getPerson(rs.getInt("person_id")));
				c.setComment_time(rs.getTimestamp("comment_time"));
				c.setComment_text(rs.getString("comment_text"));
				comments.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comments;
	}

	public void insertComment(Comment c) {
		String sqlQuery = "Insert into Comments values (?,?,?,getdate())";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, c.getIdea().getId());
			statement.setInt(2, c.getPerson().getId());
			statement.setString(3, c.getComment_text());
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int countComments(int idea_id) {
		String sqlQuery = "select count(*) from Comments where idea_id = " + idea_id;
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
			// TODO: handle exception
		}
	}

	public static final List<Long> times = Arrays.asList(TimeUnit.DAYS.toMillis(365), TimeUnit.DAYS.toMillis(30),
			TimeUnit.DAYS.toMillis(1), TimeUnit.HOURS.toMillis(1), TimeUnit.MINUTES.toMillis(1),
			TimeUnit.SECONDS.toMillis(1));
	public static final List<String> timesString = Arrays.asList("year", "month", "day", "hour", "minute", "second");

	private String toDuration(long duration) {

		StringBuffer res = new StringBuffer();
		for (int i = 0; i < times.size(); i++) {
			Long current = times.get(i);
			long temp = duration / current;
			if (temp > 0) {
				res.append(temp).append(" ").append(timesString.get(i)).append(temp != 1 ? "s" : "").append(" ago");
				break;
			}
		}
		if ("".equals(res.toString()))
			return "0 seconds ago";
		else
			return res.toString();
	}

	public String toRelative(Date start) {
		Date now = new Date();
		return toDuration(now.getTime() - start.getTime());
	}
}
