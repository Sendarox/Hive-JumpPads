package com.Sendarox.HiveJumpPads.utils;

import org.bukkit.ChatColor;

public class TranslateAlternateColors {

	public static String translateAlternateColorCodes(String message){
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
}
