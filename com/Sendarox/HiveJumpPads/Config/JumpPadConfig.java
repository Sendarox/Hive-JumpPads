/**
 * HiveJumpPads, a Bukkit plugin to create 'Jump-Pads' on your Minecraft server.
 * JumpPadConfig.java - {CLASS_DESCRIPTION}
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
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/** JumpPadConfig.java - {CLASS_DESCRIPTION} */
public class JumpPadConfig {

	private String 			   _fileName;
	private HashMap<String, Object> _settingsCache;
	private FileConfiguration  _jumppadFile;
	
	public JumpPadConfig(String _fileName) {
	
		this._fileName = _fileName;
		this._settingsCache = new HashMap<String, Object>();
		
		File _f = new File("plugins/HiveJumpPads/Jump-Pads/"+_fileName+".yml");
		if(!_f.exists()) {
			return;
		}
		
		_jumppadFile = YamlConfiguration.loadConfiguration(_f);
		cacheAllEntries();
	}
	
	private void cacheAllEntries() {
		_settingsCache = (HashMap<String, Object>) _jumppadFile.getValues(true);
		_settingsCache.remove("HiveJumpPads");
		_settingsCache.remove("HiveJumpPads.JumpPads");
	}
	
	public String getFilename() {
		return _fileName + ".yml";
	}
	
	public String getFilename(boolean _extension) {
		if(_extension)
			return _fileName + ".yml";
		else 
			return _fileName;
	}
	
	public HashMap<String, Object> getCacheMap() {
		return _settingsCache;
	}
	
	public FileConfiguration getConfiguration() {
		return _jumppadFile;
	}
	
	public void clearCache() {
		_settingsCache.clear();
	}
	
}
