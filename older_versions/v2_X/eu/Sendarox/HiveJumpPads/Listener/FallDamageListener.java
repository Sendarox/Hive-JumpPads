/*    */ package eu.Sendarox.HiveJumpPads.Listener;
/*    */ 
/*    */ import eu.Sendarox.HiveJumpPads.HiveJumpPads;
/*    */ import java.io.File;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.entity.EntityDamageEvent;
/*    */ import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
/*    */ 
/*    */ public class FallDamageListener implements Listener
/*    */ {
/*    */   private HiveJumpPads hjp;
/*    */   
/*    */   public FallDamageListener(HiveJumpPads hjp)
/*    */   {
/* 20 */     this.hjp = hjp;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onFall(EntityDamageEvent e) {
/* 25 */     this.hjp.getDataFolder();
/* 26 */     File f = new File("plugins/Hive JumpPads/", "config.yml");
/* 27 */     FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
/* 28 */     if ((cfg.getBoolean("HiveJumpPads.Disable_GlobalFallDamage") == Boolean.TRUE.booleanValue()) && 
/* 29 */       (e.getCause() == EntityDamageEvent.DamageCause.FALL)) {
/* 30 */       Entity ee = e.getEntity();
/* 31 */       if ((ee instanceof Player)) {
/* 32 */         e.setCancelled(true);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              M:\BENUTZER\Downloads\Hive_JumpPads_v.2.16.jar!\eu\Sendarox\HiveJumpPads\Listener\FallDamageListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */