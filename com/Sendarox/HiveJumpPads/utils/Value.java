package com.Sendarox.HiveJumpPads.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.Sendarox.HiveJumpPads.update.Updater.UpdateResult;

public class Value {
	
	public static final String version = "3.0";
	public static final String codename = "Skyline";
	public static final String buildid = "0001";
	public static final int bukkitdevid = 64535;
	
	public static final String chatprefix = "§a[§6HiveJumpPads§a]§7 ";
	public static final String chatprefix_short = "§a[§6HJP§a]§7 ";
	public static final String chatprefix_bold = "§a[§6§lHiveJumpPads§a]§7 ";
	
	public static final String message_help = "§7Unknown command. Please use §6'/hjp help'§7.";
	public static final String message_noperm = "§cYou don't have enough permissions to execute this command.";
	
	public static final String mysql_table_prefix = "hjp_";
	public static final String mysql_table_jumppads = "jumppads";
	public static final String mysql_table_jumppadsettings = "jumppad_settings";
	
	public static Boolean sv_updateNeeded;
	public static UpdateResult sv_updateResult;
	public static String sv_updateUrl;
	public static String sv_updateVersion;
	
	public static File dataFolder;
	
	public static List<UUID> flyingPlayers = new ArrayList<UUID>();	
	public static List<UUID> messageSent = new ArrayList<UUID>();
	public static boolean mysql_connected;
	public static boolean mysql_error;
	
	public static boolean cfg_useLegacyJump;
	public static boolean cfg_useAdvancedJump;
	public static boolean cfg_jumppadregister;
	public static boolean cfg_sendServerStatistics;
	
	public static boolean cfg_jumppad_disablefalldamage;
	public static List<String> cfg_jumppad_disabledworlds;
	
	public static boolean cfg_update_updatecheck;
	public static boolean cfg_update_download;
	
	public static boolean cfg_mysql_usemysql;
	public static String cfg_mysql_username;
	public static String cfg_mysql_password;
	public static String cfg_mysql_database;
	public static String cfg_mysql_host;
	public static int cfg_mysql_port;
	
	public static int cfg_jumppad_default_blockid;
	public static int cfg_jumppad_default_pressureplateid;
	public static double cfg_jumppad_default_jumplength;
	public static double cfg_jumppad_default_jumpheight;
	public static boolean cfg_jumppad_default_lockedsize;
	public static boolean cfg_jumppad_default_enablesounds;
	public static boolean cfg_jumppad_default_enableeffects;
	public static boolean cfg_jumppad_default_enablemessage;
	public static String cfg_jumppad_default_sound;
	public static String cfg_jumppad_default_effect;
	public static String cfg_jumppad_default_message;
}
