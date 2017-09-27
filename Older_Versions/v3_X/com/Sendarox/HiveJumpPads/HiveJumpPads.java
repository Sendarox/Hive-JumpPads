package com.Sendarox.HiveJumpPads;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.Sendarox.HiveJumpPads.commands.HiveJumpPadsCommand;
import com.Sendarox.HiveJumpPads.configuration.Configuration;
import com.Sendarox.HiveJumpPads.configuration.DefaultJumpPadConfiguration;
import com.Sendarox.HiveJumpPads.configuration.JumpPadListConfiguration;
import com.Sendarox.HiveJumpPads.configuration.VersionConfiguration;
import com.Sendarox.HiveJumpPads.listener.BlockBreakListener;
import com.Sendarox.HiveJumpPads.listener.BlockClickListener;
import com.Sendarox.HiveJumpPads.listener.PlayerFalldamageListener;
import com.Sendarox.HiveJumpPads.listener.PlayerInteractListener;
import com.Sendarox.HiveJumpPads.listener.PlayerJoinListener;
import com.Sendarox.HiveJumpPads.listener.PlayerMoveListener;
import com.Sendarox.HiveJumpPads.update.Updater;
import com.Sendarox.HiveJumpPads.update.Updater.UpdateResult;
import com.Sendarox.HiveJumpPads.update.Updater.UpdateType;
import com.Sendarox.HiveJumpPads.utils.Metrics;
import com.Sendarox.HiveJumpPads.utils.MySQL;
import com.Sendarox.HiveJumpPads.utils.MySQLTables;
import com.Sendarox.HiveJumpPads.utils.Value;

public class HiveJumpPads extends JavaPlugin {
	
	/**
	 * @author Sendarox
	 * @version 3.0
	 * @license GNU General Public License version 3 (GPLv3)
	 * 
	 */
	
	public static MySQL mysql;
	
	@Override
	public void onEnable() {
		System.out.println("[HiveJumpPads] Loading Hive JumpPads v"+Value.version+" '"+Value.codename+"' developed by Sendarox");
		this.loadConfiguration();
		this.loadCommands();
		this.loadEvents();
		this.loadMySQL();
		this.checkForUpdates();
		this.loadMetrics();
		System.out.println("[HiveJumpPads] Successfully loaded Hive JumpPads v"+Value.version);
	}
	
	@Override
	public void onDisable() {
		System.out.println("[HiveJumpPads] Disabling Hive JumpPads v"+Value.version+" '"+Value.codename+"' developed by Sendarox");
		HandlerList.unregisterAll(this);
		System.out.println("[HiveJumpPads] Successfully disabled Hive JumpPads v"+Value.version);
	}
	
	private void loadConfiguration(){
		Value.dataFolder = this.getDataFolder();
		new Configuration().create();
		new DefaultJumpPadConfiguration().create();;
		new JumpPadListConfiguration().create();
		new VersionConfiguration().create();
	}
	
	private void loadCommands(){
		this.getCommand("hivejumppads").setExecutor(new HiveJumpPadsCommand(this));
	}
	
	private void loadEvents(){
		PluginManager pm = this.getServer().getPluginManager();
		if(Value.cfg_useLegacyJump)
			pm.registerEvents(new PlayerMoveListener(), this);
		else 
			pm.registerEvents(new PlayerInteractListener(this), this);
		if(Value.cfg_useAdvancedJump){
			pm.registerEvents(new BlockBreakListener(), this);
			pm.registerEvents(new BlockClickListener(this), this);
		}
		if(Value.cfg_jumppad_disablefalldamage)
			pm.registerEvents(new PlayerFalldamageListener(), this);
		if(Value.cfg_update_updatecheck)
			pm.registerEvents(new PlayerJoinListener(), this);
	}
	
	private void loadMySQL(){
		Value.mysql_connected = false;
		Value.mysql_error = false;
		if(Value.cfg_mysql_usemysql && Value.cfg_useAdvancedJump){
			System.out.println("[HiveJumpPads] MySQL-Save is enabled. Trying to connect.");
			try {
				HiveJumpPads.mysql = new MySQL();
				Value.mysql_connected = true;
				Value.mysql_error = false;
				MySQLTables.createJumpPadSettingTable(HiveJumpPads.mysql);
				MySQLTables.createJumpPadTable(HiveJumpPads.mysql);
				System.out.println("[HiveJumpPads] MySQL-Save is ready to be used!");
				return;
			} catch (Exception e){
				Value.mysql_connected = false;
				Value.mysql_error = true;
				System.err.println("[HiveJumpPads] Couldn't connect with the MySQL database!");
				System.err.println("[HiveJumpPads] Exception: "+e.getMessage());
				if(Value.cfg_useAdvancedJump){
					System.err.println("[HiveJumpPads] 'useAdvancedJump: true' can't be used!\n>> Using 'useAdvancedJump: false' instead!");
					Value.cfg_useAdvancedJump = false;
					Value.cfg_mysql_usemysql = false;
				}
				return;
			}
		} else {
			System.out.println("[HiveJumpPads] MySQL-Save is disabled. Skipping connection.");
			return;
		}
	}
	
	private void checkForUpdates(){
		if(Value.cfg_update_updatecheck){
			UpdateType type;
			if(Value.cfg_update_download){
				type = UpdateType.DEFAULT;
			} else {
				type = UpdateType.NO_DOWNLOAD;
			}
			Updater updater = new Updater(this, Value.bukkitdevid, this.getFile(), type, false);
			if(updater.getResult() == UpdateResult.NO_UPDATE){
				System.out.println("[HiveJumpPads] No update was found for Hive JumpPads v"+Value.version);
				Value.sv_updateNeeded = false;
				Value.sv_updateResult = updater.getResult();
				return;
			}
			if(updater.getResult() == UpdateResult.UPDATE_AVAILABLE){
				System.out.println("[HiveJumpPads] An update was found for Hive JumpPads v"+Value.version);
				System.out.println("[HiveJumpPads] New version: "+updater.getLatestName()+" for Bukkit v"+updater.getLatestGameVersion());
				System.out.println("[HiveJumpPads] Automatic update download is disabled!\n| You can use '/hjp update download' to download the update.\n| You can use '/hjp update url' to get the download url.");
				Value.sv_updateNeeded = true;
				Value.sv_updateUrl = updater.getLatestFileLink();
				Value.sv_updateVersion = updater.getLatestName().replace("HiveJumpPads ", "");
				Value.sv_updateResult = updater.getResult();
				return;
			}
			if(updater.getResult() == UpdateResult.SUCCESS){
				System.out.println("[HiveJumpPads] An update was found for Hive JumpPads v"+Value.version);
				System.out.println("[HiveJumppads] It was successfully downloaded an will be installed at the next server restart!");
				Value.sv_updateNeeded = true;
				Value.sv_updateUrl = updater.getLatestFileLink();
				Value.sv_updateVersion = updater.getLatestName().replace("HiveJumpPads ", "");
				Value.sv_updateResult = updater.getResult();
				return;
			}
		} else {
			System.out.println("[HiveJumpPads] Automatic update-check is disabled. Skipping check.");
			Value.sv_updateNeeded = null;
			return;
		}
	}
	
	private void loadMetrics(){
		if(Value.cfg_sendServerStatistics){
			Metrics metrics = new Metrics(this);
			metrics.addCustomChart(new Metrics.SimplePie("used_jump-system") {
				
				@Override
				public String getValue() {
					String s;
					if(Value.cfg_useLegacyJump)
						s = "Legacy Jump-System";
					else
						s = "New Jump-System";
					return s;
				}
			});
			metrics.addCustomChart(new Metrics.SimplePie("using_advanced_jump-system") {
				
				@Override
				public String getValue() {
					String s;
					if(Value.cfg_useAdvancedJump)
						s = "Yes";
					else
						s = "No";
					return s;
				}
			});
			metrics.addCustomChart(new Metrics.SimplePie("using_updatechecker") {
				
				@Override
				public String getValue() {
					String s;
					if(Value.cfg_update_updatecheck)
						s = "Yes";
					 else 
						s = "No";
					return s;
				}
			});
			metrics.addCustomChart(new Metrics.SimplePie("using_updatedownloader") {
				
				@Override
				public String getValue() {
					String s;
					if(Value.cfg_update_download)
						s = "Yes";
					else
						s = "No";
					return s;
				}
			});
			metrics.addCustomChart(new Metrics.SimplePie("register_each_jumppad") {
				
				@Override
				public String getValue() {
					String s;
					if(Value.cfg_jumppadregister)
						s = "Yes";
					else
						s = "No";
					return s;
				}
			});
			metrics.addCustomChart(new Metrics.SimplePie("using_mysql") {
				
				@Override
				public String getValue() {
					String s;
					if(Value.cfg_mysql_usemysql)
						s = "Yes";
					else
						s = "No";
					return s;
				}
			});
			metrics.addCustomChart(new Metrics.SimplePie("default_blockid") {
				
				@Override
				public String getValue() {
					return String.valueOf(Value.cfg_jumppad_default_blockid);
				}
			});
			metrics.addCustomChart(new Metrics.SimplePie("default_pressureplateid") {
				
				@Override
				public String getValue() {
					return String.valueOf(Value.cfg_jumppad_default_pressureplateid);
				}
			});
			System.out.println("[HiveJumpPads] Metrics for HiveJumpPads successfully enabled.");
			return;
		} else {
			System.out.println("[HiveJumpPads] Metrics is disabled. Skipping initialization.");
			return;
		}
	}

	public void forceUpdateCheck(){
		Updater updater = new Updater(this, Value.bukkitdevid, this.getFile(), UpdateType.NO_DOWNLOAD, false);
		Value.sv_updateUrl = updater.getLatestFileLink();
		Value.sv_updateVersion = updater.getLatestName().replace("HiveJumpPads ", "");
		Value.sv_updateResult = updater.getResult();
		if(updater.getResult() == UpdateResult.SUCCESS || updater.getResult() == UpdateResult.UPDATE_AVAILABLE){
			Value.sv_updateNeeded = true;
		} else {
			Value.sv_updateNeeded = false;
		}
	}
	
	public void forceUpdateDownload(){
		Updater updater = new Updater(this, Value.bukkitdevid, this.getFile(), UpdateType.DEFAULT, false);
		Value.sv_updateUrl = updater.getLatestFileLink();
		Value.sv_updateVersion = updater.getLatestName().replace("HiveJumpPads ", "");
		Value.sv_updateResult = updater.getResult();
		if(updater.getResult() == UpdateResult.SUCCESS || updater.getResult() == UpdateResult.UPDATE_AVAILABLE){
			Value.sv_updateNeeded = true;
		} else {
			Value.sv_updateNeeded = false;
		}
	}
}
