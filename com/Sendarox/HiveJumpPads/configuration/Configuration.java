package com.Sendarox.HiveJumpPads.configuration;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.Sendarox.HiveJumpPads.utils.ResourceHandler;
import com.Sendarox.HiveJumpPads.utils.Value;

public class Configuration {
	
	public void create(){
		ResourceHandler rh = new ResourceHandler();
		File config = new File("plugins/HiveJumpPads/config.yml");
		if(!config.exists()){
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(config);
			try {
				cfg.save(config);
				rh.ExportResource("/config_template/config.yml", "/HiveJumpPads");
				System.out.println("[HiveJumpPads] Config.yml is not existing. Creating a new one!");
				return;
			} catch (IOException e) {
				e.printStackTrace();
				return;
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(config);
		Value.cfg_useLegacyJump = cfg.getBoolean("HiveJumpPads.useLegacyJump");
		Value.cfg_useAdvancedJump = cfg.getBoolean("HiveJumpPads.useAdvancedJumpPads");
		Value.cfg_jumppadregister = cfg.getBoolean("HiveJumpPads.registerEachJumpPad");
		Value.cfg_sendServerStatistics = cfg.getBoolean("HiveJumpPads.sendServerStatistics");
		Value.cfg_jumppad_disablefalldamage = cfg.getBoolean("HiveJumpPads.JumpPads.disableFalldamage");
		Value.cfg_jumppad_disabledworlds = cfg.getStringList("HiveJumpPads.JumpPads.disabledWorlds");
		Value.cfg_update_updatecheck = cfg.getBoolean("HiveJumpPads.Updates.automaticUpdateCheck");
		Value.cfg_update_download = cfg.getBoolean("HiveJumpPads.Updates.automaticUpdateDownload");
		Value.cfg_mysql_usemysql = cfg.getBoolean("HiveJumpPads.MySQL.useMySQL");
		Value.cfg_mysql_username = cfg.getString("HiveJumpPads.MySQL.MySQL-User");
		Value.cfg_mysql_password = cfg.getString("HiveJumpPads.MySQL.MySQL-Password");
		Value.cfg_mysql_host = cfg.getString("HiveJumpPads.MySQL.MySQL-Host");
		Value.cfg_mysql_database = cfg.getString("HiveJumpPads.MySQL.MySQL-Database");
		Value.cfg_mysql_port = cfg.getInt("HiveJumpPads.MySQL.MySQL-Port");
		if(Value.cfg_useLegacyJump){
			Value.cfg_useAdvancedJump = false;
		}
	}
	
	public Object get(String path){
		File config = new File("plugins/HiveJumpPads/config.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(config);
		return cfg.get(path);
	}
	
	public void set(String path, Object object){
		File config = new File("plugins/HiveJumpPads/config.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(config);
		cfg.set(path, object);
		try {
			cfg.save(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
