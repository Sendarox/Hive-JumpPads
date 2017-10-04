package com.Sendarox.HiveJumpPads.commands;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.Sendarox.HiveJumpPads.HiveJumpPads;
import com.Sendarox.HiveJumpPads.configuration.JumpPadConfiguration;
import com.Sendarox.HiveJumpPads.configuration.JumpPadListConfiguration;
import com.Sendarox.HiveJumpPads.configuration.VersionConfiguration;
import com.Sendarox.HiveJumpPads.update.Updater.UpdateResult;
import com.Sendarox.HiveJumpPads.utils.JumpPadHelper;
import com.Sendarox.HiveJumpPads.utils.Value;

public class HiveJumpPadsCommand implements CommandExecutor {

	private HiveJumpPads hjp;
	public HiveJumpPadsCommand(HiveJumpPads hjp) {
		this.hjp = hjp;
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public boolean onCommand(CommandSender s, Command c, String w, String[] a) {
		if(a.length == 0){
			s.sendMessage(Value.chatprefix_bold + "HiveJumpPads v"+Value.version+" developed by Sendarox");
			s.sendMessage(Value.chatprefix_bold + "Use §6'/hjp help'§7 for a list of commands");
			return true;
		} else if(a.length == 1){
			if(a[0].equalsIgnoreCase("help")){
				s.sendMessage("§6§lHiveJumpPads v"+Value.version+" Help");
				s.sendMessage("§7| §a/hivejumppads info §7- shows a short plugin info");
				s.sendMessage("§7| §a/hivejumppads wiki§7 - shows an url to the wiki-page");
				if(Value.cfg_useAdvancedJump && s.hasPermission("hivejumppads.use.tool") && s instanceof Player) 
					s.sendMessage("§7| §a/hivejumppads tool§7 - gives you the jumppad creation tool");
				if(Value.cfg_useAdvancedJump && s.hasPermission("hivejumppads.command.teleport") && s instanceof Player)
					s.sendMessage("§7| §a/hivejumppads tp <id> §7- teleports you to jumppad #<id>");
				if(Value.cfg_useAdvancedJump && s.hasPermission("hivejumppads.command.remove"))
					s.sendMessage("§7| §a/hivejumppads remove <id> §7- removes a advanced jumppad");
				if(s.hasPermission("hivejumppads.command.reload"))
					s.sendMessage("§7| §a/hivejumppads reload§7 - reloads the plugin");
				if(s.hasPermission("hivejumppads.command.update"))
					s.sendMessage("§7| §a/hivejumppads update [args]§7 - check and manage updates");
				return true;
			} else if(a[0].equalsIgnoreCase("info")){
				s.sendMessage(Value.chatprefix + "Current version: §6v"+Value.version+" "+Value.codename+".");
				s.sendMessage(Value.chatprefix + "HiveJumpPads developed by Sendarox.");
				s.sendMessage("§6§lWebsite:§7 https://dev.bukkit.org/projects/hive-jumppads/");
				return true;
			} else if(a[0].equalsIgnoreCase("reload")){
				if(s.hasPermission("hivejumppads.command.reload")){
					s.sendMessage(Value.chatprefix + "§7HiveJumpPads §7v"+Value.version+" reloading...");
					try {
						hjp.onDisable();
						hjp.onEnable();
						s.sendMessage(Value.chatprefix + "§7HiveJumpPads v"+Value.version+" reload was §asuccessful§7!");
						if(Value.mysql_error == true){
							s.sendMessage(Value.chatprefix + "§7...§lbut§7 HiveJumpPads couldn't connect with the database! Take a look at the console output!");
							return true;
						}
					} catch (Exception e){
						s.sendMessage(Value.chatprefix + "§7HiveJumpPads v"+Value.version+" reload was §cfailed§7!");
						e.printStackTrace();
						return true;
					}
					return true;
				} else {
					s.sendMessage(Value.message_noperm);
					return true;
				}
			} else if(a[0].equalsIgnoreCase("wiki")){
				s.sendMessage("§6§lHiveJumpPads Wiki:");
				s.sendMessage("§ehttps://dev.bukkit.org/projects/hive-jumppads/pages/wiki");
				return true;
			} else if(a[0].equalsIgnoreCase("update")){
				if(!s.hasPermission("hivejumppads.command.update")){
					s.sendMessage(Value.message_noperm);
					return true;
				}
				s.sendMessage(Value.chatprefix_bold + "Current version: §6Hive JumpPads v"+Value.version + " " + Value.codename + ".");
				if(Value.sv_updateNeeded == null){
					s.sendMessage("§7| Automatic check is disabled. Checking for updates...");
					this.hjp.forceUpdateCheck();
					s.sendMessage(" ");
				} 
				if(Value.sv_updateNeeded){
					if(Value.cfg_update_download && Value.sv_updateResult == UpdateResult.SUCCESS){
						s.sendMessage(Value.chatprefix_bold + "An update to §a"+Value.sv_updateVersion+" was §6found and downloaded§7."); 
						s.sendMessage(Value.chatprefix_bold + "Use §6'/hjp update help'§7 for a list of all updater commands.");
						return true;
					} else if(Value.sv_updateResult == UpdateResult.UPDATE_AVAILABLE && !Value.cfg_update_download){
						s.sendMessage(Value.chatprefix_bold + "An update to §a"+Value.sv_updateVersion+"§7 was §6found§7.");
						s.sendMessage("§7| Use §6'/hjp update download'§7 to download the update.");
						s.sendMessage("§7| Use §6'/hjp update url'§7 to get the manual download link.");
						s.sendMessage("§7| Use §6'/hjp update help'§7 for a list of all updater commands.");
						return true;
					} else {
						s.sendMessage(Value.chatprefix_bold + "There is §nno§7 new update for §6Hive JumpPads v"+Value.version+"§7.");
						s.sendMessage(Value.chatprefix_bold + "Use §6'/hjp update help'§7 for a list of all updater commands.");
						return true;
					}
				} else {
					if(Value.sv_updateResult == UpdateResult.NO_UPDATE) {
						s.sendMessage(Value.chatprefix_bold + "There is §nno§7 update for §6Hive JumpPads v"+Value.version+"§7.");
						s.sendMessage("§7| Use §6'/hjp update check'§7 for a force refresh.");
						s.sendMessage("§7| Use §6'/hjp update help'§7 for a list of all updater commands.");
						return true;
					} else {
						s.sendMessage(Value.chatprefix_bold + "Unhandled cause!");
					}
				}
			} else if(a[0].equalsIgnoreCase("tp") && Value.cfg_useAdvancedJump && s instanceof Player || a[0].equalsIgnoreCase("teleport") && Value.cfg_useAdvancedJump && s instanceof Player){
				if(!s.hasPermission("hivejumppads.command.teleport")){
					s.sendMessage(Value.message_noperm);
					return true;
				}
				s.sendMessage(Value.chatprefix + "Please use §6'/hjp tp <jumppad-id>'§7!");
				return true;
			} else if(a[0].equalsIgnoreCase("tool") && Value.cfg_useAdvancedJump && s instanceof Player){
				if(!s.hasPermission("hivejumppads.use.tool")){
					s.sendMessage(Value.message_noperm);
					return true;
				}
				List<String> lore = new ArrayList<String>();
				lore.add("§6Left-click:§7 Creates a new jumppad!");
				lore.add("§6Right-click:§7 Gets jumppad information!");
				
				ItemStack tool = new ItemStack(Material.STICK);
				ItemMeta tool_meta = tool.getItemMeta();
				tool_meta.setDisplayName("§6§oJumpPad-Tool");
				tool_meta.setLore(lore);
				tool.setItemMeta(tool_meta);
				
				Player p = (Player) s;
				p.getInventory().setItemInMainHand(tool);
				p.sendMessage(Value.chatprefix_bold + "§l§aHiveJumpPad Tool");
				p.sendMessage("§7| §6Left-click§7 on the §apressureplate§7 to register a new jumppad!");
				p.sendMessage("§7| §6Right-click§7 on the §apressureplate§7 to get jumppad informations!");
				return true;
			} else if(a[0].equalsIgnoreCase("remove") && Value.cfg_useAdvancedJump || a[0].equalsIgnoreCase("rm") && Value.cfg_useAdvancedJump){
				if(!s.hasPermission("hivejumppads.command.remove")){
					s.sendMessage(Value.message_noperm);
					return true;
				}
				s.sendMessage(Value.chatprefix + "Please use §6'/hjp remove <jumppad-id>'§7!");
				return true;
			} else {
				s.sendMessage(Value.chatprefix + Value.message_help);
				return true;
			}
		} else if(a.length == 2){
			if(a[0].equalsIgnoreCase("tp") && Value.cfg_useAdvancedJump && s instanceof Player || a[0].equalsIgnoreCase("teleport") && Value.cfg_useAdvancedJump && s instanceof Player){
				String arg2 = a[1].toString();
				int jumppadid;
				try {
					jumppadid = Integer.parseInt(arg2);
				} catch (NumberFormatException e){
					s.sendMessage(Value.chatprefix + "Please enter a valid jumppad-id!");
					return true;
				}
				Player p = (Player) s;
				if(!JumpPadHelper.isJumpPadExisting(jumppadid)){
					p.sendMessage(Value.chatprefix + "The jumppad §6#"+jumppadid+"§7 was not found!");
					return true;
				}
				JumpPadConfiguration cfg_jumppad = new JumpPadConfiguration("JumpPad-"+jumppadid+".yml", null);
				int x = (int) cfg_jumppad.get("HiveJumpPads.JumpPad-"+jumppadid+"-coordinates.X");
				int y = (int) cfg_jumppad.get("HiveJumpPads.JumpPad-"+jumppadid+"-coordinates.Y");
				int z = (int) cfg_jumppad.get("HiveJumpPads.JumpPad-"+jumppadid+"-coordinates.Z");
				World world = Bukkit.getWorld((String)cfg_jumppad.get("HiveJumpPads.JumpPad-"+jumppadid+"-coordinates.World"));
				p.teleport(new Location(world, x, y, z - 2));
				p.sendMessage(Value.chatprefix + "Teleported to jumppad §6#"+jumppadid+"§7.");
				return true;
			} else if(a[0].equalsIgnoreCase("remove") && Value.cfg_useAdvancedJump || a[0].equalsIgnoreCase("rm") && Value.cfg_useAdvancedJump){
				if(!s.hasPermission("hivejumppads.command.remove")){
					s.sendMessage(Value.message_noperm);
					return true;
				}
				int jumppadid;
				try {
					jumppadid = Integer.parseInt(a[1].toString());
				} catch (NumberFormatException e){
					s.sendMessage(Value.chatprefix + "Please enter a valid jumppad-id!");
					return true;
				}
				VersionConfiguration ver = new VersionConfiguration();
				List<String> missingids = (List<String>) ver.get("HiveJumpPads.missing-ids");
				if(!missingids.contains(String.valueOf(jumppadid)) && jumppadid <= (int) ver.get("HiveJumpPads.jumpid")){
					s.sendMessage(Value.chatprefix + "The jumppad §6#"+jumppadid+"§7 was successfully removed!");
					ver.addMissingJumppadID(jumppadid);                    
					try {
						new JumpPadConfiguration("JumpPad-"+jumppadid+".yml", null).remove();
						new JumpPadListConfiguration().delete(jumppadid);
					} catch (NoSuchFileException e) {
						e.printStackTrace();
					} catch (DirectoryNotEmptyException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if(jumppadid > (int) ver.get("HiveJumpPads.jumpid")){
					s.sendMessage(Value.chatprefix + "The jumppad §6#"+jumppadid+"§7 is not existing!");
				} else {
					s.sendMessage(Value.chatprefix + "The jumppad §6#"+jumppadid+"§7 is already deleted!");
				}
				return true;
			} else if(a[0].equalsIgnoreCase("update")){
				if(!s.hasPermission("hivejumppads.command.update")){
					s.sendMessage(Value.message_noperm);
					return true;
				}
				if(a[1].equalsIgnoreCase("help")){
					s.sendMessage("§a§lHiveJumpPads Update Help");
					s.sendMessage("§7| §6/hivejumppads update check§7 - check for updates");
					s.sendMessage("§7| §6/hivejumppads update download§7 - download updates");
					s.sendMessage("§7| §6/hivejumppads update url§7 - update file link");
					return true;
				} else if(a[1].equalsIgnoreCase("url") || a[1].equalsIgnoreCase("link")){
					if(Value.sv_updateNeeded == null){
						this.hjp.forceUpdateCheck();
					}
					s.sendMessage(Value.chatprefix + "§6HiveJumpPads §a"+Value.sv_updateVersion+" §7download URL:");
					s.sendMessage("§e" + Value.sv_updateUrl);
					return true;
				} else if(a[1].equalsIgnoreCase("check")){
					this.hjp.forceUpdateCheck();
					if(Value.sv_updateNeeded){
						s.sendMessage(Value.chatprefix_bold + "An update to §a"+Value.sv_updateVersion+"§7 was §6found§7.");
						s.sendMessage("§7| Use §6'/hjp update download'§7 to download the update.");
						s.sendMessage("§7| Use §6'/hjp update url'§7 to get the manual download link.");
						return true;
					} else {
						s.sendMessage(Value.chatprefix + "There was §nno§7 update found for §6HiveJumpPads v"+Value.version+".");
						return true;
					}
				} else if(a[1].equalsIgnoreCase("download")){
					if(Value.sv_updateNeeded == null){
						this.hjp.forceUpdateCheck();
					}
					if(!Value.sv_updateNeeded){
						s.sendMessage(Value.chatprefix + "There is §nno§7 new update for §6HiveJumpPads v"+Value.version+"§7 to download :(.");
						return true;
					}
					if(Value.cfg_update_download){
						s.sendMessage(Value.chatprefix + "The update was already downloaded and will be installed at the next server restart :).");
						return true;
					}
					this.hjp.forceUpdateDownload();
					if(Value.sv_updateResult == UpdateResult.SUCCESS){
						s.sendMessage(Value.chatprefix + "The update §6"+Value.sv_updateVersion+"§7 was §6successfully§7 downloaded.");
						s.sendMessage(Value.chatprefix + "It will be installed at the §6next server restart§7.");
						return true;
					} else {
						s.sendMessage(Value.chatprefix + "Unhandled download exception. Please install it manually.");
						s.sendMessage(Value.chatprefix + "Use §6'/hjp update url'§7 for a download link.");
						return true;
					}
				} else {
					s.sendMessage(Value.chatprefix + "Unknown update command. Use §6'/hjp update help'§7.");
					return true;
				}
			} else {
				s.sendMessage(Value.chatprefix + Value.message_help);
				return true;
			}
		} else {
			s.sendMessage(Value.chatprefix + Value.message_help);
			return true;
		}
		return true;
	}

}
