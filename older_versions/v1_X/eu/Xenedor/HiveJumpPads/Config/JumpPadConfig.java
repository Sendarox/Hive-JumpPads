/*    */ package eu.Xenedor.HiveJumpPads.Config;
/*    */ 
/*    */ import java.io.File;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.configuration.file.FileConfigurationOptions;
/*    */ 
/*    */ public class JumpPadConfig
/*    */ {
/*    */   public static void loadJumpPadConfig()
/*    */   {
/*    */     try
/*    */     {
/* 13 */       File file = new File("plugins/HiveJumpPads/Configs/", "JumpPad-Config.yml");
/* 14 */       FileConfiguration cfg = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
/*    */       
/* 16 */       cfg.options().header("HiveJumpPads - JumpPad Config \n\n Anivable Sounds/Effects: \n\nSounds: http://dev.Bukkit.org/bukkit-plugins/hive-jump-pads/pages/Sounds \nEffects: http://dev.Bukkit.org/bukkit-plugins/hive-jump-pad/pages/Effects \n");
/*    */       
/*    */ 
/*    */ 
/* 20 */       cfg.addDefault("HiveJumpPads.JumpPads.Enable Jump Pads", Boolean.valueOf(true));
/* 21 */       cfg.addDefault("HiveJumpPads.JumpPads.3x3 Field", Boolean.valueOf(true));
/* 22 */       cfg.addDefault("HiveJumpPads.JumpPads.Enable Sounds", Boolean.valueOf(true));
/* 23 */       cfg.addDefault("HiveJumpPads.JumpPads.Enable Effects", Boolean.valueOf(true));
/* 24 */       cfg.addDefault("HiveJumpPads.JumpPads.PressurePlate ID", Integer.valueOf(70));
/* 25 */       cfg.addDefault("HiveJumpPads.JumpPads.Block ID", Integer.valueOf(152));
/* 26 */       cfg.addDefault("HiveJumpPads.JumpPads.Jump lenght", Integer.valueOf(35));
/* 27 */       cfg.addDefault("HiveJumpPads.JumpPads.Jump height", Integer.valueOf(3));
/* 28 */       cfg.addDefault("HiveJumpPads.JumpPads.Sound", String.valueOf("WITHER_SHOOT"));
/* 29 */       cfg.addDefault("HiveJumpPads.JumpPads.Effect", String.valueOf("SMOKE"));
/*    */       
/* 31 */       cfg.addDefault("HiveJumpPads.JumpPads.Disabled Worlds", new String[] {
/* 32 */         "World 1", 
/* 33 */         "World 2", 
/* 34 */         "World 3" });
/*    */       
/*    */ 
/*    */ 
/* 38 */       cfg.options().copyDefaults(true);
/* 39 */       cfg.options().copyHeader(true);
/*    */       
/* 41 */       cfg.save(file);
/*    */     }
/*    */     catch (Exception localException) {}
/*    */   }
/*    */ }


/* Location:              M:\BENUTZER\Downloads\HiveJumpPads_v1.9.3.jar!\eu\Xenedor\HiveJumpPads\Config\JumpPadConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */