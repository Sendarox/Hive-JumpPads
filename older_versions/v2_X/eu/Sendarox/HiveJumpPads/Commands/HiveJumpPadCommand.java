/*    */ package eu.Sendarox.HiveJumpPads.Commands;
/*    */ 
/*    */ import eu.Sendarox.HiveJumpPads.HiveJumpPads;
/*    */ import eu.Sendarox.HiveJumpPads.TransanslateAlternateColors;
/*    */ import eu.Sendarox.HiveJumpPads.Variables.Strings;
/*    */ import java.io.File;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class HiveJumpPadCommand implements CommandExecutor
/*    */ {
/*    */   private HiveJumpPads hjp;
/*    */   
/*    */   public HiveJumpPadCommand(HiveJumpPads hjp)
/*    */   {
/* 20 */     this.hjp = hjp;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean onCommand(CommandSender sender, Command cmd, String strg, String[] args)
/*    */   {
/* 26 */     File file = new File("plugins/Hive JumpPads/", "messages.yml");
/* 27 */     FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
/*    */     
/* 29 */     if ((sender instanceof Player)) {
/* 30 */       Player p = (Player)sender;
/* 31 */       if (args.length == 0) {
/* 32 */         p.sendMessage(Strings.HJP_CHAT + "Version: §6" + Strings.HJP_VERSION);
/* 33 */         p.sendMessage(Strings.HJP_CHAT + "Developed by §6Sendarox");
/* 34 */         p.sendMessage(" ");
/* 35 */         p.sendMessage("§7Use §6/hjp help §7for the Commands.");
/* 36 */         return true; }
/* 37 */       if (args.length == 1) {
/* 38 */         if (args[0].equalsIgnoreCase("help")) {
/* 39 */           p.sendMessage("§7----------[ §6HiveJumpPads §7]----------");
/* 40 */           p.sendMessage(" ");
/* 41 */           p.sendMessage("§6/hjp version§7 - Shows the actual version");
/* 42 */           p.sendMessage("§6/hjp reload§7 - Reload Hive JumpPads");
/* 43 */           return true; }
/* 44 */         if (args[0].equalsIgnoreCase("version")) {
/* 45 */           p.sendMessage(Strings.HJP_CHAT + "Version: §6" + Strings.HJP_VERSION);
/* 46 */           return true; }
/* 47 */         if (args[0].equalsIgnoreCase("reload")) {
/* 48 */           if (p.hasPermission("HiveJumpPads.command.Reload")) {
/* 49 */             p.sendMessage(Strings.HJP_CHAT + "Reloading Hive JumpPads...");
/* 50 */             this.hjp.onDisable();
/* 51 */             this.hjp.onEnable();
/* 52 */             p.sendMessage(Strings.HJP_CHAT + "Reload complete!");
/* 53 */             return true;
/*    */           }
/* 55 */           p.sendMessage(TransanslateAlternateColors.colMsg(cfg.getString("HiveJumpPads.Messages.NoPermissions")));
/*    */         } else {
/* 57 */           if (args[0].equalsIgnoreCase("info")) {
/* 58 */             p.sendMessage(Strings.HJP_CHAT + "Version: §6" + Strings.HJP_VERSION);
/* 59 */             p.sendMessage(Strings.HJP_CHAT + "Author: §6Sendarox");
/* 60 */             return true;
/*    */           }
/* 62 */           p.sendMessage(TransanslateAlternateColors.colMsg(cfg.getString("HiveJumpPads.Messages.CommandSynatax").replace("%cmd%", args[0])));
/* 63 */           return true;
/*    */         }
/*    */       } else {
/* 66 */         p.sendMessage(TransanslateAlternateColors.colMsg(cfg.getString("HiveJumpPads.Messages.CommandSynatax").replace("%cmd%", args[0])));
/* 67 */         return true;
/*    */       }
/*    */     }
/* 70 */     return true;
/*    */   }
/*    */ }


/* Location:              M:\BENUTZER\Downloads\Hive_JumpPads_v.2.16.jar!\eu\Sendarox\HiveJumpPads\Commands\HiveJumpPadCommand.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */