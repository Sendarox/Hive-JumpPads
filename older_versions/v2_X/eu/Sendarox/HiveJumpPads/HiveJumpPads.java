/*    */ package eu.Sendarox.HiveJumpPads;
/*    */ 
/*    */ import eu.Sendarox.HiveJumpPads.Commands.HiveJumpPadCommand;
/*    */ import eu.Sendarox.HiveJumpPads.Listener.FallDamageListener;
/*    */ import eu.Sendarox.HiveJumpPads.Listener.FallDamageListener2;
/*    */ import eu.Sendarox.HiveJumpPads.Listener.JumpPadListener;
/*    */ import eu.Sendarox.HiveJumpPads.Listener.PlayerJoinListener;
/*    */ import eu.Sendarox.HiveJumpPads.Updater.UpdateChecker;
/*    */ import eu.Sendarox.HiveJumpPads.Variables.Strings;
/*    */ import java.io.File;
/*    */ import java.io.PrintStream;
/*    */ import java.util.HashMap;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.command.PluginCommand;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.plugin.PluginDescriptionFile;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ 
/*    */ public class HiveJumpPads extends org.bukkit.plugin.java.JavaPlugin
/*    */ {
/* 22 */   public final HiveJumpPadCommand hjpc = new HiveJumpPadCommand(this);
/* 23 */   public static HashMap<String, Boolean> Jumped = new HashMap();
/* 24 */   public static HashMap<String, Long> cooldown = new HashMap();
/*    */   
/*    */   protected UpdateChecker uc;
/* 27 */   File f = new File("plugins/Hive JumpPads", "config.yml");
/* 28 */   FileConfiguration cfg = YamlConfiguration.loadConfiguration(this.f);
/*    */   
/*    */   public void onEnable()
/*    */   {
/* 32 */     registerConfiguration();
/* 33 */     registerListener();
/* 34 */     registerCommands();
/* 35 */     String version = getDescription().getVersion();
/*    */     
/* 37 */     System.out.println(Strings.HJP_CONSOLE + "Hive JumpPads v" + version + " developed by Xenedor!");
/* 38 */     System.out.println(Strings.HJP_CONSOLE + "Hive JumpPads Successful loaded!");
/*    */   }
/*    */   
/*    */   public void onDisable()
/*    */   {
/* 43 */     String version = getDescription().getVersion();
/* 44 */     System.out.println(Strings.HJP_CONSOLE + "Hive JumpPads v" + version + " disabled!");
/*    */   }
/*    */   
/*    */   private void registerListener() {
/* 48 */     File f = new File("plugins/Hive JumpPads/", "config.yml");
/* 49 */     FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
/*    */     
/* 51 */     PluginManager pm = getServer().getPluginManager();
/* 52 */     pm.registerEvents(new FallDamageListener(this), this);
/* 53 */     pm.registerEvents(new JumpPadListener(this), this);
/* 54 */     pm.registerEvents(new eu.Sendarox.HiveJumpPads.Listener.TrampolineListener(this), this);
/* 55 */     pm.registerEvents(new FallDamageListener2(), this);
/* 56 */     if (cfg.getBoolean("HiveJumpPads.Use_Updater")) {
/* 57 */       pm.registerEvents(new PlayerJoinListener(this), this);
/*    */     }
/*    */   }
/*    */   
/*    */   private void registerCommands() {
/* 62 */     getCommand("HiveJumpPads").setExecutor(this.hjpc);
/*    */   }
/*    */   
/*    */   private void registerConfiguration() {
/* 66 */     Configuration.JumpPadConfiguration();
/* 67 */     Configuration.Konfiguration();
/* 68 */     Configuration.MessagesConfiguration();
/* 69 */     Configuration.TrampolineConfiguration();
/*    */   }
/*    */ }


/* Location:              M:\BENUTZER\Downloads\Hive_JumpPads_v.2.16.jar!\eu\Sendarox\HiveJumpPads\HiveJumpPads.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */