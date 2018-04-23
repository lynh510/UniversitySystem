package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.system.entity.Tag;

public class TagManagement {

	public List<Tag> getTags() {
		List<Tag> tags = new ArrayList<Tag>();
		String sqlQuery = "select * from Tag";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Tag tag = new Tag();
				tag.setId(rs.getInt("tag_id"));
				tag.setDescription(rs.getString("tag_des"));
				tags.add(tag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tags;
	}

	public List<Tag> getTagsByDepartment(int dept_id) {
		List<Tag> tags = new ArrayList<Tag>();
		String sqlQuery = "select * from Tag where dept_id = " + dept_id;
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Tag tag = new Tag();
				tag.setId(rs.getInt("tag_id"));
				tag.setDescription(rs.getString("tag_des"));
				tags.add(tag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tags;
	}

	public Tag getTag(int id) {
		Tag tag = new Tag();
		String sqlQuery = "select * from Tag where tag_id = " + id;
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				tag.setId(rs.getInt("tag_id"));
				tag.setDescription(rs.getString("tag_des"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tag;
	}

	public int count_tag_being_used(int tag_id) {
		int count = 0;
		String sqlQuery = "select count(*) from Idea_tag where tag_id = " + tag_id;
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public boolean delete_tag(int tag_id) {
		boolean flag = false;
		String sqlQuery = "delete from Tag where tag_id = " + tag_id;
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.executeUpdate();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public void insert_tag(Tag t) {
		String sqlQuery = "insert into Tag values(?)";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, t.getDescription());
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean chec_exist(String tag_name) {
		boolean flag = false;
		String sqlQuery = "select * from Tag where tag_des = ?";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, tag_name);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
}
