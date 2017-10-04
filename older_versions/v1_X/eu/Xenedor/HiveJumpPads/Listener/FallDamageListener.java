/*    */ package eu.Xenedor.HiveJumpPads.Listener;
/*    */ 
/*    */ import eu.Xenedor.HiveJumpPads.HiveJumpPads;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.entity.EntityDamageEvent;
/*    */ import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
/*    */ 
/*    */ public class FallDamageListener implements Listener
/*    */ {
/*    */   private HiveJumpPads pl;
/*    */   
/*    */   public FallDamageListener(HiveJumpPads pl)
/*    */   {
/* 15 */     this.pl = pl;
/*    */   }
/*    */   
/*    */   @org.bukkit.event.EventHandler
/*    */   public void onDamage(EntityDamageEvent e)
/*    */   {
/* 21 */     if ((this.pl.getConfig().getBoolean("HiveJumpPads.Disable Fall Damage") == Boolean.TRUE.booleanValue()) && 
/* 22 */       (e.getCause() == EntityDamageEvent.DamageCause.FALL)) {
/* 23 */       e.setCancelled(true);
/*    */     }
/*    */   }
/*    */ }


/* Location:              M:\BENUTZER\Downloads\HiveJumpPads_v1.9.3.jar!\eu\Xenedor\HiveJumpPads\Listener\FallDamageListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */