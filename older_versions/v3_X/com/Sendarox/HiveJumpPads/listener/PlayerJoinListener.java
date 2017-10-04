package com.Sendarox.HiveJumpPads.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.Sendarox.HiveJumpPads.utils.Value;

public class PlayerJoinListener implements Listener {
	
	@EventHandler (priority = EventPriority.LOW)
	private void onPlayerJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if(!p.isOp() || !p.hasPermission("hivejumppads.command.update")){
			return;
		}
		if(!Value.sv_updateNeeded || Value.cfg_update_download){
			return;
		}
		p.sendMessage(Value.chatprefix_bold + "An update was found for §6Hive JumpPads §6v"+Value.version+"§7!");
		p.sendMessage("§7| Use §6'/hjp update url'§7 to get the download url.");
		p.sendMessage("§7| Use §6'/hjp update download§7' to download the plugin.");
		return;
	}
}
