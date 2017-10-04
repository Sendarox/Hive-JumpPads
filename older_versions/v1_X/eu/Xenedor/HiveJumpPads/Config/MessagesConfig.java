/*    */ package eu.Xenedor.HiveJumpPads.Config;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.configuration.file.FileConfigurationOptions;
/*    */ 
/*    */ public class MessagesConfig
/*    */ {
/*    */   public static void loadMessagesFile()
/*    */   {
/* 12 */     File file = new File("plugins/HiveJumpPads/", "Messages.yml");
/* 13 */     FileConfiguration cfg = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
/* 14 */     if (file.exists())
/*    */     {
/*    */       try
/*    */       {
/* 18 */         cfg.options().header("HiveJumpPads - Messages.yml | Change the Messages \n");
/*    */         
/* 20 */         cfg.addDefault("HiveJumpPads.Messages.No Permissions", "&cYou don´t have the Permissions to execute this Command.");
/* 21 */         cfg.addDefault("HiveJumpPads.Messages.Usage", "&7Please use &6'/HiveJumpPads <args>'");
/* 22 */         cfg.addDefault("HiveJumpPads.Messages.Reload", "&7Reload ...");
/* 23 */         cfg.addDefault("HiveJumpPads.Messages.Reload Complete", "&7Reload Complete");
/*    */         
/* 25 */         cfg.options().copyDefaults(true);
/* 26 */         cfg.options().copyHeader(true);
/*    */         
/* 28 */         cfg.save(file);
/*    */       }
/*    */       catch (Exception e)
/*    */       {
/* 32 */         System.err.println("[HiveJumpPads] A Error -->");
/* 33 */         System.err.println("[HiveJumpPads] Error Message: " + e.getMessage());
/*    */       }
/*    */     }
/*    */     else
/*    */     {
/* 38 */       System.out.println("[HiveJumpPads] 'Messages.yml' was not found --> Creating a new one!");
/*    */       
/* 40 */       cfg.options().header("HiveJumpPads - Messages.yml | Change the Messages \n");
/*    */       
/* 42 */       cfg.addDefault("HiveJumpPads.Messages.No Permissions", "&cYou don´t have the Permissions to execute this Command.");
/* 43 */       cfg.addDefault("HiveJumpPads.Messages.Usage", "&7Please use &6/HiveJumpPads <args>");
/* 44 */       cfg.addDefault("HiveJumpPads.Messages.Reload", "&7Reload ...");
/* 45 */       cfg.addDefault("HiveJumpPads.Messages.Reload Complete", "&7Reload Complete");
/*    */       
/* 47 */       cfg.options().copyDefaults(true);
/* 48 */       cfg.options().copyHeader(true);
/*    */       try
/*    */       {
/* 51 */         cfg.save(file);
/*    */       }
/*    */       catch (IOException e)
/*    */       {
/* 55 */         e.printStackTrace();
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              M:\BENUTZER\Downloads\HiveJumpPads_v1.9.3.jar!\eu\Xenedor\HiveJumpPads\Config\MessagesConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */