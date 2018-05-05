package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.system.Helper;
import com.system.entity.*;

public class IdeaAttachFileManagement {
	private Helper helper;

	public IdeaAttachFileManagement() {
		helper = new Helper();
	}

	public List<Idea_attachfiles> get() {
		List<Idea_attachfiles> listFiles = new ArrayList<>();
		String sqlQuery = "select * from Idea_attachfile";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Idea_attachfiles ia = new Idea_attachfiles();
				ia.setId(rs.getInt("attachfile_id"));
				ia.setIdea(new Idea(rs.getInt("idea_id")));
				ia.setOld_name(rs.getString("old_name"));
				ia.setNew_name(rs.getString("new_name"));
				ia.setFile_type(rs.getString("file_type"));
				ia.setLink(rs.getString("link"));
				listFiles.add(ia);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listFiles;
	}

	public List<Idea_attachfiles> getbyDepartment(int dept_id) {
		List<Idea_attachfiles> listFiles = new ArrayList<>();
		String sqlQuery = "select ia.attachfile_id,ia.idea_id,ia.old_name,ia.file_type from "
				+ "Idea_attachfile ia join Idea i on ia.idea_id = i.idea_id join "
				+ "Person p on p.person_id = i.person_id join "
				+ "Department d on d.dept_id = p.dept_id where d.dept_id = ?";
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, dept_id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Idea_attachfiles ia = new Idea_attachfiles();
				ia.setId(rs.getInt("attachfile_id"));
				ia.setIdea(new Idea(rs.getInt("idea_id")));
				ia.setOld_name(rs.getString("old_name"));
				ia.setFile_type(rs.getString("file_type"));
				listFiles.add(ia);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listFiles;
	}

	public Idea_attachfiles getFileNameByID(int file_id) {
		String sqlQuery = "select * from Idea_attachfile where attachfile_id = ?";
		Idea_attachfiles ia = new Idea_attachfiles();
		try {
			Connection connection = DataProcess.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setInt(1, file_id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				ia.setLink(rs.getString("link"));
				ia.setNew_name(rs.getString("new_name"));
				ia.setOld_name(rs.getString("old_name"));
				ia.setFile_type(rs.getString("file_type"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ia;
	}

	public List<Idea_attachfiles> getFileNameByIDs(List<String> listIds) {
		List<Idea_attachfiles> listFiles = new ArrayList<>();
		for (String id : listIds) {
			listFiles.add(getFileNameByID(helper.decodeID(id)));

		}
		return listFiles;
	}
}
