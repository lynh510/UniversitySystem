package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.system.entity.*;

public class ReportManagement {
	private DepartmentManagement dm;

	public ReportManagement() {
		dm = new DepartmentManagement();
	}

	private int countIdeaEachDepartment(int dept_id) {
		int count = 0;
		String sqlQuery = "select distinct i.idea_id,t.dept_id,d.dept_id from Idea_tag i join Tag t on i.tag_id = t.tag_id join Department d on t.dept_id = d.dept_id where t.dept_id = ?";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, dept_id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				count += 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public HashMap<Department, Integer> NumberOfIdeas() {
		HashMap<Department, Integer> numberOfIdea = new HashMap<>();
		for (Department d : dm.getDepartments()) {
			numberOfIdea.put(d, countIdeaEachDepartment(d.getId()));
		}
		return numberOfIdea;
	}

	private int countIdeas() {
		int count = 0;
		String sqlQuery = "select count(*) from Idea";
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

	private int CountIdeaWithout(int dept_id) {
		int count = 0;
		String sqlQuery = "select i.idea_id,d.dept_id,(select count(*) from Comment where idea_id = i.idea_id) as CommentCount from Idea_tag i join Tag t on i.tag_id = t.tag_id join Department d on t.dept_id = d.dept_id where t.dept_id = ?";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, dept_id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getInt(3) == 0) {
					count += 1;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	public HashMap<Department, Integer> ideaWithoutComment() {
		HashMap<Department, Integer> ideaWithoutComment = new HashMap<>();
		for (Department d : dm.getDepartments()) {
			ideaWithoutComment.put(d, CountIdeaWithout(d.getId()));
		}
		return ideaWithoutComment;
	}

	public HashMap<Department, Float> PercentageOfIdeas() {
		HashMap<Department, Float> percentageOfIdeas = new HashMap<>();
		int totalOfIdea = countIdeas();
		for (Department d : dm.getDepartments()) {
			int numberOfIdea = countIdeaEachDepartment(d.getId());
			float percent = numberOfIdea * 100f / totalOfIdea;
			percentageOfIdeas.put(d, percent);
		}
		return percentageOfIdeas;
	}

	public HashMap<Department, Integer> numberOfContributer() {
		HashMap<Department, Integer> NumberOfContributer = new HashMap<>();
		for (Department d : dm.getDepartments()) {
			NumberOfContributer.put(d, numberOfContributiors(d.getId()));
		}
		return NumberOfContributer;
	}

	private int numberOfContributiors(int dept_id) {
		int count = 0;
		String sqlQuery = "select count(distinct p.person_id)   from Idea_tag it join Tag t on it.tag_id = t.tag_id join Department d on t.dept_id = d.dept_id join Idea_attachfile ia on it.idea_id = ia.idea_id join Idea i on i.idea_id = ia.idea_id join Person p on p.person_id = i.person_id where t.dept_id = ?";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, dept_id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

}
