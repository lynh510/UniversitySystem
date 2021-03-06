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
		String sqlQuery = "SELECT * FROM Idea Where _status = 1 or _status = 2 or _status = 4  ORDER BY post_date DESC OFFSET "
				+ offset + " ROWS FETCH NEXT " + itemPerPage + " ROWS ONLY";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Idea idea = new Idea();
				idea.setId(rs.getInt("idea_id"));
				idea.setTitle(rs.getString("idea_title"));
				idea.setContent(rs.getString("idea_content"));
				idea.setStatus(rs.getInt("_status"));
				idea.setMode(rs.getInt("mode"));
				if (rs.getInt("mode") == 0) {
					Person p = pm.getPerson(rs.getInt("person_id"));
					p.setPerson_name("Anonymous");
					p.setPerson_picture("/uploads/default_avatar.png");
					idea.setPerson(p);
				} else {
					idea.setPerson(pm.getPerson(rs.getInt("person_id")));
				}
				idea.setPost_date(rs.getDate("post_date"));
				idea.setViews(rs.getInt("idea_views"));
				ideaList.add(idea);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ideaList;
	}

	public List<Idea> searchIdea(String search, int currentPage, int itemPerPage) {
		List<Idea> ideaList = new ArrayList<>();
		int offset = itemPerPage * (currentPage - 1);
		String sqlQuery = "SELECT * FROM Idea Where (_status = 1 or _status = 2 or _status = 4)  and (idea_title like ? or idea_content like ?)  ORDER BY post_date DESC OFFSET "
				+ offset + " ROWS FETCH NEXT " + itemPerPage + " ROWS ONLY";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, "%" + search + "%");
			statement.setString(2, "%" + search + "%");
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Idea idea = new Idea();
				idea.setId(rs.getInt("idea_id"));
				idea.setTitle(rs.getString("idea_title"));
				idea.setContent(rs.getString("idea_content"));
				idea.setStatus(rs.getInt("_status"));
				idea.setMode(rs.getInt("mode"));
				if (rs.getInt("mode") == 0) {
					Person p = pm.getPerson(rs.getInt("person_id"));
					p.setPerson_name("Anonymous");
					p.setPerson_picture("/uploads/default_avatar.png");
					idea.setPerson(p);
				} else {
					idea.setPerson(pm.getPerson(rs.getInt("person_id")));
				}
				idea.setPost_date(rs.getDate("post_date"));
				idea.setViews(rs.getInt("idea_views"));

				ideaList.add(idea);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ideaList;
	}

	public List<Idea> getIdeasPerPageByPersonal(int currentPage, int itemPerPage, int person_id) {
		List<Idea> ideaList = new ArrayList<>();
		int offset = itemPerPage * (currentPage - 1);
		String sqlQuery = "SELECT * FROM Idea Where person_id = ? ORDER BY post_date DESC OFFSET " + offset
				+ " ROWS FETCH NEXT " + itemPerPage + " ROWS ONLY";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, person_id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Idea idea = new Idea();
				idea.setId(rs.getInt("idea_id"));
				idea.setTitle(rs.getString("idea_title"));
				idea.setContent(rs.getString("idea_content"));
				idea.setPerson(pm.getPerson(rs.getInt("person_id")));
				idea.setPost_date(rs.getDate("post_date"));
				idea.setViews(rs.getInt("idea_views"));
				idea.setStatus(rs.getInt("_status"));
				ideaList.add(idea);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ideaList;
	}

	public int noOfRecords(int user_id) {
		int result = 0;
		String sqlQuery = "select count(*) from Idea where _status = 1 or _status = 2 or _status = 4";
		if (user_id != 0) {
			sqlQuery = "select count(*) from Idea where person_id = " + user_id;
		}
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
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

	public int countSearch(String keywords) {
		int result = 0;
		String sqlQuery = "SELECT count(*) FROM Idea Where (_status = 1 or _status = 2 or _status = 4) and (idea_title like ? or idea_content like ?) ";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, "%" + keywords + "%");
			statement.setString(2, "%" + keywords + "%");
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
		String sqlQuery = "Insert into Idea values(?,?,?,getdate(),?,?,?); SELECT SCOPE_IDENTITY()";
		int id = 0;
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, idea.getTitle());
			statement.setString(2, idea.getContent());
			statement.setInt(3, idea.getPerson().getId());
			statement.setInt(4, idea.getViews());
			statement.setInt(5, idea.getMode());
			statement.setInt(6, idea.getStatus());
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
		String sqlQuery = "Insert into Idea_tag values(?,?)";
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
		String sqlQuery = "Insert into Idea_attachfile values(?,?,?,?,?)";
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

	public Idea get_Idea(int idea_id) {
		String sqlQuery = "Select * from Idea where idea_id = ?";
		Idea idea = new Idea();
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, idea_id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				idea = new Idea(rs.getInt(1), rs.getString("idea_title"), rs.getString("idea_content"),
						pm.getPerson(rs.getInt("person_id")), rs.getTimestamp("post_date"), rs.getInt("idea_views"),
						rs.getInt("mode"), rs.getInt("_status"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idea;
	}

	public boolean check_idea_belong(int user_id) {
		try {
			if (pm.getUserSession().getId() == user_id) {
				return true;
			} else {
				return false;
			}
		} catch (NullPointerException e) {
			return false;
		}

	}

	public void eidt_idea(Idea idea) {
		String sqlQuery = "Update Idea Set idea_title = ?, idea_content = ? where idea_id = ?";
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
			statement.setInt(1, 5);
			statement.setInt(2, idea_id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Idea> getIdeasByTag(int tag_id) {
		String sqlQuery = "select it.idea_id,i._status from Idea_tag it join Idea i on it.idea_id = i.idea_id where _status = 0 and tag_id = "
				+ tag_id;
		List<Idea> ideaList = new ArrayList<>();
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Idea idea = get_Idea(rs.getInt("idea_id"));
				ideaList.add(idea);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ideaList;
	}

	public List<Idea> getIdeasByStatus(int status) {
		String sqlQuery = "select * from Idea where _status = " + status;
		List<Idea> ideaList = new ArrayList<>();
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Idea idea = new Idea(rs.getInt(1), rs.getString("idea_title"), rs.getString("idea_content"),
						pm.getPerson(rs.getInt("person_id")), rs.getTimestamp("post_date"), rs.getInt("idea_views"),
						rs.getInt("mode"), rs.getInt("_status"));
				ideaList.add(idea);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ideaList;
	}

	public void approve_idea(int idea_id) {
		String sqlQuery = "Update Idea set _status = 1 where idea_id = ?";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, idea_id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void denied_idea(int idea_id) {
		String sqlQuery = "Update Idea set _status = 3 where idea_id = ?";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, idea_id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateView(int idea_id) {
		String sqlQuery = "Update Idea set idea_views = ? where idea_id = ?";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, getViewsCount(idea_id) + 1);
			statement.setInt(2, idea_id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private int getViewsCount(int idea_id) {
		String sqlQuery = "Select idea_views from Idea where idea_id = ?";
		int count = 0;
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, idea_id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<Idea> MostLikedIdeas(int currentPage, int itemPerPage) {
		List<Idea> ideaList = new ArrayList<>();
		int offset = itemPerPage * (currentPage - 1);
		String sqlQuery = "select *, (select count(*) from Idea_emoji where idea_id = i.idea_id and emo_id = 1) as likecount from Idea i where _status = 1 or _status = 2 or _status = 4 ORDER BY likecount DESC OFFSET "
				+ offset + " ROWS FETCH NEXT " + itemPerPage + " ROWS ONLY";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Idea idea = new Idea();
				idea.setId(rs.getInt("idea_id"));
				idea.setTitle(rs.getString("idea_title"));
				idea.setContent(rs.getString("idea_content"));
				idea.setStatus(rs.getInt("_status"));
				idea.setMode(rs.getInt("mode"));
				if (rs.getInt("mode") == 0) {
					Person p = pm.getPerson(rs.getInt("person_id"));
					p.setPerson_name("Anonymous");
					p.setPerson_picture("/uploads/default_avatar.png");
					idea.setPerson(p);
				} else {
					idea.setPerson(pm.getPerson(rs.getInt("person_id")));
				}
				idea.setPost_date(rs.getDate("post_date"));
				idea.setViews(rs.getInt("idea_views"));
				ideaList.add(idea);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ideaList;
	}

	public List<Idea> MostViewedIdeas(int currentPage, int itemPerPage) {
		List<Idea> ideaList = new ArrayList<>();
		int offset = itemPerPage * (currentPage - 1);
		String sqlQuery = "select * from Idea order by idea_views desc OFFSET " + offset + " ROWS FETCH NEXT "
				+ itemPerPage + " ROWS ONLY";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Idea idea = new Idea();
				idea.setId(rs.getInt("idea_id"));
				idea.setTitle(rs.getString("idea_title"));
				idea.setContent(rs.getString("idea_content"));
				idea.setStatus(rs.getInt("_status"));
				idea.setMode(rs.getInt("mode"));
				if (rs.getInt("mode") == 0) {
					Person p = pm.getPerson(rs.getInt("person_id"));
					p.setPerson_name("Anonymous");
					p.setPerson_picture("/uploads/default_avatar.png");
					idea.setPerson(p);
				} else {
					idea.setPerson(pm.getPerson(rs.getInt("person_id")));
				}
				idea.setPost_date(rs.getDate("post_date"));
				idea.setViews(rs.getInt("idea_views"));
				ideaList.add(idea);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ideaList;
	}

}
