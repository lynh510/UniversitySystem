package com.system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.system.entity.*;

public class IdeaAttachFileManagement {
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
}
