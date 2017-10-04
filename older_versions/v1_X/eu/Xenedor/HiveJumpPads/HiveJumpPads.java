/*    */ package eu.Xenedor.HiveJumpPads;
/*    */ 
/*    */ import eu.Xenedor.HiveJumpPads.Commands.HiveJumpPadCommand;
/*    */ import eu.Xenedor.HiveJumpPads.Config.JumpPadConfig;
/*    */ import eu.Xenedor.HiveJumpPads.Config.MainConfig;
/*    */ import eu.Xenedor.HiveJumpPads.Config.MessagesConfig;
/*    */ import eu.Xenedor.HiveJumpPads.Config.TrampolineConfig;
/*    */ import eu.Xenedor.HiveJumpPads.Listener.JumpPadListener;
/*    */ import eu.Xenedor.HiveJumpPads.Listener.TrampolineListener;
/*    */ import java.io.PrintStream;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.plugin.PluginDescriptionFile;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ 
/*    */ public class HiveJumpPads extends JavaPlugin
/*    */ {
/*    */   public void onEnable()
/*    */   {
/* 22 */     PluginDescriptionFile pdfile = getDescription();
/*    */     
/* 24 */     System.out.println("[" + pdfile.getName() + "] HiveJumpPads v" + pdfile.getVersion() + " is now Enabled.");
/* 25 */     System.out.println("[" + pdfile.getName() + "] Developed by Xenedor");
/*    */     
/* 27 */     loadConfigurations();
/* 28 */     registerEvents();
/*    */     
/* 30 */     super.onEnable();
/*    */   }
/*    */   
/*    */   public void onDisable()
/*    */   {
/* 35 */     PluginDescriptionFile pdfile = getDescription();
/*    */     
/* 37 */     System.out.println("[" + pdfile.getName() + "] HiveJumpPads v" + pdfile.getVersion() + " is now Disabled.");
/* 38 */     System.out.println("[" + pdfile.getName() + "] Developed by Xenedor");
/*    */     
/* 40 */     super.onDisable();
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
/*    */   {
/* 45 */     if (command.getName().equalsIgnoreCase("HiveJumpPads")) {
/* 46 */       return new HiveJumpPadCommand(command, args, this).onExecute(sender, command, label, args);
/*    */     }
/* 48 */     return true;
/*    */   }
/*    */   
/*    */   private void registerEvents()
/*    */   {
/* 53 */     PluginManager pm = getServer().getPluginManager();
/*    */     
/* 55 */     pm.registerEvents(new JumpPadListener(this), this);
/* 56 */     pm.registerEvents(new TrampolineListener(this), this);
/* 57 */     pm.registerEvents(new eu.Xenedor.HiveJumpPads.Listener.FallDamageListener(this), this);
/*    */   }
/*    */   
/*    */   private void loadConfigurations()
/*    */   {
/*    */     try
/*    */     {
/* 64 */       TrampolineConfig.loadTrampolineConfig();
/* 65 */       JumpPadConfig.loadJumpPadConfig();
/* 66 */       MessagesConfig.loadMessagesFile();
/* 67 */       MainConfig.LoadDefaultConfiguration();
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 71 */       System.err.println("[HiveJumpPads] A Error -->");
/* 72 */       System.err.println("[HiveJumpPads] Error Message: " + e.getMessage());
/*    */     }
/*    */   }
/*    */ }


/* Location:              M:\BENUTZER\Downloads\HiveJumpPads_v1.9.3.jar!\eu\Xenedor\HiveJumpPads\HiveJumpPads.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */