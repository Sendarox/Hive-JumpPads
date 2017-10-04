package com.Sendarox.HiveJumpPads.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.Sendarox.HiveJumpPads.utils.Value;

public class PlayerFalldamageListener implements Listener {

	@EventHandler (priority = EventPriority.LOW)
	private void onFall(EntityDamageEvent e){
		Entity entity = e.getEntity();
		if(!(entity instanceof Player)){
			return;
		}
		if(Value.cfg_jumppad_disablefalldamage && e.getCause() == DamageCause.FALL){
			Player p = (Player)entity;
			if(Value.flyingPlayers.contains(p.getUniqueId())){
				Value.flyingPlayers.remove(p.getUniqueId());
				e.setCancelled(true);
			}
		}
		return;
	}
}
