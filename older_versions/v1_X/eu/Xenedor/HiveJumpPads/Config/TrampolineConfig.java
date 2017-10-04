/*    */ package eu.Xenedor.HiveJumpPads.Config;
/*    */ 
/*    */ import java.io.File;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.configuration.file.FileConfigurationOptions;
/*    */ 
/*    */ public class TrampolineConfig
/*    */ {
/*    */   public static void loadTrampolineConfig()
/*    */   {
/*    */     try
/*    */     {
/* 13 */       File file = new File("plugins/HiveJumpPads/Configs/", "Trampoline-Config.yml");
/* 14 */       FileConfiguration cfg = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
/*    */       
/* 16 */       cfg.options().header("HiveJumpPads - Trampoline Config \n\n Anivable Sounds/Effects: \n\nSounds: http://dev.Bukkit.org/bukkit-plugins/hive-jump-pads/pages/Sounds \nEffects: http://dev.Bukkit.org/bukkit-plugins/hive-jump-pad/pages/Effects \n");
/*    */       
/*    */ 
/*    */ 
/* 20 */       cfg.addDefault("HiveJumpPads.Trampolines.Enable Trampoline", Boolean.valueOf(false));
/* 21 */       cfg.addDefault("HiveJumpPads.Trampolines.3x3 Field", Boolean.valueOf(true));
/* 22 */       cfg.addDefault("HiveJumpPads.Trampolines.Enable Sounds", Boolean.valueOf(false));
/* 23 */       cfg.addDefault("HiveJumpPads.Trampolines.Enable Effects", Boolean.valueOf(false));
/* 24 */       cfg.addDefault("HiveJumpPads.Trampolines.PressurePlate ID", Integer.valueOf(147));
/* 25 */       cfg.addDefault("HiveJumpPads.Trampolines.Block ID", Integer.valueOf(152));
/* 26 */       cfg.addDefault("HiveJumpPads.Trampolines.Jump height", Integer.valueOf(15));
/* 27 */       cfg.addDefault("HiveJumpPads.Trampolines.Sound", String.valueOf("none"));
/* 28 */       cfg.addDefault("HiveJumpPads.Trampolines.Effect", String.valueOf("none"));
/*    */       
/* 30 */       cfg.addDefault("HiveJumpPads.Trampolines.Disabled Worlds", new String[] {
/* 31 */         "World 1", 
/* 32 */         "World 2", 
/* 33 */         "World 3" });
/*    */       
/*    */ 
/*    */ 
/* 37 */       cfg.options().copyDefaults(true);
/* 38 */       cfg.options().copyHeader(true);
/*    */       
/* 40 */       cfg.save(file);
/*    */     }
/*    */     catch (Exception localException) {}
/*    */   }
/*    */ }


/* Location:              M:\BENUTZER\Downloads\HiveJumpPads_v1.9.3.jar!\eu\Xenedor\HiveJumpPads\Config\TrampolineConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */