/*    */ package eu.Xenedor.HiveJumpPads.Config;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.configuration.file.FileConfigurationOptions;
/*    */ 
/*    */ public class MainConfig
/*    */ {
/*    */   public static void LoadDefaultConfiguration()
/*    */   {
/* 12 */     File file = new File("plugins/HiveJumpPads/", "Config.yml");
/* 13 */     FileConfiguration cfg = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
/*    */     
/* 15 */     cfg.options().header("HiveJumpPads Main Configuration | Deverloped by Xenedor \n");
/*    */     
/* 17 */     cfg.addDefault("HiveJumpPads.Disable Fall Damage", Boolean.valueOf(false));
/* 18 */     cfg.addDefault("HiveJumpPads.Disabled Worlds for both Pads", new String[] {
/* 19 */       "World 1", 
/* 20 */       "World 2", 
/* 21 */       "World 3" });
/*    */     
/*    */ 
/* 24 */     cfg.options().copyDefaults(true);
/* 25 */     cfg.options().copyHeader(true);
/*    */     try
/*    */     {
/* 28 */       cfg.save(file);
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 32 */       System.err.println("[HiveJumpPads] A Error -->");
/* 33 */       System.err.println("[HiveJumpPads] Error Message: " + e.getMessage());
/*    */     }
/*    */   }
/*    */ }


/* Location:              M:\BENUTZER\Downloads\HiveJumpPads_v1.9.3.jar!\eu\Xenedor\HiveJumpPads\Config\MainConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */