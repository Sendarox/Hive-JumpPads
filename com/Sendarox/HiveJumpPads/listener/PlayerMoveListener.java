package com.Sendarox.HiveJumpPads.listener;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.Sendarox.HiveJumpPads.utils.TranslateAlternateColors;
import com.Sendarox.HiveJumpPads.utils.Value;

public class PlayerMoveListener implements Listener {
	
	@EventHandler (priority = EventPriority.LOW)
	@SuppressWarnings("deprecation")
	private void onPlayerMove(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(Value.cfg_jumppad_disabledworlds.contains(p.getWorld().getName())){
			return;
		}
		if(!Value.cfg_useAdvancedJump && p.getLocation().getWorld().getBlockAt(p.getLocation()).getRelative(0, -1, 0).getTypeId() != Value.cfg_jumppad_default_blockid){
			return;
		}
		if(!p.hasPermission("hivejumppads.use.jumppad")){
			return;
		}
		this.Jump(p);
	}
	
	@SuppressWarnings("deprecation")
	private void Jump(Player p){
		Location loc = p.getLocation();
		if(Value.cfg_jumppad_default_lockedsize){
			if(loc.getWorld().getBlockAt(loc).getRelative(0, 0, 0).getTypeId() == Value.cfg_jumppad_default_pressureplateid){
				if(loc.getWorld().getBlockAt(loc).getRelative(1, -1, 0).getTypeId() == Value.cfg_jumppad_default_blockid &&
				   loc.getWorld().getBlockAt(loc).getRelative(-1, -1, 0).getTypeId() == Value.cfg_jumppad_default_blockid &&
				   loc.getWorld().getBlockAt(loc).getRelative(0, -1, 1).getTypeId() == Value.cfg_jumppad_default_blockid &&
				   loc.getWorld().getBlockAt(loc).getRelative(0, -1, -1).getTypeId() == Value.cfg_jumppad_default_blockid && 
				   loc.getWorld().getBlockAt(loc).getRelative(-1, -1, -1).getTypeId() == Value.cfg_jumppad_default_blockid &&
				   loc.getWorld().getBlockAt(loc).getRelative(1, -1, 1).getTypeId() == Value.cfg_jumppad_default_blockid)
				{
					p.setVelocity(p.getLocation().getDirection().setY(Value.cfg_jumppad_default_jumpheight).multiply(Value.cfg_jumppad_default_jumplength));
					if(Value.cfg_jumppad_default_enablesounds){
						p.playSound(p.getLocation(), Sound.valueOf(Value.cfg_jumppad_default_sound.toUpperCase()), 1.0F, 1.0F);
					}
					if(Value.cfg_jumppad_default_enableeffects){
						p.getWorld().playEffect(p.getLocation(), Effect.valueOf(Value.cfg_jumppad_default_effect), 5);
					}
					if(Value.cfg_jumppad_default_enablemessage){
						p.sendMessage(TranslateAlternateColors.translateAlternateColorCodes(Value.cfg_jumppad_default_message));
					}
					if(Value.cfg_jumppad_disablefalldamage){
						Value.flyingPlayers.add(p.getUniqueId());
					}
					return;
				}
				return;
			}
			return;
		} else {
			if(loc.getWorld().getBlockAt(loc).getRelative(0, 0, 0).getTypeId() == Value.cfg_jumppad_default_pressureplateid){
				p.setVelocity(p.getLocation().getDirection().setY(Value.cfg_jumppad_default_jumpheight).multiply(Value.cfg_jumppad_default_jumplength));
				System.out.println("Jump");
				if(Value.cfg_jumppad_default_enablesounds){
					p.playSound(p.getLocation(), Sound.valueOf(Value.cfg_jumppad_default_sound.toUpperCase()), 100.0F, 100.0F);
				}
				if(Value.cfg_jumppad_default_enableeffects){
					p.getWorld().playEffect(p.getLocation(), Effect.valueOf(Value.cfg_jumppad_default_effect), 5);
				}
				if(Value.cfg_jumppad_default_enablemessage){
					p.sendMessage(TranslateAlternateColors.translateAlternateColorCodes(Value.cfg_jumppad_default_message));
				}
				if(Value.cfg_jumppad_disablefalldamage){
					Value.flyingPlayers.add(p.getUniqueId());
				}
				return;
			}
			return;
		}
	}
}
