package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.system.entity.Tag;

public class TagManagement {

	public List<Tag> getTags() {
		List<Tag> tags = new ArrayList();
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
}
