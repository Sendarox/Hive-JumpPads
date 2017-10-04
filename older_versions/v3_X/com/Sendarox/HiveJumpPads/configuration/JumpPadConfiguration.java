package com.Sendarox.HiveJumpPads.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.Sendarox.HiveJumpPads.utils.Value;

public class JumpPadConfiguration {

	private FileConfiguration cfg;
	private String name;
	private String world;
	public JumpPadConfiguration(String name, String world) {
		this.name = name;
		this.world = world;
	}
	
	// COMING IN FUTURE UPDATE (PLANNED FOR V3.2)
	public JumpPadConfiguration(String name, String world, String template) {
		/*
		 * 
		 * 
		 */
	}
	
	public void create(int x, int y, int z) throws IOException {
		File conf_new = new File(Value.dataFolder+"/JumpPads/"+name);
		if(conf_new.exists())
			Files.delete(conf_new.toPath());
		File conf_default = new File(Value.dataFolder + "/jumppad-default.yml");
		FileInputStream fis = new FileInputStream(conf_default);
		FileOutputStream fos = new FileOutputStream(conf_new);
			
		byte buffer[] = new byte[1024];
		int lenght;
		while((lenght = fis.read(buffer)) > 0){
			fos.write(buffer, 0, lenght);
		}
		fis.close();
		fos.close();
		String ymlpath_default = "HiveJumpPads.JumpPads.";
		String ymlpath = "HiveJumpPads."+name.replace(".yml", "")+".";
		String ymlpath_coordinates = "HiveJumpPads."+name.replace(".yml", "")+"-coordinates.";
		this.cfg = YamlConfiguration.loadConfiguration(conf_new);
		cfg.options().header("HiveJumpPads v3.0 | Please report bugs on https://dev.bukkit.org/projects/hive-jumppads/ - thank you :)\nIf there are any problems please contact me on dev.Bukkit.org\n\nAvailable Sounds and Effects can be found at:\nSounds: https://dev.bukkit.org/projects/hive-jumppads/pages/wiki/sounds\nEffects: https://dev.bukkit.org/projects/hive-jumppads/pages/wiki/effects\n\nConfiguration for jumppad at: X:"+x+"; Y:"+y+"; Z:"+z);
		cfg.addDefault(ymlpath+"BlockID", cfg.get(ymlpath_default+"BlockID"));
		cfg.addDefault(ymlpath+"PressurePlateID", cfg.get(ymlpath_default+"PressurePlateID"));
		cfg.addDefault(ymlpath+"LockedSize", cfg.get(ymlpath_default+"LockedSize"));
		cfg.addDefault(ymlpath+"JumpLength", cfg.get(ymlpath_default+"JumpLength"));
		cfg.addDefault(ymlpath+"JumpHeight", cfg.get(ymlpath_default+"JumpHeight"));
		cfg.addDefault(ymlpath+"EnableSounds", cfg.get(ymlpath_default+"EnableSounds"));
		cfg.addDefault(ymlpath+"EnableEffects", cfg.get(ymlpath_default+"EnableEffects"));
		cfg.addDefault(ymlpath+"EnableMessage", cfg.get(ymlpath_default+"EnableMessage"));
		cfg.addDefault(ymlpath+"Sound", cfg.get(ymlpath_default+"Sound"));
		cfg.addDefault(ymlpath+"Effect", cfg.get(ymlpath_default+"Effect"));
		cfg.addDefault(ymlpath+"Message", cfg.get(ymlpath_default+"Message"));
		cfg.addDefault(ymlpath_coordinates+"X", x);
		cfg.addDefault(ymlpath_coordinates+"Y", y);
		cfg.addDefault(ymlpath_coordinates+"Z", z);
		cfg.addDefault(ymlpath_coordinates+"World", world);
		cfg.set("HiveJumpPads.JumpPads", null);
		cfg.options().copyHeader(true);
		cfg.options().copyDefaults(true);
		cfg.save(conf_new);
	}
	
	public void remove() throws NoSuchFileException, DirectoryNotEmptyException, IOException {
		File f = new File(Value.dataFolder+"/JumpPads/"+name);
		Files.delete(f.toPath());
	}
	
	public void set(String path, Object item){
		File f = new File(Value.dataFolder+"/JumpPads/"+name);
		FileConfiguration conf = YamlConfiguration.loadConfiguration(f);
		conf.set(path, item);
		try {
			conf.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Object get(String path){
		File f = new File(Value.dataFolder+"/JumpPads/"+name);
		FileConfiguration conf = YamlConfiguration.loadConfiguration(f);
		return conf.get(path);
	}

}
