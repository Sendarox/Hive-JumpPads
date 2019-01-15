/**
 *      __  ___                __                      ____            __    
 *     / / / (_)   _____      / /_  ______ ___  ____  / __ \____ _____/ /____
 *    / /_/ / / | / / _ \__  / / / / / __ `__ \/ __ \/ /_/ / __ `/ __  / ___/
 *   / __  / /| |/ /  __/ /_/ / /_/ / / / / / / /_/ / ____/ /_/ / /_/ (__  ) 
 *  /_/ /_/_/ |___/\___/\____/\__,_/_/ /_/ /_/ .___/_/    \__,_/\__,_/____/  
 *         Plugin version: v4.0             /_/  Copyright (C) 2013-2019 Sendarox                      
 * 
 * HiveJumpPads, a Bukkit plugin to create 'Jump-Pads' on your Minecraft server.
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

import org.bukkit.plugin.java.JavaPlugin;

/** HiveJumpPads.java - Provides main functionality. */
public class HiveJumpPads extends JavaPlugin {

	@Override
	public void onEnable() {
		getLogger().log(Level.INFO, "[HiveJumpPads]");
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	
}
