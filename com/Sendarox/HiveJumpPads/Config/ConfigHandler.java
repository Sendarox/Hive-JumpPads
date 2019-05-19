/**
 * HiveJumpPads, a Bukkit plugin to create 'Jump-Pads' on your Minecraft server.
 * ConfigHandler.java - {CLASS_DESCRIPTION}
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
 **/

package com.Sendarox.HiveJumpPads.Config;

import java.io.File;
import java.util.logging.Level;

import com.Sendarox.HiveJumpPads.HiveJumpPads;
import com.Sendarox.HiveJumpPads.Utils.Handler.ResourceHandler;

/** ConfigHandler.java - Configuration handler. */
public class ConfigHandler {
	
	public static void setupAllConfigurations() {
		setupDirectories();
		setupDefaultLanguageFiles();
		
	}
	
	private static void setupDefaultLanguageFiles() {
		File _lang_enUS = new File("plugins/HiveJumpPads/lang/en_us.lang");
		if(!_lang_enUS.exists()) {
			try {
				ResourceHandler.ExportResource("/config_template/lang/en_us.lang", "/HiveJumpPads/lang/");
			} catch (Exception _e) {
				HiveJumpPads._hjp_logger.log(Level.SEVERE, "Err");
				_e.printStackTrace();
			}
		}
		
		File _lang_deDE = new File("plugins/HiveJumpPads/lang/de_de.lang");
		if(!_lang_deDE.exists()) {
			try {
				ResourceHandler.ExportResource("/config_template/lang/de_de.lang", "/HiveJumpPads/lang/");
			} catch (Exception _e) {
				HiveJumpPads._hjp_logger.log(Level.SEVERE, "Err");
				_e.printStackTrace();
			}
		}
	}
	
	/** Creates the plugins' folder, if it's not existing. **/
	private static void setupDirectories() {
		File _dir = new File("plugins/HiveJumpPads/lang/");
		if(!_dir.exists()) {
			_dir.mkdirs();
		}
		_dir = new File("plugins/HiveJumpPads/jump-pads/");
		if(!_dir.exists()) {
			_dir.mkdirs();
		}
	}
	
}
