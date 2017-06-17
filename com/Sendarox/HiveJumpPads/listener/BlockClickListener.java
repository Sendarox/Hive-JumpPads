package com.Sendarox.HiveJumpPads.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.Sendarox.HiveJumpPads.HiveJumpPads;
import com.Sendarox.HiveJumpPads.configuration.JumpPadConfiguration;
import com.Sendarox.HiveJumpPads.configuration.JumpPadListConfiguration;
import com.Sendarox.HiveJumpPads.configuration.VersionConfiguration;
import com.Sendarox.HiveJumpPads.utils.JumpPadSaveType;
import com.Sendarox.HiveJumpPads.utils.Value;

public class BlockClickListener implements Listener {

	private List<UUID> cooldown = new ArrayList<UUID>();
	private HiveJumpPads hjp;

	public BlockClickListener(HiveJumpPads hjp) {
		this.hjp = hjp;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	private void onBlockClick(final PlayerInteractEvent e) {
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
			if (item.getType() == Material.AIR && item.getType() != Material.STICK) {
				return;
			}
			if (!e.getPlayer().hasPermission("hivejumppads.use.tool")) {
				return;
			}
			if (item.getItemMeta().getDisplayName() != "§6§oJumpPad-Tool") {
				return;
			}
			if (cooldown.contains(e.getPlayer().getUniqueId())) {
				return;
			}
			Block b = e.getClickedBlock();
			final int x = b.getX();
			final int y = b.getY();
			final int z = b.getZ();
			VersionConfiguration ver = new VersionConfiguration();
			final JumpPadListConfiguration jpl = new JumpPadListConfiguration();
			final int jumpid = (int) ver.get("HiveJumpPads.jumpid");
			final List<Integer> missingids = ver.getMissingJumppadIDs();
			for (int i = 0; i < jumpid; i++) {
				if (missingids.contains(i)) {
					continue;
				}
				if (Value.cfg_mysql_usemysql) {

					return;
				}
				if (JumpPadSaveType
						.valueOf((String) jpl.get("HiveJumpPads.JumpPad-" + i + ".Type")) == JumpPadSaveType.MYSQL) {
					e.getPlayer().sendMessage(Value.chatprefix + "Right-Click MySQL save is currently not supported.");
					return;
				}
				int x2 = (int) jpl.get("HiveJumpPads.JumpPad-" + i + ".X");
				int y2 = (int) jpl.get("HiveJumpPads.JumpPad-" + i + ".Y");
				int z2 = (int) jpl.get("HiveJumpPads.JumpPad-" + i + ".Z");
				if (x == x2 && y == y2 && z == z2) {
					JumpPadConfiguration jc = new JumpPadConfiguration("jumppad-" + i + ".yml", e.getPlayer().getWorld().getName());
					final Player p = e.getPlayer();
					p.sendMessage(Value.chatprefix_bold + "§6§lJumpPad #" + i + "§7 Overview");
					p.sendMessage("§6Location: §7X: " + x + ", Y: " + y + ", Z: " + z);
					p.sendMessage("§6BlockID: §7" + (int) jc.get("HiveJumpPads.jumppad-" + i + ".BlockID")
							+ "§7; §6PressureplateID:§7 " + (int) jc.get("HiveJumpPads.jumppad-" + i + ".PressurePlateID"));
					p.sendMessage("§6LockedSize: §a" + (boolean) jc.get("HiveJumpPads.jumppad-" + i + ".LockedSize")
							+ "§7 - §6EnableSounds: §a" + (boolean) jc.get("HiveJumpPads.jumppad-" + i + ".EnableSounds"));
					p.sendMessage("§6UseEffect: §a" + (boolean) jc.get("HiveJumpPads.jumppad-" + i + ".EnableEffects")
							+ "§7 - §6UseMessage: §a" + (boolean) jc.get("HiveJumpPads.jumppad-" + i + ".EnableMessage"));
					cooldown.add(p.getUniqueId());
					Bukkit.getScheduler().runTaskLater(hjp, new Runnable() {
						@Override
						public void run() {
							cooldown.remove(p.getUniqueId());
						}
					}, 20L);
					return;
				} else {
					continue;
				}
			}
		}
		return;
	}
}
