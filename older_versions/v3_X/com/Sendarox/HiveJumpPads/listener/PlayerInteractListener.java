package com.Sendarox.HiveJumpPads.listener;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.Sendarox.HiveJumpPads.HiveJumpPads;
import com.Sendarox.HiveJumpPads.configuration.JumpPadConfiguration;
import com.Sendarox.HiveJumpPads.configuration.JumpPadListConfiguration;
import com.Sendarox.HiveJumpPads.configuration.VersionConfiguration;
import com.Sendarox.HiveJumpPads.utils.MySQLTables;
import com.Sendarox.HiveJumpPads.utils.TranslateAlternateColors;
import com.Sendarox.HiveJumpPads.utils.Value;

public class PlayerInteractListener implements Listener {

	private HiveJumpPads hjp;

	public PlayerInteractListener(HiveJumpPads hjp) {
		this.hjp = hjp;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	@SuppressWarnings("deprecation")
	private void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (!e.getAction().equals(Action.PHYSICAL)) {
			return;
		}
		if (Value.cfg_jumppad_disabledworlds.contains(p.getWorld().getName())) {
			return;
		}
		if (!Value.cfg_useAdvancedJump && p.getLocation().getWorld().getBlockAt(p.getLocation()).getRelative(0, -1, 0)
				.getTypeId() != Value.cfg_jumppad_default_blockid) {
			return;
		}
		if (!p.hasPermission("hivejumppads.use.jumppad")) {
			return;
		}
		if (Value.flyingPlayers.contains(p.getUniqueId())) {
			Value.flyingPlayers.remove(p.getUniqueId());
		}
		if (Value.cfg_useAdvancedJump) {
			if (!Value.cfg_mysql_usemysql) {
				this.advancedJump(e, p);
			} else {
				this.advancedJumpMySQL(e, p);
			}
		} else {
			this.Jump(e, p);
		}
	}

	@SuppressWarnings("deprecation")
	private void advancedJump(PlayerInteractEvent e, final Player p) {
		if (Value.cfg_jumppadregister) {
			int x = p.getLocation().getBlockX();
			int y = p.getLocation().getBlockY();
			int z = p.getLocation().getBlockZ();
			String world = p.getLocation().getWorld().getName();
			VersionConfiguration ver = new VersionConfiguration();
			List<Integer> missingids = ver.getMissingJumppadIDs();
			JumpPadListConfiguration jplc = new JumpPadListConfiguration();
			for (int i = 0; i < (int) ver.get("HiveJumpPads.jumpid"); i++) {
				if (missingids.contains(i)) {
					continue;
				}
				int x2 = (int) jplc.get("HiveJumpPads.JumpPad-" + i + ".X");
				int y2 = (int) jplc.get("HiveJumpPads.JumpPad-" + i + ".Y");
				int z2 = (int) jplc.get("HiveJumpPads.JumpPad-" + i + ".Z");
				String world2 = p.getLocation().getWorld().getName();
				if (x == x2 && y == y2 && z == z2 && world == world2) {
					JumpPadConfiguration jpc = new JumpPadConfiguration("JumpPad-" + i + ".yml", world);
					if ((boolean) jpc.get("HiveJumpPads.jumppad-" + i + ".LockedSize")) {
						int blockid = (int) jpc.get("HiveJumpPads.jumppad-" + i + ".BlockID");
						int pressureplateid = (int) jpc.get("HiveJumpPads.jumppad-" + i + ".PressurePlateID");
						Block block = e.getClickedBlock();
						Block plate = block.getRelative(BlockFace.SELF);
						if (plate.getTypeId() != pressureplateid) {
							return;
						}
						if (block.getRelative(0, -1, 0).getTypeId() == blockid
								&& block.getRelative(-1, -1, 0).getTypeId() == blockid
								&& block.getRelative(0, -1, -1).getTypeId() == blockid
								&& block.getRelative(1, -1, 0).getTypeId() == blockid
								&& block.getRelative(0, -1, 1).getTypeId() == blockid
								&& block.getRelative(1, -1, 1).getTypeId() == blockid
								&& block.getRelative(-1, -1, -1).getTypeId() == blockid
								&& block.getRelative(-1, -1, 1).getTypeId() == blockid
								&& block.getRelative(1, -1, -1).getTypeId() == blockid) {

							double lenght = (double) jpc.get("HiveJumpPads.jumppad-" + i + ".JumpLength");
							double height = (double) jpc.get("HiveJumpPads.jumppad-" + i + ".JumpHeight");
							p.setVelocity(p.getLocation().getDirection().setY(height).multiply(lenght));
							boolean useEffect = (boolean) jpc.get("HiveJumpPads.jumppad-" + i + ".EnableEffects");
							boolean useSound = (boolean) jpc.get("HiveJumpPads.jumppad-" + i + ".EnableSounds");
							boolean useMessage = (boolean) jpc.get("HiveJumpPads.jumppad-" + i + ".EnableMessage");
							String effect = (String) jpc.get("HiveJumpPads.jumppad-" + i + ".Effect");
							String sound = (String) jpc.get("HiveJumpPads.jumppad-" + i + ".Sound");
							String message = (String) jpc.get("HiveJumpPads.jumppad-" + i + ".Message");
							if (useSound) {
								p.playSound(p.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
							}
							if (useEffect) {
								p.getWorld().playEffect(p.getLocation(), Effect.valueOf(effect), 5);
							}
							if (useMessage) {
								if (!Value.messageSent.contains(p.getUniqueId())) {
									Value.messageSent.add(p.getUniqueId());
									p.sendMessage(TranslateAlternateColors.translateAlternateColorCodes(message));
									Bukkit.getScheduler().runTaskLater(hjp, new Runnable() {

										@Override
										public void run() {
											Value.messageSent.remove(p.getUniqueId());
										}
									}, 10L);
								}
							}
							if (Value.cfg_jumppad_disablefalldamage) {
								Value.flyingPlayers.add(p.getUniqueId());
							}
							e.setCancelled(true);
						}
					} else {
						int blockid = (int) jpc.get("HiveJumpPads.jumppad-" + i + ".BlockID");
						int pressureplateid = (int) jpc.get("HiveJumpPads.jumppad-" + i + ".PressurePlateID");
						Block block = e.getClickedBlock();
						Block plate = block.getRelative(BlockFace.SELF);
						if (plate.getTypeId() != pressureplateid) {
							return;
						}
						if (block.getRelative(0, -1, 0).getTypeId() == blockid) {
							double lenght = (double) jpc.get("HiveJumpPads.jumppad-" + i + ".JumpLength");
							double height = (double) jpc.get("HiveJumpPads.jumppad-" + i + ".JumpHeight");
							p.setVelocity(p.getLocation().getDirection().setY(height).multiply(lenght));
							boolean useEffect = (boolean) jpc.get("HiveJumpPads.jumppad-" + i + ".EnableEffects");
							boolean useSound = (boolean) jpc.get("HiveJumpPads.jumppad-" + i + ".EnableSounds");
							boolean useMessage = (boolean) jpc.get("HiveJumpPads.jumppad-" + i + ".EnableMessage");
							String effect = (String) jpc.get("HiveJumpPads.jumppad-" + i + ".Effect");
							String sound = (String) jpc.get("HiveJumpPads.jumppad-" + i + ".Sound");
							String message = (String) jpc.get("HiveJumpPads.jumppad-" + i + ".Message");
							if (useSound) {
								p.playSound(p.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
							}
							if (useEffect) {
								p.getWorld().playEffect(p.getLocation(), Effect.valueOf(effect), 5);
							}
							if (useMessage) {
								if (!Value.messageSent.contains(p.getUniqueId())) {
									Value.messageSent.add(p.getUniqueId());
									p.sendMessage(TranslateAlternateColors.translateAlternateColorCodes(message));
									Bukkit.getScheduler().runTaskLater(hjp, new Runnable() {

										@Override
										public void run() {
											Value.messageSent.remove(p.getUniqueId());
										}
									}, 10L);
								}
							}
							if (Value.cfg_jumppad_disablefalldamage) {
								Value.flyingPlayers.add(p.getUniqueId());
							}
							e.setCancelled(true);
						}
					}
					return;
				}
			}
		} else {
			int x = p.getLocation().getBlockX();
			int y = p.getLocation().getBlockY();
			int z = p.getLocation().getBlockZ();
			String world = p.getLocation().getWorld().getName();
			VersionConfiguration ver = new VersionConfiguration();
			List<Integer> missingids = ver.getMissingJumppadIDs();
			JumpPadListConfiguration jplc = new JumpPadListConfiguration();
			for (int i = 0; i < (int) ver.get("HiveJumpPads.jumpid"); i++) {
				if (missingids.contains(i)) {
					continue;
				}
				int x2 = (int) jplc.get("HiveJumpPads.JumpPad-" + i + ".X");
				int y2 = (int) jplc.get("HiveJumpPads.JumpPad-" + i + ".Y");
				int z2 = (int) jplc.get("HiveJumpPads.JumpPad-" + i + ".Z");
				String world2 = p.getLocation().getWorld().getName();
				if (x == x2 && y == y2 && z == z2 && world == world2) {
					JumpPadConfiguration jpc = new JumpPadConfiguration("JumpPad-" + i + ".yml", world);
					if ((boolean) jpc.get("HiveJumpPads.jumppad-" + i + ".LockedSize")) {
						int blockid = (int) jpc.get("HiveJumpPads.jumppad-" + i + ".BlockID");
						int pressureplateid = (int) jpc.get("HiveJumpPads.jumppad-" + i + ".PressurePlateID");
						Block block = e.getClickedBlock();
						Block plate = block.getRelative(BlockFace.SELF);
						if (plate.getTypeId() != pressureplateid) {
							return;
						}
						if (block.getRelative(0, -1, 0).getTypeId() == blockid
								&& block.getRelative(-1, -1, 0).getTypeId() == blockid
								&& block.getRelative(0, -1, -1).getTypeId() == blockid
								&& block.getRelative(1, -1, 0).getTypeId() == blockid
								&& block.getRelative(0, -1, 1).getTypeId() == blockid
								&& block.getRelative(1, -1, 1).getTypeId() == blockid
								&& block.getRelative(-1, -1, -1).getTypeId() == blockid
								&& block.getRelative(-1, -1, 1).getTypeId() == blockid
								&& block.getRelative(1, -1, -1).getTypeId() == blockid) {

							double lenght = (double) jpc.get("HiveJumpPads.jumppad-" + i + ".JumpLength");
							double height = (double) jpc.get("HiveJumpPads.jumppad-" + i + ".JumpHeight");
							p.setVelocity(p.getLocation().getDirection().setY(height).multiply(lenght));
							boolean useEffect = (boolean) jpc.get("HiveJumpPads.jumppad-" + i + ".EnableEffects");
							boolean useSound = (boolean) jpc.get("HiveJumpPads.jumppad-" + i + ".EnableSounds");
							boolean useMessage = (boolean) jpc.get("HiveJumpPads.jumppad-" + i + ".EnableMessage");
							String effect = (String) jpc.get("HiveJumpPads.jumppad-" + i + ".Effect");
							String sound = (String) jpc.get("HiveJumpPads.jumppad-" + i + ".Sound");
							String message = (String) jpc.get("HiveJumpPads.jumppad-" + i + ".Message");
							if (useSound) {
								p.playSound(p.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
							}
							if (useEffect) {
								p.getWorld().playEffect(p.getLocation(), Effect.valueOf(effect), 5);
							}
							if (useMessage) {
								if (!Value.messageSent.contains(p.getUniqueId())) {
									Value.messageSent.add(p.getUniqueId());
									p.sendMessage(TranslateAlternateColors.translateAlternateColorCodes(message));
									Bukkit.getScheduler().runTaskLater(hjp, new Runnable() {

										@Override
										public void run() {
											Value.messageSent.remove(p.getUniqueId());
										}
									}, 10L);
								}
							}
							if (Value.cfg_jumppad_disablefalldamage) {
								Value.flyingPlayers.add(p.getUniqueId());
							}
							e.setCancelled(true);
						}
					} else {
						int blockid = (int) jpc.get("HiveJumpPads.JumpPad-" + i + ".BlockID");
						int pressureplateid = (int) jpc.get("HiveJumpPads.JumpPad-" + i + ".PressurePlateID");
						Block block = e.getClickedBlock();
						Block plate = block.getRelative(BlockFace.SELF);
						if (plate.getTypeId() != pressureplateid) {
							return;
						}
						if (block.getRelative(0, -1, 0).getTypeId() == blockid) {
							double lenght = (double) jpc.get("HiveJumpPads.jumppad-" + i + ".JumpLength");
							double height = (double) jpc.get("HiveJumpPads.jumppad-" + i + ".JumpHeight");
							p.setVelocity(p.getLocation().getDirection().setY(height).multiply(lenght));
							boolean useEffect = (boolean) jpc.get("HiveJumpPads.jumppad-" + i + ".EnableEffects");
							boolean useSound = (boolean) jpc.get("HiveJumpPads.jumppad-" + i + ".EnableSounds");
							boolean useMessage = (boolean) jpc.get("HiveJumpPads.jumppad-" + i + ".EnableMessage");
							String effect = (String) jpc.get("HiveJumpPads.jumppad-" + i + ".Effect");
							String sound = (String) jpc.get("HiveJumpPads.jumppad-" + i + ".Sound");
							String message = (String) jpc.get("HiveJumpPads.jumppad-" + i + ".Message");
							if (useSound) {
								p.playSound(p.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
							}
							if (useEffect) {
								p.getWorld().playEffect(p.getLocation(), Effect.valueOf(effect), 5);
							}
							if (useMessage) {
								if (!Value.messageSent.contains(p.getUniqueId())) {
									Value.messageSent.add(p.getUniqueId());
									p.sendMessage(TranslateAlternateColors.translateAlternateColorCodes(message));
									Bukkit.getScheduler().runTaskLater(hjp, new Runnable() {

										@Override
										public void run() {
											Value.messageSent.remove(p.getUniqueId());
										}
									}, 10L);
								}
							}
							if (Value.cfg_jumppad_disablefalldamage) {
								Value.flyingPlayers.add(p.getUniqueId());
							}
							e.setCancelled(true);
						}
					}
					return;
				}
			}
			this.Jump(e, p);
			return;
		}
	}

	/** YOU SHOULDN'T USE THE MYSQL SERVICE AT ALL. IT'S VERY VERY BUGGY! I'LL FIX IT IN THE NEXT VERSIONS **/
	@SuppressWarnings("deprecation")
	private void advancedJumpMySQL(PlayerInteractEvent e, final Player p) {
		if (!Value.cfg_jumppadregister) {
			int x = p.getLocation().getBlockX();
			int y = p.getLocation().getBlockY();
			int z = p.getLocation().getBlockZ();
			World world = p.getLocation().getWorld();
			VersionConfiguration ver = new VersionConfiguration();
			List<Integer> missingids = ver.getMissingJumppadIDs();
			for (int i = 0; i < (int) ver.get("HiveJumpPads.jumpid"); i++) {
				if (missingids.contains(i)) {
					continue;
				}
				try {
					int x2 = (int) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
							Value.mysql_table_prefix + Value.mysql_table_jumppads, "X", i);
					int y2 = (int) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
							Value.mysql_table_prefix + Value.mysql_table_jumppads, "Y", i);
					int z2 = (int) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
							Value.mysql_table_prefix + Value.mysql_table_jumppads, "Z", i);
					String world2 = (String) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
							Value.mysql_table_prefix + Value.mysql_table_jumppads, "World", i);
					if (x == x2 && y == y2 && z == z2 && world == Bukkit.getWorld(world2)) {
						if ((boolean) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
								Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "LockedSize", i)) {
							int blockid = (int) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
									Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "BlockID", i);
							int pressureplateid = (int) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
									Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "PressureplateID", i);
							Block block = e.getClickedBlock();
							Block plate = block.getRelative(BlockFace.SELF);
							if (plate.getTypeId() != pressureplateid) {
								return;
							}
							if (block.getRelative(0, -1, 0).getTypeId() == blockid
									&& block.getRelative(1, -1, 0).getTypeId() == blockid
									&& block.getRelative(0, -1, 1).getTypeId() == blockid
									&& block.getRelative(-1, -1, 0).getTypeId() == blockid
									&& block.getRelative(0, -1, -1).getTypeId() == blockid
									&& block.getRelative(1, -1, 1).getTypeId() == blockid
									&& block.getRelative(-1, -1, -1).getTypeId() == blockid
									&& block.getRelative(-1, -1, 1).getTypeId() == blockid
									&& block.getRelative(1, -1, -1).getTypeId() == blockid) {

								double lenght = (double) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "JumpLength", i);
								double height = (double) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "JumpHeight", i);
								p.setVelocity(p.getLocation().getDirection().setY(height).multiply(lenght));
								boolean useEffect = (boolean) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "EnableEffects",
										i);
								boolean useSound = (boolean) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "EnableSounds",
										i);
								boolean useMessage = (boolean) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "EnableMessage",
										i);
								String effect = (String) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "Effect", i);
								String sound = (String) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "Sound", i);
								String message = (String) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "Message", i);
								if (useSound) {
									p.playSound(p.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
								}
								if (useEffect) {
									p.getWorld().playEffect(p.getLocation(), Effect.valueOf(effect), 5);
								}
								if (useMessage) {
									if (!Value.messageSent.contains(p.getUniqueId())) {
										Value.messageSent.add(p.getUniqueId());
										p.sendMessage(TranslateAlternateColors.translateAlternateColorCodes(message));
										Bukkit.getScheduler().runTaskLater(hjp, new Runnable() {

											@Override
											public void run() {
												Value.messageSent.remove(p.getUniqueId());
											}
										}, 10L);
									}
								}
								if (Value.cfg_jumppad_disablefalldamage) {
									Value.flyingPlayers.add(p.getUniqueId());
								}
								e.setCancelled(true);
								return;
							}
						} else {
							int blockid = (int) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
									Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "BlockID", i);
							int pressureplateid = (int) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
									Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "PressureplateID", i);
							Block block = e.getClickedBlock();
							Block plate = block.getRelative(BlockFace.SELF);
							if (plate.getTypeId() != pressureplateid) {
								return;
							}
							if (block.getRelative(0, -1, 0).getTypeId() == blockid) {
								double lenght = (double) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "JumpLength", i);
								double height = (double) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "JumpHeight", i);
								p.setVelocity(p.getLocation().getDirection().setY(height).multiply(lenght));
								boolean useEffect = (boolean) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "EnableEffects",
										i);
								boolean useSound = (boolean) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "EnableSounds",
										i);
								boolean useMessage = (boolean) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "EnableMessage",
										i);
								String effect = (String) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "Effect", i);
								String sound = (String) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "Sound", i);
								String message = (String) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "Message", i);
								if (useSound) {
									p.playSound(p.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
								}
								if (useEffect) {
									p.getWorld().playEffect(p.getLocation(), Effect.valueOf(effect), 5);
								}
								if (useMessage) {
									if (!Value.messageSent.contains(p.getUniqueId())) {
										Value.messageSent.add(p.getUniqueId());
										p.sendMessage(TranslateAlternateColors.translateAlternateColorCodes(message));
										Bukkit.getScheduler().runTaskLater(hjp, new Runnable() {

											@Override
											public void run() {
												Value.messageSent.remove(p.getUniqueId());
											}
										}, 10L);
									}
								}
								if (Value.cfg_jumppad_disablefalldamage) {
									Value.flyingPlayers.add(p.getUniqueId());
								}
								e.setCancelled(true);
							}
						}
					}
				} catch (Exception e_1) {
					p.sendMessage(Value.chatprefix + "§lMySQL Error occurred");
					p.sendMessage("§7| Can't read §aMySQL§7 row. Take a look at the §aconsole§7 output.");
					p.sendMessage("§7| HiveJumpPads is now using §a'useAdvancedJumpPads: false'§7!");
					Value.cfg_useAdvancedJump = false;
					Value.cfg_mysql_usemysql = false;
					Value.mysql_connected = false;
					Value.mysql_error = true;
					e_1.printStackTrace();
					e.setCancelled(true);
					return;
				}
			}
			this.Jump(e, p);
			return;
		} else {
			int x = p.getLocation().getBlockX();
			int y = p.getLocation().getBlockY();
			int z = p.getLocation().getBlockZ();
			World world = p.getLocation().getWorld();
			VersionConfiguration ver = new VersionConfiguration();
			List<Integer> missingids = ver.getMissingJumppadIDs();
			for (int i = 0; i < (int) ver.get("HiveJumpPads.jumpid"); i++) {
				if (missingids.contains(i)) {
					continue;
				}
				try {
					int x2 = (int) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
							Value.mysql_table_prefix + Value.mysql_table_jumppads, "X", i);
					int y2 = (int) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
							Value.mysql_table_prefix + Value.mysql_table_jumppads, "Y", i);
					int z2 = (int) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
							Value.mysql_table_prefix + Value.mysql_table_jumppads, "Z", i);
					String world2 = (String) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
							Value.mysql_table_prefix + Value.mysql_table_jumppads, "World", i);
					if (x == x2 && y == y2 && z == z2 && Bukkit.getWorld(world2) == world) {
						if ((boolean) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
								Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "LockedSize", i)) {
							int blockid = (int) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
									Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "BlockID", i);
							int pressureplateid = (int) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
									Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "PressureplateID", i);
							Block block = e.getClickedBlock();
							Block plate = block.getRelative(BlockFace.SELF);
							if (plate.getTypeId() != pressureplateid) {
								return;
							}
							if (block.getRelative(0, -1, 0).getTypeId() == blockid
									&& block.getRelative(1, -1, 0).getTypeId() == blockid
									&& block.getRelative(0, -1, 1).getTypeId() == blockid
									&& block.getRelative(-1, -1, 0).getTypeId() == blockid
									&& block.getRelative(0, -1, -1).getTypeId() == blockid
									&& block.getRelative(1, -1, 1).getTypeId() == blockid
									&& block.getRelative(-1, -1, -1).getTypeId() == blockid
									&& block.getRelative(-1, -1, 1).getTypeId() == blockid
									&& block.getRelative(1, -1, -1).getTypeId() == blockid) {

								double lenght = (double) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "JumpLength", i);
								double height = (double) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "JumpHeight", i);
								p.setVelocity(p.getLocation().getDirection().setY(height).multiply(lenght));
								boolean useEffect = (boolean) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "EnableEffects",
										i);
								boolean useSound = (boolean) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "EnableSounds",
										i);
								boolean useMessage = (boolean) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "EnableMessage",
										i);
								String effect = (String) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "Effect", i);
								String sound = (String) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "Sound", i);
								String message = (String) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "Message", i);
								if (useSound) {
									p.playSound(p.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
								}
								if (useEffect) {
									p.getWorld().playEffect(p.getLocation(), Effect.valueOf(effect), 5);
								}
								if (useMessage) {
									if (!Value.messageSent.contains(p.getUniqueId())) {
										Value.messageSent.add(p.getUniqueId());
										p.sendMessage(TranslateAlternateColors.translateAlternateColorCodes(message));
										Bukkit.getScheduler().runTaskLater(hjp, new Runnable() {

											@Override
											public void run() {
												Value.messageSent.remove(p.getUniqueId());
											}
										}, 10L);
									}
								}
								if (Value.cfg_jumppad_disablefalldamage) {
									Value.flyingPlayers.add(p.getUniqueId());
								}
								e.setCancelled(true);
								return;
							}
						} else {
							int blockid = (int) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
									Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "BlockID", i);
							int pressureplateid = (int) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
									Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "PressureplateID", i);
							Block block = e.getClickedBlock();
							Block plate = block.getRelative(BlockFace.SELF);
							if (plate.getTypeId() != pressureplateid) {
								return;
							}
							if (block.getRelative(0, -1, 0).getTypeId() == blockid) {
								double lenght = (double) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "JumpLength", i);
								double height = (double) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "JumpHeight", i);
								p.setVelocity(p.getLocation().getDirection().setY(height).multiply(lenght));
								boolean useEffect = (boolean) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "EnableEffects",
										i);
								boolean useSound = (boolean) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "EnableSounds",
										i);
								boolean useMessage = (boolean) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "EnableMessage",
										i);
								String effect = (String) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "Effect", i);
								String sound = (String) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "Sound", i);
								String message = (String) MySQLTables.getObjectFromDatabase(HiveJumpPads.mysql,
										Value.mysql_table_prefix + Value.mysql_table_jumppadsettings, "Message", i);
								if (useSound) {
									p.playSound(p.getLocation(), Sound.valueOf(sound), 1.0F, 1.0F);
								}
								if (useEffect) {
									p.getWorld().playEffect(p.getLocation(), Effect.valueOf(effect), 5);
								}
								if (useMessage) {
									if (!Value.messageSent.contains(p.getUniqueId())) {
										Value.messageSent.add(p.getUniqueId());
										p.sendMessage(TranslateAlternateColors.translateAlternateColorCodes(message));
										Bukkit.getScheduler().runTaskLater(hjp, new Runnable() {

											@Override
											public void run() {
												Value.messageSent.remove(p.getUniqueId());
											}
										}, 10L);
									}
								}
								if (Value.cfg_jumppad_disablefalldamage) {
									Value.flyingPlayers.add(p.getUniqueId());
								}
								e.setCancelled(true);
							}
						}
					}
				} catch (Exception e_1) {
					p.sendMessage(Value.chatprefix + "§lMySQL Error occurred");
					p.sendMessage("§7| Can't read §aMySQL§7 row. Take a look at the §aconsole§7 output.");
					p.sendMessage("§7| HiveJumpPads is now using §a'useAdvancedJumpPads: false'§7!");
					Value.cfg_useAdvancedJump = false;
					Value.cfg_mysql_usemysql = false;
					Value.mysql_connected = false;
					Value.mysql_error = true;
					e_1.printStackTrace();
					e.setCancelled(true);
					return;
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void Jump(PlayerInteractEvent e, final Player p) {
		Block block = e.getClickedBlock();
		Block plate = block.getRelative(BlockFace.SELF);
		if (Value.cfg_jumppad_default_lockedsize) {
			if (plate.getTypeId() != Value.cfg_jumppad_default_pressureplateid) {
				return;
			}
			if (block.getRelative(0, -1, 0).getTypeId() != Value.cfg_jumppad_default_blockid
					&& block.getRelative(1, -1, 0).getTypeId() != Value.cfg_jumppad_default_blockid
					&& block.getRelative(0, -1, 1).getTypeId() != Value.cfg_jumppad_default_blockid
					&& block.getRelative(0, -1, -1).getTypeId() != Value.cfg_jumppad_default_blockid
					&& block.getRelative(-1, -1, 0).getTypeId() != Value.cfg_jumppad_default_blockid
					&& block.getRelative(1, -1, 1).getTypeId() != Value.cfg_jumppad_default_blockid
					&& block.getRelative(-1, -1, -1).getTypeId() != Value.cfg_jumppad_default_blockid
					&& block.getRelative(1, -1, -1).getTypeId() != Value.cfg_jumppad_default_blockid
					&& block.getRelative(-1, -1, 1).getTypeId() != Value.cfg_jumppad_default_blockid) {
				return;
			}
			p.setVelocity(p.getLocation().getDirection().setY(Value.cfg_jumppad_default_jumpheight)
					.multiply(Value.cfg_jumppad_default_jumplength));
			if (Value.cfg_jumppad_default_enablesounds) {
				p.playSound(p.getLocation(), Sound.valueOf(Value.cfg_jumppad_default_sound), 1.0F, 1.0F);
			}
			if (Value.cfg_jumppad_default_enableeffects) {
				p.getWorld().playEffect(p.getLocation(), Effect.valueOf(Value.cfg_jumppad_default_effect), 5);
			}
			if (Value.cfg_jumppad_default_enablemessage) {
				if (!Value.messageSent.contains(p.getUniqueId())) {
					Value.messageSent.add(p.getUniqueId());
					p.sendMessage(
							TranslateAlternateColors.translateAlternateColorCodes(Value.cfg_jumppad_default_message));
					Bukkit.getScheduler().runTaskLater(hjp, new Runnable() {

						@Override
						public void run() {
							Value.messageSent.remove(p.getUniqueId());
						}
					}, 10L);
				}
			}
			if (Value.cfg_jumppad_disablefalldamage) {
				Value.flyingPlayers.add(p.getUniqueId());
			}
			e.setCancelled(true);
		} else {
			if (plate.getTypeId() != Value.cfg_jumppad_default_pressureplateid) {
				return;
			}
			if (block.getRelative(0, -1, 0).getTypeId() != Value.cfg_jumppad_default_blockid) {
				return;
			}
			p.setVelocity(p.getLocation().getDirection().setY(Value.cfg_jumppad_default_jumpheight)
					.multiply(Value.cfg_jumppad_default_jumplength));
			if (Value.cfg_jumppad_default_enablesounds) {
				p.playSound(p.getLocation(), Sound.valueOf(Value.cfg_jumppad_default_sound), 1.0F, 1.0F);
			}
			if (Value.cfg_jumppad_default_enableeffects) {
				p.getWorld().playEffect(p.getLocation(), Effect.valueOf(Value.cfg_jumppad_default_effect), 5);
			}
			if (Value.cfg_jumppad_default_enablemessage) {
				if (!Value.messageSent.contains(p.getUniqueId())) {
					Value.messageSent.add(p.getUniqueId());
					p.sendMessage(
							TranslateAlternateColors.translateAlternateColorCodes(Value.cfg_jumppad_default_message));
					Bukkit.getScheduler().runTaskLater(hjp, new Runnable() {

						@Override
						public void run() {
							Value.messageSent.remove(p.getUniqueId());
						}
					}, 10L);
				}
			}
			if (Value.cfg_jumppad_disablefalldamage) {
				Value.flyingPlayers.add(p.getUniqueId());
			}
			e.setCancelled(true);
		}
	}
}
