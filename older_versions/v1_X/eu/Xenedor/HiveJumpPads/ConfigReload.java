/*    */ package eu.Xenedor.HiveJumpPads;
/*    */ 
/*    */ import java.io.File;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ 
/*    */ public class ConfigReload
/*    */ {
/*    */   private HiveJumpPads pl;
/*    */   
/*    */   public ConfigReload(HiveJumpPads pl)
/*    */   {
/* 13 */     this.pl = pl;
/*    */   }
/*    */   
/* 16 */   private File Config = new File(this.pl.getDataFolder(), "Config.yml");
/* 17 */   private File Messages = new File(this.pl.getDataFolder(), "Messages.yml");
/* 18 */   private File JumpPad = new File(this.pl.getDataFolder() + "/Configs/", "JumpPad-Config.yml");
/* 19 */   private File Trampoline = new File(this.pl.getDataFolder() + "/Configs/", "Trampoline-Config.yml");
/*    */   
/*    */   public void ConfigReload()
/*    */   {
/*    */     try
/*    */     {
/* 25 */       FileConfiguration cfg = YamlConfiguration.loadConfiguration(this.Config);
/* 26 */       cfg.load(this.Config);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 30 */       ReloadExceptionMessages();
/*    */     }
/*    */   }
/*    */   
/*    */   public void MessagesReload()
/*    */   {
/*    */     try
/*    */     {
/* 38 */       FileConfiguration cfg = YamlConfiguration.loadConfiguration(this.Messages);
/* 39 */       cfg.load(this.Messages);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 43 */       ReloadExceptionMessages();
/*    */     }
/*    */   }
/*    */   
/*    */   public void JumpPadReload()
/*    */   {
/*    */     try
/*    */     {
/* 51 */       FileConfiguration cfg = YamlConfiguration.loadConfiguration(this.JumpPad);
/* 52 */       cfg.load(this.JumpPad);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 56 */       ReloadExceptionMessages();
/*    */     }
/*    */   }
/*    */   
/*    */   public void TrampolineReload()
/*    */   {
/*    */     try
/*    */     {
/* 64 */       FileConfiguration cfg = YamlConfiguration.loadConfiguration(this.Trampoline);
/* 65 */       cfg.load(this.Trampoline);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 69 */       ReloadExceptionMessages();
/*    */     }
/*    */   }
/*    */   
/*    */   public void ReloadAll()
/*    */   {
/*    */     try
/*    */     {
/* 77 */       FileConfiguration cfg_c = YamlConfiguration.loadConfiguration(this.Config);
/* 78 */       FileConfiguration cfg_m = YamlConfiguration.loadConfiguration(this.Messages);
/* 79 */       FileConfiguration cfg_j = YamlConfiguration.loadConfiguration(this.JumpPad);
/* 80 */       FileConfiguration cfg_t = YamlConfiguration.loadConfiguration(this.Trampoline);
/*    */       
/* 82 */       cfg_c.load(this.Config);
/* 83 */       cfg_m.load(this.Messages);
/* 84 */       cfg_j.load(this.JumpPad);
/* 85 */       cfg_t.load(this.Trampoline);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 89 */       ReloadExceptionMessages();
/*    */     }
/*    */   }
/*    */   
/*    */   private void ReloadExceptionMessages()
/*    */   {
/* 95 */     System.out.println("[HiveJumpPads] Config Error -->");
/* 96 */     System.out.println("[HiveJumpPads] Error Message: Your Computer disk is full");
/*    */   }
/*    */ }


/* Location:              M:\BENUTZER\Downloads\HiveJumpPads_v1.9.3.jar!\eu\Xenedor\HiveJumpPads\ConfigReload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */