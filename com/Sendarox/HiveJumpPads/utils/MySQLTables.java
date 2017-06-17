package com.Sendarox.HiveJumpPads.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQLTables {

	public static void createJumpPadTable(MySQL sql) throws Exception {
		sql.query("CREATE TABLE IF NOT EXISTS "+Value.mysql_table_prefix+Value.mysql_table_jumppads+" (ID INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,JumpID INT(6) NOT NULL,X INT(6) NOT NULL,Y INT(6) NOT NULL,Z INT(6) NOT NULL, World VARCHAR(32) NOT NULL)");
	}
	
	public static void createJumpPadSettingTable(MySQL sql) throws Exception {
		sql.query("CREATE TABLE IF NOT EXISTS "+Value.mysql_table_prefix+Value.mysql_table_jumppadsettings+" (ID INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, JumpID INT(6) NOT NULL, BlockID INT(6) NOT NULL, PressureplateID INT(6) NOT NULL, LockedSize VARCHAR(5) NOT NULL, JumpLength VARCHAR(5) NOT NULL, JumpHeight VARCHAR(5) NOT NULL, EnableSounds VARCHAR(5) NOT NULL, EnableEffects VARCHAR(5) NOT NULL, EnableMessage VARCHAR(5) NOT NULL, Sound VARCHAR(30) NOT NULL, Effect VARCHAR(30) NOT NULL, Message VARCHAR(265) NOT NULL)");
	}

	public static void insertNewJumpPadCoordinates(MySQL sql, int jumpid, int x, int y, int z, String world) throws Exception {
		sql.query("INSERT INTO "+Value.mysql_table_prefix+Value.mysql_table_jumppads+" (JumpID, X, Y, Z, World) VALUES ('"+jumpid+"', '"+x+"', '"+y+"', '"+z+"', '"+world+"')");
	}
	
	public static void insertNewJumpPad(MySQL sql, int jumpid, int blockID, int PressureplateID, boolean LockedSize, double JumpLength, double JumpHeight, boolean EnableSounds, boolean EnableEffects, boolean EnableMessage, String Sound, String Effect, String Message) throws Exception {
		sql.query("INSERT INTO "+Value.mysql_table_prefix+Value.mysql_table_jumppadsettings+" (JumpID, BlockID, PressureplateID, LockedSize, JumpLength, JumpHeight, EnableSounds, EnableEffects, EnableMessage, Sound, Effect, Message) VALUES ('"+jumpid+"', '"+blockID+"', '"+PressureplateID+"', '"+LockedSize+"', '"+JumpLength+"', '"+JumpHeight+"', '"+EnableSounds+"', '"+EnableEffects+"', '"+EnableMessage+"', '"+Sound+"', '"+Effect+"', '"+Message+"')");
	}
	
	public static Object getObjectFromDatabase(MySQL sql, String table, String object, int jumppadid){
		Connection connection = sql.getConnection();
		ResultSet rs = null;
		PreparedStatement st = null;
		Object item = null;
		try {
			st = connection.prepareStatement("SELECT "+object+" FROM "+table+" WHERE JumpID='"+jumppadid+"'");
			rs = st.executeQuery();
			rs.last();
			if(rs.getRow() != 0){
				rs.first();
				item = rs.getObject(object);
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			sql.closeRessources(rs, st);
		}
		return item;
	}
	
}
