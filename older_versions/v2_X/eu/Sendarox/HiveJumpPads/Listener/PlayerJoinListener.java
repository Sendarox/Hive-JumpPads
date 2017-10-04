/*    */ package eu.Sendarox.HiveJumpPads.Listener;
/*    */ 
/*    */ import eu.Sendarox.HiveJumpPads.HiveJumpPads;
/*    */ import eu.Sendarox.HiveJumpPads.Updater.UpdateChecker;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerJoinEvent;
/*    */ import org.bukkit.scheduler.BukkitScheduler;
/*    */ 
/*    */ public class PlayerJoinListener implements Listener
/*    */ {
/*    */   private HiveJumpPads hjp;
/*    */   
/*    */   public PlayerJoinListener(HiveJumpPads hjp)
/*    */   {
/* 17 */     this.hjp = hjp;
/*    */   }
/*    */   
/*    */   @org.bukkit.event.EventHandler
/*    */   private void onPlayerJoin(PlayerJoinEvent e)
/*    */   {
/* 23 */     final Player p = e.getPlayer();
/* 24 */     if (p.isOp()) {
/* 25 */       this.hjp.getServer().getScheduler().scheduleSyncDelayedTask(this.hjp, new Runnable() {
/*    */         protected UpdateChecker uc;
/*    */         
/*    */         public void run() {
/* 29 */           this.uc = new UpdateChecker(PlayerJoinListener.this.hjp, "http://dev.bukkit.org/bukkit-plugins/hive-jumppads/files.rss");
/* 30 */           this.uc.updateNeeded(p);
/*    */         }
/* 32 */       }, 80L);
/*    */     }
/*    */   }
/*    */ }


/* Location:              M:\BENUTZER\Downloads\Hive_JumpPads_v.2.16.jar!\eu\Sendarox\HiveJumpPads\Listener\PlayerJoinListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */