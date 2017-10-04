package com.Sendarox.HiveJumpPads.configuration;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.Sendarox.HiveJumpPads.utils.JumpPadSaveType;

public class JumpPadListConfiguration {
	
	public void create(){
		File f = new File("plugins/HiveJumpPads/JumpPads/JumpPad-List.yml");
		if(f.exists()){
			return;
		}
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		
		cfg.options().header("HiveJumpPads v3.0b | Please report bugs on https://dev.bukkit.org/projects/hive-jumppads/ - thank you :)\nDon't change anything in this file, otherwise the plugin maybe won't work any longer!");
		cfg.options().copyHeader(true);
		
		try {
			System.out.println("[HiveJumpPads] JumpPad-List.yml is not existing. Creating a new one!");
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void add(int id, int x, int y, int z, String world, JumpPadSaveType type){
		File f = new File("plugins/HiveJumpPads/JumpPads/JumpPad-List.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		
		String path = "HiveJumpPads.JumpPad-"+id+".";
		cfg.set(path+"X", x);
		cfg.set(path+"Y", y);
		cfg.set(path+"Z", z);
		cfg.set(path+"World", world);
		cfg.set(path+"Type", type.toString());
		cfg.set(path+"ID", id);
		
		try {
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id){
		File f = new File("plugins/HiveJumpPads/JumpPads/JumpPad-List.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		cfg.set("HiveJumpPads.JumpPad-"+id+"", null);
		try {
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Object get(String path){
		File f = new File("plugins/HiveJumpPads/JumpPads/JumpPad-List.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		return cfg.get(path);
	}
	
	public void set(String path, Object object){
		File f = new File("plugins/HiveJumpPads/JumpPads/JumpPad-List.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		cfg.set(path, object);
		try {
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
