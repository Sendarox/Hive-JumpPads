/**
 * HiveJumpPads, a Bukkit plugin to create 'Jump-Pads' on your Minecraft server.
 * HiveJumpPadsCommand.java - HiveJumpPads main command class.
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

package com.Sendarox.HiveJumpPads.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.Sendarox.HiveJumpPads.HiveJumpPads;
import com.Sendarox.HiveJumpPads.Config.JumpPadConfig;

/** HiveJumpPadsCommand.java - HiveJumpPads main command class. */
public class HiveJumpPadsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender _s, Command _c, String _str, String[] _args) {
		if(_args.length == 0) {
			_s.sendMessage(HiveJumpPads.getLanguage().getLanguageContent("hjp.general.devby")+" §6§lSendarox");
			_s.sendMessage(HiveJumpPads.getLanguage().getLanguageContent("hjp.general.help"));
			
			JumpPadConfig c = new JumpPadConfig("a");
			
		} else if(_args.length == 1) {
			if(_args[0].equalsIgnoreCase("help")) {
				_s.sendMessage(HiveJumpPads.getLanguage().getLanguageContent("hjp.command.help.title"));
				_s.sendMessage("§7| §a/hivejumppads info §7- "+HiveJumpPads.getLanguage().getLanguageContent("hjp.command.help.desc_info"));
				_s.sendMessage("§7| §a/hivejumppads wiki §7- "+HiveJumpPads.getLanguage().getLanguageContent("hjp.command.help.desc_wiki"));
				_s.sendMessage("§7| §a/hivejumppads reload §7- "+HiveJumpPads.getLanguage().getLanguageContent("hjp.command.help.desc_reload"));
				_s.sendMessage("§7| §a/hivejumppads update [args] §7- "+HiveJumpPads.getLanguage().getLanguageContent("hjp.command.help.desc_update"));
			} else if(_args[0].equalsIgnoreCase("info")) {
				_s.sendMessage(HiveJumpPads.getLanguage().getLanguageContent("hjp.general.curver"));
				_s.sendMessage(HiveJumpPads.getLanguage().getLanguageContent("hjp.general.devby")+" §6Sendarox");
				_s.sendMessage(HiveJumpPads.getLanguage().getLanguageContent("hjp.general.website")+" §7§n"+HiveJumpPads.getDescriptionFile().getWebsite());
			} else if(_args[0].equalsIgnoreCase("reload")) {
				if(_s.hasPermission("hivejumppads.command.reload")) {
					_s.sendMessage("");
				}
			}
		}
		return true;
	}

}
