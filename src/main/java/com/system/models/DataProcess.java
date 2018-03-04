package com.system.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.system.entity.Person;
import com.system.entity.Student;

public class DataProcess {
	
	public static Connection getConnection() {
		Connection con = null;
		try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String user = "sa";
            String pass = "1234";
            String url = "jdbc:sqlserver://127.0.0.1:8484;databaseName=UniversitySystem";
            try {
                con = DriverManager.getConnection(url, user, pass);
            } catch (SQLException ex) {
                Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
		return con;
	}
}
