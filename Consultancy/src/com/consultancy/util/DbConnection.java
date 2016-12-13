package com.consultancy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnection {

	private final String database;
    private String message;
    
    

    public Connection getConnection() {
        Connection cn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            cn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + database,
                    "root", "root");

        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            setMessage(ex.getMessage());
        }

        return cn;
    }
    
    public int getCount(String table , String column ,String condition)
    {
    	int count = 0 ;
    	Connection conn;
    	conn = getConnection();
    	try {
			String sql = "SELECT count("+column+") as count FROM "+table+" "+condition+"";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				
				count =rs.getInt("count");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return count;
    }

    public DbConnection() {
        this.database = "consultancy";
    }

    public DbConnection(String database) {
        this.database = database;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
