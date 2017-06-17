package com.Sendarox.HiveJumpPads.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {
	
	private String host;
	private int port;
	private String user;
	private String password;
	private String database;
	
	private Connection conn;
	
	public MySQL() throws Exception {
		this.host = Value.cfg_mysql_host;
		this.port = Value.cfg_mysql_port;
		this.user = Value.cfg_mysql_username;
		this.password = Value.cfg_mysql_password;
		this.database = Value.cfg_mysql_database;
		this.openConnection();
	}
	
	public Connection getConnection() {
		return this.conn;
	}
	
	public Connection openConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.user, this.password);
		this.conn = conn;
		return conn;
	}
	
	public boolean hasConnection() {
		try {
			return this.conn != null || this.conn.isValid(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void query(String query) throws SQLException {
		Connection conn = this.conn;
		PreparedStatement st = null;
		st = conn.prepareStatement(query);
		st.executeUpdate();
		this.closeRessources(null, st);
	}
	 
	public void closeRessources(ResultSet rs, PreparedStatement st) {
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(st != null){
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void closeConnection() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.conn = null;			
		}
	}

}
