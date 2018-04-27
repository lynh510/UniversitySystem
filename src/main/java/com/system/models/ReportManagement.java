package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.system.entity.*;

public class ReportManagement {

	public ReportManagement() {

	}

	public String NummberOfIdeas() {
		String value = "[";
		for (Entry<Department, Integer> entry : NumberOfIdeas().entrySet()) {
			Department d = entry.getKey();
			Integer i = entry.getValue();
			value += "{\"name\": \"" + d.getDept_name() + "\",\"y\":" + i + ",\"drilldown\":\"" + d.getDept_name()
					+ "\"},";
		}
		return value + "]";
	}

	public String NummberOfContributor() {
		String value = "[";
		for (Entry<Department, Integer> entry : numberOfContributor().entrySet()) {
			Department d = entry.getKey();
			Integer i = entry.getValue();
			value += "{\"name\": \"" + d.getDept_name() + "\",\"y\":" + i + ",\"drilldown\":\"" + d.getDept_name()
					+ "\"},";
		}
		return value + "]";
	}

	public String PercentageOfIdeas() {
		String value = "[";
		for (Entry<Department, Float> entry : percentageOfIdeas().entrySet()) {
			Department d = entry.getKey();
			Float i = entry.getValue();
			value += "{\"name\": \"" + d.getDept_name() + "\",\"y\":" + i + ",\"drilldown\":\"" + d.getDept_name()
					+ "\"},";
		}
		return value + "]";
	}

	public String IdeasWithoutComment() {
		String value = "[";
		for (Entry<Department, Integer> entry : ideaWithoutComment().entrySet()) {
			Department d = entry.getKey();
			Integer i = entry.getValue();
			value += "{\"name\": \"" + d.getDept_name() + "\",\"y\":" + i + ",\"drilldown\":\"" + d.getDept_name()
					+ "\"},";
		}
		return value + "]";
	}

	public String anonymousIdeaAndComment() {
		String value = "[";
		for (Entry<Department, Integer[]> entry : numberOfAnonymousIdeaAndComment().entrySet()) {
			Department d = entry.getKey();
			Integer ideas = entry.getValue()[0];
			Integer comments = entry.getValue()[1];
			value += "{name: '" + d.getDept_name() + "',data: [" + ideas + ", " + comments + "]},";
		}
		return value + "]";
	}

	public HashMap<Department, Integer> NumberOfIdeas() {
		HashMap<Department, Integer> numberOfIdea = new HashMap<>();
		String sqlQuery = "select d.dept_name, (select count(*) from (select distinct i.idea_id, d.dept_id from Idea_tag i join Tag t on i.tag_id = t.tag_id where t.dept_id = d.dept_id) as _counter) as _counter  from Department d ";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Department d = new Department();
				d.setDept_name(rs.getString("dept_name"));
				numberOfIdea.put(d, rs.getInt("_counter"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numberOfIdea;
	}

	public HashMap<Department, Float> percentageOfIdeas() {
		HashMap<Department, Float> percentageOfIdeas = new HashMap<>();
		int totalOfIdea = countIdeas();
		String sqlQuery = "select d.dept_name, "
				+ "(select count(*) from (select distinct i.idea_id, d.dept_id from Idea_tag i join Tag t on i.tag_id = t.tag_id where t.dept_id = d.dept_id) as _counter) as _counter  "
				+ "from Department d ";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Department d = new Department();
				d.setDept_name(rs.getString("dept_name"));
				float percent = rs.getInt("_counter") * 100f / totalOfIdea;
				percentageOfIdeas.put(d, percent);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return percentageOfIdeas;
	}

	private int countIdeas() {
		int count = 0;
		String sqlQuery = "select count(*) from Idea where _status = 1";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public HashMap<Department, Integer> ideaWithoutComment() {
		HashMap<Department, Integer> ideaWithoutComment = new HashMap<>();
		String sqlQuery = "select d.dept_name,  (select count(*) from (select distinct i.idea_id,d.dept_id,(select count(*) from Comment where idea_id = i.idea_id) as CommentCount from Idea_tag i join Tag t on i.tag_id = t.tag_id  where  t.dept_id = d.dept_id) as _counter) as _counter from Department d";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Department d = new Department();
				d.setDept_name(rs.getString("dept_name"));
				ideaWithoutComment.put(d, rs.getInt("_counter"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ideaWithoutComment;
	}

	public HashMap<Department, Integer> numberOfContributor() {
		HashMap<Department, Integer> NumberOfContributer = new HashMap<>();
		String sqlQuery = "select d.dept_name,( select count(distinct p.person_id) from Idea_tag it join Tag t on it.tag_id = t.tag_id  join Idea_attachfile ia on it.idea_id = ia.idea_id join Idea i on i.idea_id = ia.idea_id join Person p on p.person_id = i.person_id where t.dept_id = d.dept_id) as _counter from Department d";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Department d = new Department();
				d.setDept_name(rs.getString("dept_name"));
				NumberOfContributer.put(d, rs.getInt("_counter"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return NumberOfContributer;
	}

	public HashMap<Department, Integer[]> numberOfAnonymousIdeaAndComment() {
		HashMap<Department, Integer[]> NumberOfAnonymousIdeaAndComment = new HashMap<>();
		String sqlQuery = "select d.dept_name,(select count(*) from Idea i join Person p on p.person_id = i.person_id where mode = 0 and p.dept_id = d.dept_id) as idea_counter, (select count(*) from Comment c join Person p on p.person_id = c.person_id where mode = 0 and p.dept_id = d.dept_id) as comment_counter from Department d";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Department d = new Department();
				d.setDept_name(rs.getString("dept_name"));
				NumberOfAnonymousIdeaAndComment.put(d,
						new Integer[] { rs.getInt("idea_counter"), rs.getInt("comment_counter") });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return NumberOfAnonymousIdeaAndComment;
	}

}
