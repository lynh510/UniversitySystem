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

	public String NummberOfIdeas(int academic_year) {
		String value = "[";
		for (Entry<Department, Integer> entry : NumberOfIdeas(academic_year).entrySet()) {
			Department d = entry.getKey();
			Integer i = entry.getValue();
			value += "{\"name\": \"" + d.getDept_name() + "\",\"y\":" + i + ",\"drilldown\":\"" + d.getDept_name()
					+ "\"},";
		}
		return value + "]";
	}

	public String NummberOfContributor(int academic_year) {
		String value = "[";
		for (Entry<Department, Integer> entry : numberOfContributor(academic_year).entrySet()) {
			Department d = entry.getKey();
			Integer i = entry.getValue();
			value += "{\"name\": \"" + d.getDept_name() + "\",\"y\":" + i + ",\"drilldown\":\"" + d.getDept_name()
					+ "\"},";
		}
		return value + "]";
	}

	public String PercentageOfIdeas(int academic_year) {
		String value = "[";
		for (Entry<Department, Float> entry : percentageOfIdeas(academic_year).entrySet()) {
			Department d = entry.getKey();
			Float i = entry.getValue();
			value += "{\"name\": \"" + d.getDept_name() + "\",\"y\":" + i + ",\"drilldown\":\"" + d.getDept_name()
					+ "\"},";
		}
		return value + "]";
	}

	public String IdeasWithoutComment(int academic_year) {
		String value = "[";
		for (Entry<Department, Integer> entry : ideaWithoutComment(academic_year).entrySet()) {
			Department d = entry.getKey();
			Integer i = entry.getValue();
			value += "{\"name\": \"" + d.getDept_name() + "\",\"y\":" + i + ",\"drilldown\":\"" + d.getDept_name()
					+ "\"},";
		}
		return value + "]";
	}

	public String anonymousIdeaAndComment(int academic_year) {
		String value = "[";
		for (Entry<Department, Integer[]> entry : numberOfAnonymousIdeaAndComment(academic_year).entrySet()) {
			Department d = entry.getKey();
			Integer ideas = entry.getValue()[0];
			Integer comments = entry.getValue()[1];
			value += "{name: '" + d.getDept_name() + "',data: [" + ideas + ", " + comments + "]},";
		}
		return value + "]";
	}

	public HashMap<Department, Integer> NumberOfIdeas(int academic_id) {
		HashMap<Department, Integer> numberOfIdea = new HashMap<>();
		String sqlQuery = "";
		if (academic_id == 0) {
			sqlQuery = "select d.dept_name, (select count(*) from (select distinct i.idea_id, d.dept_id from Idea i join Person p on i.person_id = p.person_id  where p.dept_id = d.dept_id) as _counter) as _counter  from Department d ";
		} else {
			sqlQuery = "select d.dept_name, (select count(*) from (select distinct i.idea_id, d.dept_id from Idea i join Person p on i.person_id = p.person_id  where p.dept_id = d.dept_id) as _counter) as _counter  from Department d where academic_year_id = "
					+ academic_id;
		}
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

	public HashMap<Department, Float> percentageOfIdeas(int academic_year) {
		HashMap<Department, Float> percentageOfIdeas = new HashMap<>();
		int totalOfIdea = countIdeas(academic_year);
		String sqlQuery = "select d.dept_name, (select count(*) from (select distinct i.idea_id, d.dept_id from Idea i join Person p on i.person_id = p.person_id  where p.dept_id = d.dept_id) as _counter) as _counter  from Department d ";
		if (academic_year == 0) {
			sqlQuery = "select d.dept_name, (select count(*) from (select distinct i.idea_id, d.dept_id from Idea i join Person p on i.person_id = p.person_id  where p.dept_id = d.dept_id) as _counter) as _counter  from Department d ";

		} else {
			sqlQuery = "select d.dept_name, (select count(*) from (select distinct i.idea_id, d.dept_id from Idea i join Person p on i.person_id = p.person_id  where p.dept_id = d.dept_id) as _counter) as _counter  from Department d where academic_year_id = "
					+ academic_year;
		}
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

	private int countIdeas(int academic_year) {
		int count = 0;
		String sqlQuery = "";
		if (academic_year == 0) {
			sqlQuery = "select count(*) from Idea where _status = 1";
		} else {
			sqlQuery = "select count(*) from Idea i join Person p on p.person_id = i.person_id join Department d on d.dept_id = p.dept_id join AcademicYear ay on ay.academic_year_id = d.academic_year_id where ay.academic_year_id = "
					+ academic_year;
		}
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

	public HashMap<Department, Integer> ideaWithoutComment(int academic_year) {
		HashMap<Department, Integer> ideaWithoutComment = new HashMap<>();
		String sqlQuery = "select d.dept_name,(select count(*) from (select i.idea_id, (select count(*) from Comment where idea_id = i.idea_id) as _counter from Idea i join Person p on p.person_id = i.person_id where d.dept_id = p.dept_id GROUP BY i.idea_id having (select count(*) from Comment where idea_id = i.idea_id) = 0) as _counter)as _counter from Department d";
		if (academic_year == 0) {
			sqlQuery = "select d.dept_name,(select count(*) from (select i.idea_id, (select count(*) from Comment where idea_id = i.idea_id) as _counter from Idea i join Person p on p.person_id = i.person_id where d.dept_id = p.dept_id GROUP BY i.idea_id having (select count(*) from Comment where idea_id = i.idea_id) = 0) as _counter)as _counter from Department d";
		} else {
			sqlQuery = "select d.dept_name,(select count(*) from (select i.idea_id, (select count(*) from Comment where idea_id = i.idea_id) as _counter from Idea i join Person p on p.person_id = i.person_id where d.dept_id = p.dept_id GROUP BY i.idea_id having (select count(*) from Comment where idea_id = i.idea_id) = 0) as _counter)as _counter from Department d where academic_year_id = "
					+ academic_year;
		}
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

	public HashMap<Department, Integer> numberOfContributor(int academic_year) {
		HashMap<Department, Integer> NumberOfContributer = new HashMap<>();
		String sqlQuery = "select d.dept_name,( select count(distinct p.person_id) from Idea_tag it join Tag t on it.tag_id = t.tag_id  join Idea_attachfile ia on it.idea_id = ia.idea_id join Idea i on i.idea_id = ia.idea_id join Person p on p.person_id = i.person_id where t.dept_id = d.dept_id) as _counter from Department d";
		if (academic_year == 0) {
			sqlQuery = "select d.dept_name,( select count(distinct p.person_id) from Idea_tag it join Tag t on it.tag_id = t.tag_id  join Idea_attachfile ia on it.idea_id = ia.idea_id join Idea i on i.idea_id = ia.idea_id join Person p on p.person_id = i.person_id where t.dept_id = d.dept_id) as _counter from Department d";
		} else {
			sqlQuery = "select d.dept_name,( select count(distinct p.person_id) from Idea_tag it join Tag t on it.tag_id = t.tag_id  join Idea_attachfile ia on it.idea_id = ia.idea_id join Idea i on i.idea_id = ia.idea_id join Person p on p.person_id = i.person_id where t.dept_id = d.dept_id) as _counter from Department d where academic_year_id = "
					+ academic_year;
		}
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

	public HashMap<Department, Integer[]> numberOfAnonymousIdeaAndComment(int academic_year) {
		HashMap<Department, Integer[]> NumberOfAnonymousIdeaAndComment = new HashMap<>();
		String sqlQuery = "select d.dept_name,(select count(*) from Idea i join Person p on p.person_id = i.person_id where mode = 0 and p.dept_id = d.dept_id) as idea_counter, (select count(*) from Comment c join Person p on p.person_id = c.person_id where mode = 1 and p.dept_id = d.dept_id) as comment_counter from Department d";
		if (academic_year == 0) {
			sqlQuery = "select d.dept_name,(select count(*) from Idea i join Person p on p.person_id = i.person_id where mode = 0 and p.dept_id = d.dept_id) as idea_counter, (select count(*) from Comment c join Person p on p.person_id = c.person_id where mode = 1 and p.dept_id = d.dept_id) as comment_counter from Department d";
		} else {
			sqlQuery = "select d.dept_name,(select count(*) from Idea i join Person p on p.person_id = i.person_id where mode = 0 and p.dept_id = d.dept_id) as idea_counter, (select count(*) from Comment c join Person p on p.person_id = c.person_id where mode = 1 and p.dept_id = d.dept_id) as comment_counter from Department d where academic_year_id = "
					+ academic_year;
		}
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
