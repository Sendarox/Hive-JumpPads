package com.Sendarox.HiveJumpPads.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.Sendarox.HiveJumpPads.utils.ResourceHandler;

public class VersionConfiguration {
	
	public void create(){
		File f = new File("plugins/HiveJumpPads/version/version.yml");
		if(!f.exists()){
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
			try {
				cfg.save(f);
			} catch (IOException e){
				e.printStackTrace();
			}
			ResourceHandler rh = new ResourceHandler();
			try {
				rh.ExportResource("/config_template/version.yml", "/HiveJumpPads/version/");
				System.out.println("[HiveJumpPads] version.yml is not existing. Creating a new one!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addMissingJumppadID(int id){
		File f = new File("plugins/HiveJumpPads/version/version.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		
		List<String> missingids = cfg.getStringList("HiveJumpPads.missing-ids");
		missingids.add(String.valueOf(id));
		cfg.set("HiveJumpPads.missing-ids", missingids);
		
		try {
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Integer> getMissingJumppadIDs(){
		File f = new File("plugins/HiveJumpPads/version/version.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		List<String> missingids_string = cfg.getStringList("HiveJumpPads.missing-ids");
		List<Integer> missingids = new ArrayList<Integer>();
		for(int i = 0; i < missingids_string.size(); i++){
			try {
				missingids.add(Integer.parseInt(missingids_string.get(i)));
			} catch (NumberFormatException e){
				e.printStackTrace();
			}
		}
		return missingids;
	}
	
	public Object get(String path){
		File f = new File("plugins/HiveJumpPads/version/version.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		return cfg.get(path);
	}
	
	public void set(String path, Object object){
		File f = new File("plugins/HiveJumpPads/version/version.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		cfg.set(path, object);
		try {
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
