package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.system.entity.Idea_Tag;

public class IdeaTagManagement {
	private TagManagement tm;

	public IdeaTagManagement() {
		tm = new TagManagement();
	}

	public List<Idea_Tag> get_idea_tags(int idea_id) {
		List<Idea_Tag> list_idea_tag = new ArrayList();
		try {
			String sqlQuery = "select * from Idea_tags where idea_id = " + idea_id;
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Idea_Tag idea_tag = new Idea_Tag();
				idea_tag.setId(rs.getInt("idea_tag_id"));
				idea_tag.setTag(tm.getTag(rs.getInt("tag_id")));
				list_idea_tag.add(idea_tag);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list_idea_tag;
	}
}
