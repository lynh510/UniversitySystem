package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		List<Comment> comments = new ArrayList<Comment>();
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
				c.setMode(rs.getInt("mode"));
				comments.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comments;
	}

	public List<Comment> getComments(int idea_id, int position, int role) {
		List<Comment> comments = new ArrayList<Comment>();
		int offset = 5 * (position - 1);
		long timespan = getTimeSpan();
		String sqlQuery = "";
		if (role == 0) {
			sqlQuery = "select c.comment_id,c.idea_id,c.comment_text,c.comment_time,c.person_id,c.mode from Comment c join Person p on p.person_id = c.person_id where c.idea_id = ? and p.person_role = 0 ORDER BY c.comment_time OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY";
		} else {
			sqlQuery = "SELECT * FROM Comment where idea_id = ? ORDER BY comment_time OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY";
		}
		try {
			Person user = pm.getUserSession();
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, idea_id);
			statement.setInt(2, offset);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Comment c = new Comment();
				c.setComment_id(rs.getInt("comment_id"));
				if (rs.getInt("mode") == 1) {
					if (user.getId() == rs.getInt("person_id")) {
						c.setPerson(pm.getPerson(rs.getInt("person_id")));
					} else {
						Person p = new Person();
						p.setId(rs.getInt("person_id"));
						p.setPerson_name("Anonymous");
						p.setPerson_picture("/image/default_avatar.png");
						c.setPerson(p);
					}
				} else {
					c.setPerson(pm.getPerson(rs.getInt("person_id")));
				}
				c.setComment_time(new Date(rs.getTimestamp("comment_time").getTime() + timespan));
				c.setComment_text(rs.getString("comment_text"));
				c.setMode(rs.getInt("mode"));
				comments.add(c);
			}
		} catch (NullPointerException e) {
			try {
				Connection connection = DataProcess.getConnection();
				PreparedStatement statement = connection.prepareStatement(sqlQuery);
				statement.setInt(1, idea_id);
				statement.setInt(2, offset);
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					Comment c = new Comment();
					c.setComment_id(rs.getInt("comment_id"));
					if (rs.getInt("mode") == 1) {
						Person p = new Person();
						p.setId(rs.getInt("person_id"));
						p.setPerson_name("Anonymous");
						p.setPerson_picture("/image/default_avatar.png");
						c.setPerson(p);
					} else {
						c.setPerson(pm.getPerson(rs.getInt("person_id")));
					}
					c.setComment_time(new Date(rs.getTimestamp("comment_time").getTime() + timespan));
					c.setComment_text(rs.getString("comment_text"));
					c.setMode(rs.getInt("mode"));
					comments.add(c);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comments;
	}

	public int insertComment(Comment c) {
		String sqlQuery = "Insert into Comment values (?,?,?,getdate(),?); SELECT SCOPE_IDENTITY()";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, c.getIdea().getId());
			statement.setInt(2, c.getPerson().getId());
			statement.setString(3, c.getComment_text());
			statement.setInt(4, c.getMode());
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

	public int noOfComments(int idea_id) {
		int noOfComments = (int) Math.ceil(countCommentsPerIdea(idea_id) * 1.0 / 5);
		return noOfComments;
	}

	public void edit_commnent(int comment_id, String comment_text) {
		String sqlQuery = "update Comment set comment_text = ? where comment_id = ?";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, comment_text);
			statement.setInt(2, comment_id);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete_comment(int comment_id) {
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement("delete from Comment where comment_id = ?");
			statement.setInt(1, comment_id);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
