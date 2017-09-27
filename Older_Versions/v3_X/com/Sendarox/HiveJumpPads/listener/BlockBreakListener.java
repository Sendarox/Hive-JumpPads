package com.Sendarox.HiveJumpPads.listener;

import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.Sendarox.HiveJumpPads.HiveJumpPads;
import com.Sendarox.HiveJumpPads.configuration.JumpPadConfiguration;
import com.Sendarox.HiveJumpPads.configuration.JumpPadListConfiguration;
import com.Sendarox.HiveJumpPads.configuration.VersionConfiguration;
import com.Sendarox.HiveJumpPads.utils.JumpPadSaveType;
import com.Sendarox.HiveJumpPads.utils.MySQLTables;
import com.Sendarox.HiveJumpPads.utils.Value;

public class BlockBreakListener implements Listener {

	@EventHandler
	@SuppressWarnings("deprecation")
	private void onBlockBreak(BlockBreakEvent e) {
		if (!Value.cfg_useAdvancedJump) {
			return;
		}
		Player p = e.getPlayer();
		if (!p.hasPermission("hivejumppads.use.tool")) {
			return;
		}
		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		if (item.getType() == Material.AIR && item.getType() != Material.STICK) {
			return;
		}
		if (item.getItemMeta().getDisplayName() != "§6§oJumpPad-Tool") {
			return;
		}
		Location l = e.getBlock().getLocation();
		if (e.getBlock().getTypeId() == Value.cfg_jumppad_default_pressureplateid) {
			if (Value.cfg_jumppad_default_lockedsize) {
				if (l.getWorld().getBlockAt(l).getRelative(0, -1, 0).getTypeId() == Value.cfg_jumppad_default_blockid
						&& l.getWorld().getBlockAt(l).getRelative(-1, -1, 0)
								.getTypeId() == Value.cfg_jumppad_default_blockid
						&& l.getWorld().getBlockAt(l).getRelative(0, -1, -1)
								.getTypeId() == Value.cfg_jumppad_default_blockid
						&& l.getWorld().getBlockAt(l).getRelative(1, -1, 0)
								.getTypeId() == Value.cfg_jumppad_default_blockid
						&& l.getWorld().getBlockAt(l).getRelative(0, -1, 1)
								.getTypeId() == Value.cfg_jumppad_default_blockid
						&& l.getWorld().getBlockAt(l).getRelative(1, -1, 1)
								.getTypeId() == Value.cfg_jumppad_default_blockid
						&& l.getWorld().getBlockAt(l).getRelative(-1, -1, -1)
								.getTypeId() == Value.cfg_jumppad_default_blockid
						&& l.getWorld().getBlockAt(l).getRelative(1, -1, -1)
								.getTypeId() == Value.cfg_jumppad_default_blockid
						&& l.getWorld().getBlockAt(l).getRelative(-1, -1, 1)
								.getTypeId() == Value.cfg_jumppad_default_blockid) {

					VersionConfiguration ver = new VersionConfiguration();
					int jumppadid = (int) ver.get("HiveJumpPads.jumpid");
					List<Integer> missingids = ver.getMissingJumppadIDs();
					if (Value.cfg_mysql_usemysql) {
						try {
							for (int i = 0; i < jumppadid; i++) {
								if (missingids.contains(i)) {
									continue;
								}
								Object obj_mysql = MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppads, "JumpID", i);
								if (obj_mysql == null || obj_mysql == "" || obj_mysql == "null") {
									continue;
								}
								int x = (int) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppads, "X", i);
								int y = (int) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppads, "Y", i);
								int z = (int) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppads, "Z", i);
								String world = (String) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppads, "World", i);
								if (e.getBlock().getX() == x && e.getBlock().getY() == y && e.getBlock().getZ() == z
										&& Bukkit.getWorld(world) == e.getBlock().getWorld()) {
									p.sendMessage(
											Value.chatprefix + "The jumppad already exists as §6jumppad #" + i + "!");
									e.setCancelled(true);
									return;
								}
							}
							MySQLTables.insertNewJumpPadCoordinates(HiveJumpPads.mysql, jumppadid, e.getBlock().getX(),
									e.getBlock().getY(), e.getBlock().getZ(), e.getBlock().getWorld().getName());
							MySQLTables.insertNewJumpPad(HiveJumpPads.mysql, jumppadid,
									Value.cfg_jumppad_default_blockid, Value.cfg_jumppad_default_pressureplateid,
									Value.cfg_jumppad_default_lockedsize, Value.cfg_jumppad_default_jumplength,
									Value.cfg_jumppad_default_jumpheight, Value.cfg_jumppad_default_enablesounds,
									Value.cfg_jumppad_default_enableeffects, Value.cfg_jumppad_default_enablemessage,
									Value.cfg_jumppad_default_sound, Value.cfg_jumppad_default_effect,
									Value.cfg_jumppad_default_message);
							ver.set("HiveJumpPads.jumpid", jumppadid + 1);
							new JumpPadListConfiguration().add(jumppadid, e.getBlock().getX(), e.getBlock().getY(),
									e.getBlock().getZ(), e.getBlock().getWorld().getName(), JumpPadSaveType.MYSQL);
							p.sendMessage(Value.chatprefix + "Successfully created §6jumppad #" + jumppadid + "§7.");
						} catch (Exception e_1) {
							p.sendMessage(Value.chatprefix + "§lMySQL Error occurred");
							p.sendMessage("§7| Can't create §aMySQL§7 row. Take a look at the §aconsole§7 output.");
							p.sendMessage("§7| HiveJumpPads is now using §a'useAdvancedJumpPads: false'§7!");
							Value.cfg_useAdvancedJump = false;
							Value.cfg_mysql_usemysql = false;
							Value.mysql_connected = false;
							Value.mysql_error = true;
							e_1.printStackTrace();
							e.setCancelled(true);
							return;
						}
					} else {
						JumpPadListConfiguration cfg_jpl = new JumpPadListConfiguration();
						for (int i = 0; i < jumppadid; i++) {
							if (missingids.contains(i)) {
								continue;
							}
							int x = (int) cfg_jpl.get("HiveJumpPads.JumpPad-" + i + ".X");
							int y = (int) cfg_jpl.get("HiveJumpPads.JumpPad-" + i + ".Y");
							int z = (int) cfg_jpl.get("HiveJumpPads.JumpPad-" + i + ".Z");
							String world = (String) cfg_jpl.get("HiveJumpPads.JumpPad-" + i + ".World");
							if (x == e.getBlock().getX() && y == e.getBlock().getY() && z == e.getBlock().getZ()
									&& Bukkit.getWorld(world) == e.getBlock().getWorld()) {
								p.sendMessage(Value.chatprefix + "The jumppad is already registered as §6jumppad #" + i
										+ "§7.");
								return;
							}
						}
						cfg_jpl.add(jumppadid, e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ(),
								e.getBlock().getWorld().getName(), JumpPadSaveType.LOCAL);
						try {
							new JumpPadConfiguration("jumppad-" + jumppadid + ".yml", e.getBlock().getWorld().getName()).create(e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						ver.set("HiveJumpPads.jumpid", jumppadid + 1);
						p.sendMessage(Value.chatprefix + "The §6jumppad #" + jumppadid + "§7 was added.");
					}
					e.setCancelled(true);
					return;
				} else {
					p.sendMessage(Value.chatprefix + "There was no jumppad found.");
				}
				e.setCancelled(true);
			} else {
				if (l.getWorld().getBlockAt(l).getRelative(0, -1, 0).getTypeId() == Value.cfg_jumppad_default_blockid) {
					VersionConfiguration ver = new VersionConfiguration();
					int jumppadid = (int) ver.get("HiveJumpPads.jumpid");
					List<Integer> missingids = ver.getMissingJumppadIDs();
					if (Value.cfg_mysql_usemysql) {
						try {
							for (int i = 0; i < jumppadid; i++) {
								if (missingids.contains(i)) {
									continue;
								}
								Object obj_mysql = MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppads, "JumpID", i);
								if (obj_mysql == null || obj_mysql == "" || obj_mysql == "null") {
									continue;
								}
								int x = (int) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppads, "X", i);
								int y = (int) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppads, "Y", i);
								int z = (int) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppads, "Z", i);
								String world = (String) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppads, "World", i);
								if (e.getBlock().getX() == x && e.getBlock().getY() == y && e.getBlock().getZ() == z
										&& Bukkit.getWorld(world) == e.getBlock().getWorld()) {
									p.sendMessage(
											Value.chatprefix + "The jumppad already exists as §6jumppad #" + i + "!");
									e.setCancelled(true);
									return;
								}
							}
							MySQLTables.insertNewJumpPadCoordinates(HiveJumpPads.mysql, jumppadid, e.getBlock().getX(),
									e.getBlock().getY(), e.getBlock().getZ(), e.getBlock().getWorld().getName());
							MySQLTables.insertNewJumpPad(HiveJumpPads.mysql, jumppadid,
									Value.cfg_jumppad_default_blockid, Value.cfg_jumppad_default_pressureplateid,
									Value.cfg_jumppad_default_lockedsize, Value.cfg_jumppad_default_jumplength,
									Value.cfg_jumppad_default_jumpheight, Value.cfg_jumppad_default_enablesounds,
									Value.cfg_jumppad_default_enableeffects, Value.cfg_jumppad_default_enablemessage,
									Value.cfg_jumppad_default_sound, Value.cfg_jumppad_default_effect,
									Value.cfg_jumppad_default_message);
							ver.set("HiveJumpPads.jumpid", jumppadid + 1);
							new JumpPadListConfiguration().add(jumppadid, e.getBlock().getX(), e.getBlock().getY(),
									e.getBlock().getZ(), e.getBlock().getWorld().getName(), JumpPadSaveType.MYSQL);
							p.sendMessage(Value.chatprefix + "Successfully created §6jumppad #" + jumppadid + "§7.");
						} catch (Exception e_1) {
							p.sendMessage(Value.chatprefix + "§lMySQL Error occurred");
							p.sendMessage("§7| Can't create §aMySQL§7 row. Take a look at the §aconsole§7 output.");
							p.sendMessage("§7| HiveJumpPads is now using §a'useAdvancedJumpPads: false'§7!");
							Value.cfg_useAdvancedJump = false;
							Value.cfg_mysql_usemysql = false;
							Value.mysql_connected = false;
							Value.mysql_error = true;
							e_1.printStackTrace();
							e.setCancelled(true);
							return;
						}
					} else {
						JumpPadListConfiguration cfg_jpl = new JumpPadListConfiguration();
						for (int i = 0; i < jumppadid; i++) {
							if (missingids.contains(i)) {
								continue;
							}
							int x = (int) cfg_jpl.get("HiveJumpPads.JumpPad-" + i + ".X");
							int y = (int) cfg_jpl.get("HiveJumpPads.JumpPad-" + i + ".Y");
							int z = (int) cfg_jpl.get("HiveJumpPads.JumpPad-" + i + ".Z");
							String world = (String) cfg_jpl.get("HiveJumpPads.JumpPad-" + i + ".World");
							if (x == e.getBlock().getX() && y == e.getBlock().getY() && z == e.getBlock().getZ()
									&& Bukkit.getWorld(world) == e.getBlock().getWorld()) {
								p.sendMessage(Value.chatprefix + "The jumppad is already registered as §6jumppad #" + i
										+ "§7.");
								return;
							}
						}
						cfg_jpl.add(jumppadid, e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ(),
								e.getBlock().getWorld().getName(), JumpPadSaveType.LOCAL);
						try {
							new JumpPadConfiguration("jumppad-" + jumppadid + ".yml", e.getBlock().getWorld().getName()).create(e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						ver.set("HiveJumpPads.jumpid", jumppadid + 1);
						p.sendMessage(Value.chatprefix + "The §6jumppad #" + jumppadid + "§7 was added.");
					}
					e.setCancelled(true);
					return;
				} else {
					p.sendMessage(Value.chatprefix + "There was no jumppad found.");
					e.setCancelled(true);
				}
			}
		} else {
			p.sendMessage(Value.chatprefix + "There was no jumppad found.");
			e.setCancelled(true);
		}
	}
}
