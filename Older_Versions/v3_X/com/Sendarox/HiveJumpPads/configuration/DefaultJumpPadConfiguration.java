package com.Sendarox.HiveJumpPads.configuration;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.Sendarox.HiveJumpPads.utils.ResourceHandler;
import com.Sendarox.HiveJumpPads.utils.Value;

public class DefaultJumpPadConfiguration {
	
	public void create(){
		File file = new File("plugins/HiveJumpPads/jumppad-default.yml");
		if(!file.exists()){
			ResourceHandler rh = new ResourceHandler();
			try {
				rh.ExportResource("/config_template/jumppad-default.yml", "/HiveJumpPads");
				System.out.println("[HiveJumpPads] jumppad-default.yml is not existing. Creating a new one!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		Value.cfg_jumppad_default_blockid = cfg.getInt("HiveJumpPads.JumpPads.BlockID");
		Value.cfg_jumppad_default_pressureplateid = cfg.getInt("HiveJumpPads.JumpPads.PressurePlateID");
		Value.cfg_jumppad_default_jumplength = cfg.getDouble("HiveJumpPads.JumpPads.JumpLength");
		Value.cfg_jumppad_default_jumpheight = cfg.getDouble("HiveJumpPads.JumpPads.JumpHeight");
		Value.cfg_jumppad_default_lockedsize = cfg.getBoolean("HiveJumpPads.JumpPads.LockedSize");
		Value.cfg_jumppad_default_enablesounds = cfg.getBoolean("HiveJumpPads.JumpPads.EnableSounds");
		Value.cfg_jumppad_default_enableeffects = cfg.getBoolean("HiveJumpPads.JumpPads.EnableEffects");
		Value.cfg_jumppad_default_enablemessage = cfg.getBoolean("HiveJumpPads.JumpPads.EnableMessage");
		Value.cfg_jumppad_default_sound = cfg.getString("HiveJumpPads.JumpPads.Sound");
		Value.cfg_jumppad_default_effect = cfg.getString("HiveJumpPads.JumpPads.Effect");
		Value.cfg_jumppad_default_message = cfg.getString("HiveJumpPads.JumpPads.Message");
	}
	
	public Object get(String path){
		File file = new File("plugins/HiveJumpPads/jumppad-default.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		return cfg.get(path);
	}
	
	public void set(String path, Object object){
		File file = new File("plugins/HiveJumpPads/jumppad-default.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.set(path, object);
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
