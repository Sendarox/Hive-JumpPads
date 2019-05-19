/**
 *      __  ___                __                      ____            __    
 *     / / / (_)   _____      / /_  ______ ___  ____  / __ \____ _____/ /____
 *    / /_/ / / | / / _ \__  / / / / / __ `__ \/ __ \/ /_/ / __ `/ __  / ___/
 *   / __  / /| |/ /  __/ /_/ / /_/ / / / / / / /_/ / ____/ /_/ / /_/ (__  ) 
 *  /_/ /_/_/ |___/\___/\____/\__,_/_/ /_/ /_/ .___/_/    \__,_/\__,_/____/  
 *         PLUGIN VERSION v4.0             /_/  Copyright (C) 2013-2019 Sendarox                      
 * 
 * HiveJumpPads, a Bukkit plugin enables you to create 'Jump-Pads' on your Minecraft server.
 * HiveJumpPads.java - Provides main functionality.
 * Copyright (C) 2013-2019 Sendarox 
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 **/

package com.Sendarox.HiveJumpPads;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.Sendarox.HiveJumpPads.Commands.HiveJumpPadsCommand;
import com.Sendarox.HiveJumpPads.Config.ConfigHandler;
import com.Sendarox.HiveJumpPads.Utils.Language;
import com.Sendarox.HiveJumpPads.Utils.Metrics;

/** HiveJumpPads.java - Provides main functionality. */
public class HiveJumpPads extends JavaPlugin {

	private static Language 			 _hjp_language;
	private static PluginDescriptionFile _hjp_desc;
	
	public static Logger  				 _hjp_logger = Bukkit.getLogger();
	
	@Override
	/** Executed whenever HiveJumpPads gets enabled. **/
	public void onEnable() {
		ConfigHandler.setupAllConfigurations();
		
		_hjp_language = new Language("en_us");
		_hjp_desc     = this.getDescription();
		
		_hjp_logger.log(Level.INFO, _hjp_language.getLanguageContent("hjp.general.cprefix")+" "+_hjp_language.getLanguageContent("hjp.console.enable.start").replace("%ver%", this.getDescription().getVersion()));
		this.getCommand("hivejumppads").setExecutor(new HiveJumpPadsCommand());
	
		setupMetrics();
		checkForPluginUpdates();
	}
	
	@Override
	/** Executed whenever HiveJumpPads gets disabled. **/
	public void onDisable() {
		_hjp_language.clearLanguageCache();
	}
	
	/** Sets up metrics and statistics collection. **/
	private void setupMetrics() {
		if(true) {
			Metrics _hjp_metrics = new Metrics(this);
			_hjp_metrics.addCustomChart(new Metrics.SimplePie("pluginVersion") {
				
				@Override
				public String getValue() {
					return "v"+ HiveJumpPads.getDescriptionFile().getVersion();
				}
			});
		}
	}
	
	/** Checks if a new version of HiveJumpPads is available. **/
	private void checkForPluginUpdates() {
		
	}
	
	/** Gets the Language that's currently in use.
	 *  @return Returns the used Language (Object). **/
	public static Language getLanguage() {
		return _hjp_language;
	}
	
	/** Gets the HiveJumpPads plugin.yml file. 
	 *  @return Returns The Plugins' description file. **/
	public static PluginDescriptionFile getDescriptionFile() {
		return _hjp_desc;
	}
	
	
}
