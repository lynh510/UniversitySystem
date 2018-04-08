package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.system.entity.*;;

public class CommentManagement {
	private PersonManagement pm;

	public CommentManagement() {
		pm = new PersonManagement();
	}

	public List<Comment> getCommentByIdea(int idea_id) {
		List<Comment> comments = new ArrayList();
		String sqlQuery = "Select * from Comment where idea_id = " + idea_id;
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

	public List<Comment> getComments(int idea_id, int position, int role) {
		List<Comment> comments = new ArrayList();
		int offset = 5 * (position - 1);
		long timespan = getTimeSpan();
		String sqlQuery = "";
		if (role == 0) {
			sqlQuery = "select c.comment_id,c.idea_id,c.comment_text,c.comment_time,c.person_id from Comment c join Person p on p.person_id = c.person_id where c.idea_id = ? and p.person_role = 0 ORDER BY c.comment_time OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY";
		} else {
			sqlQuery = "SELECT * FROM Comment where idea_id = ? ORDER BY comment_time OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY";
		}
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, idea_id);
			statement.setInt(2, offset);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Comment c = new Comment();
				c.setComment_id(rs.getInt("comment_id"));
				c.setPerson(pm.getPerson(rs.getInt("person_id")));
				c.setComment_time(new Date(rs.getTimestamp("comment_time").getTime() + timespan));
				c.setComment_text(rs.getString("comment_text"));
				comments.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comments;
	}

	public int insertComment(Comment c) {
		String sqlQuery = "Insert into Comment values (?,?,?,getdate()); SELECT SCOPE_IDENTITY()";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, c.getIdea().getId());
			statement.setInt(2, c.getPerson().getId());
			statement.setString(3, c.getComment_text());
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public Comment getComment(int id) {
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement("select * from Comment where comment_id = ?");
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				Comment c = new Comment();
				c.setComment_id(rs.getInt("comment_id"));
				c.setPerson(pm.getPerson(rs.getInt("person_id")));
				c.setComment_text(rs.getString("comment_text"));
				c.setComment_time(rs.getTimestamp("comment_time"));
				return c;
			} else {
				return new Comment();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public long getTimeSpan() {
		try {
			CommentManagement cm = new CommentManagement();
			long timespan = System.currentTimeMillis() - cm.getServerTime().getTime();
			return timespan;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int noOfComments(int idea_id) {
		int noOfComments = (int) Math.ceil(countCommentsPerIdea(idea_id) * 1.0 / 5);
		return noOfComments;
	}

	public Date getServerTime() throws SQLException {
		Date d = null;
		Connection connection = DataProcess.getConnection();
		PreparedStatement statement = connection.prepareStatement("SELECT getdate()");
		ResultSet rs = statement.executeQuery();
		if (rs.next()) {
			return d = new Date(rs.getTimestamp(1).getTime());
		} else {
			return d;
		}
	}

	public int countCommentsPerIdea(int idea_id) {
		String sqlQuery = "";
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession(false);
		if (session.getAttribute("user") == null) {
			sqlQuery = "select count(*) from Comment c join Person p on p.person_id = c.person_id where p.person_role = 0 and c.idea_id = "
					+ idea_id;
		} else {
			Person u = (Person) session.getAttribute("user");
			int person_role = u.getPerson_role();
			if (person_role == 0) {
				sqlQuery = "select count(*) from Comment c join Person p on p.person_id = c.person_id where p.person_role = 0 and c.idea_id = "
						+ idea_id;
			} else {
				sqlQuery = "select count(*) from Comment where idea_id = " + idea_id;
			}
		}
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

	public String toRelative2(long start) {
		Date now = new Date();
		return toDuration(now.getTime() - start);
	}
}
