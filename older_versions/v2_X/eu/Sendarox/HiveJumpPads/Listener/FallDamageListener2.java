/*    */ package eu.Sendarox.HiveJumpPads.Listener;
/*    */ 
/*    */ import eu.Sendarox.HiveJumpPads.HiveJumpPads;
/*    */ import java.io.File;
/*    */ import java.util.HashMap;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.entity.EntityDamageEvent;
/*    */ import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
/*    */ 
/*    */ public class FallDamageListener2
/*    */   implements Listener
/*    */ {
/*    */   @EventHandler
/*    */   public void onFall(EntityDamageEvent e)
/*    */   {
/* 21 */     File f = new File("plugins/Hive JumpPads/", "config.yml");
/* 22 */     FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
/*    */     
/*    */ 
/* 25 */     if ((cfg.getBoolean("HiveJumpPads.Disable_JumpPadFallDamage") == Boolean.TRUE.booleanValue()) && 
/* 26 */       (e.getCause() == EntityDamageEvent.DamageCause.FALL)) {
/* 27 */       Entity ent = e.getEntity();
/* 28 */       if ((ent instanceof Player)) {
/* 29 */         Player p = (Player)ent;
/* 30 */         if (HiveJumpPads.Jumped.containsKey(p.getName())) {
/* 31 */           if (((Boolean)HiveJumpPads.Jumped.get(p.getName())).booleanValue()) {
/* 32 */             HiveJumpPads.Jumped.put(p.getName(), Boolean.valueOf(false));
/* 33 */             e.setCancelled(true);
/*    */           }
/*    */           else {}
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              M:\BENUTZER\Downloads\Hive_JumpPads_v.2.16.jar!\eu\Sendarox\HiveJumpPads\Listener\FallDamageListener2.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */