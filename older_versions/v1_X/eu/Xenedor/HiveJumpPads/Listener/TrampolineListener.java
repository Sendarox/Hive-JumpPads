/*     */ package eu.Xenedor.HiveJumpPads.Listener;
/*     */ 
/*     */ import eu.Xenedor.HiveJumpPads.HiveJumpPads;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class TrampolineListener implements org.bukkit.event.Listener
/*     */ {
/*     */   private HiveJumpPads pl;
/*     */   
/*     */   public TrampolineListener(HiveJumpPads pl)
/*     */   {
/*  24 */     this.pl = pl;
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   private void onTrampolineJump(PlayerMoveEvent e)
/*     */   {
/*  30 */     File file = new File("plugins/HiveJumpPads/Configs/", "Trampoline-Config.yml");
/*  31 */     FileConfiguration cfg = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
/*     */     
/*  33 */     Player p = e.getPlayer();
/*     */     
/*  35 */     String w = p.getWorld().getName();
/*  36 */     if (cfg.getBoolean("HiveJumpPads.Trampolines.3x3 Field") == Boolean.TRUE.booleanValue())
/*     */     {
/*  38 */       if (this.pl.getConfig().getStringList("HiveJumpPads.Disabled Worlds for both Pads").contains(w))
/*     */       {
/*  40 */         if (cfg.getStringList("HiveJumpPads.Trampolines.Disabled Worlds").contains(w))
/*     */         {
/*  42 */           if (cfg.getBoolean("HiveJumpPads.Trampolines.Enable Trampoline") == Boolean.FALSE.booleanValue()) {
/*  43 */             return;
/*     */           }
/*  45 */           return;
/*     */         }
/*  47 */         return;
/*     */       }
/*  49 */       String Config_Sound = cfg.getString("HiveJumpPads.Trampolines.Sound");
/*  50 */       String Config_Effect = cfg.getString("HiveJumpPads.Trampolines.Effect");
/*     */       
/*  52 */       boolean enable_Sounds = cfg.getBoolean("HiveJumpPads.Trampolines.Enable Sounds");
/*  53 */       boolean enable_Effects = cfg.getBoolean("HiveJumpPads.Trampolines.Enable Effects");
/*     */       
/*  55 */       int TrampolinePressurePlate = cfg.getInt("HiveJumpPads.Trampolines.PressurePlate ID");
/*  56 */       int TrampolinePlattformBlock = cfg.getInt("HiveJumpPads.Trampolines.Block ID");
/*     */       
/*  58 */       double Height_multiplier = cfg.getDouble("HiveJumpPads.Trampolines.Jump height");
/*     */       
/*  60 */       Location loc = p.getLocation();
/*     */       
/*  62 */       int pPlate = loc.getWorld().getBlockAt(loc).getRelative(0, 0, 0).getTypeId();
/*  63 */       if (pPlate == TrampolinePressurePlate)
/*     */       {
/*  65 */         int under = loc.getWorld().getBlockAt(loc).getRelative(0, -1, 0).getTypeId();
/*  66 */         int right = loc.getWorld().getBlockAt(loc).getRelative(0, -1, 1).getTypeId();
/*  67 */         int left = loc.getWorld().getBlockAt(loc).getRelative(1, -1, 0).getTypeId();
/*  68 */         int north = loc.getWorld().getBlockAt(loc).getRelative(-1, -1, 0).getTypeId();
/*  69 */         int south = loc.getWorld().getBlockAt(loc).getRelative(0, -1, -1).getTypeId();
/*     */         
/*  71 */         int north_west = loc.getWorld().getBlockAt(loc).getRelative(1, -1, 1).getTypeId();
/*  72 */         int north_east = loc.getWorld().getBlockAt(loc).getRelative(-1, -1, -1).getTypeId();
/*  73 */         int south_east = loc.getWorld().getBlockAt(loc).getRelative(1, -1, -1).getTypeId();
/*  74 */         int south_west = loc.getWorld().getBlockAt(loc).getRelative(-1, -1, 1).getTypeId();
/*  75 */         if ((under == TrampolinePlattformBlock) && 
/*  76 */           (right == TrampolinePlattformBlock) && 
/*  77 */           (left == TrampolinePlattformBlock) && 
/*  78 */           (north == TrampolinePlattformBlock) && 
/*  79 */           (south == TrampolinePlattformBlock) && 
/*  80 */           (north_west == TrampolinePlattformBlock) && 
/*  81 */           (north_east == TrampolinePlattformBlock) && 
/*  82 */           (south_east == TrampolinePlattformBlock) && 
/*  83 */           (south_west == TrampolinePlattformBlock))
/*     */         {
/*  85 */           e.setCancelled(true);
/*     */           
/*  87 */           double Height = Height_multiplier / 9.0D;
/*     */           
/*  89 */           p.setVelocity(p.getVelocity().setY(Height));
/*  90 */           if (enable_Sounds == Boolean.TRUE.booleanValue())
/*  91 */             p.playSound(p.getLocation(), Sound.valueOf(Config_Sound.toUpperCase()), 1.0F, 1.0F); else {
/*     */             return;
/*     */           }
/*     */           Player[] arrayOfPlayer;
/*  95 */           int j = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length; for (int i = 0; i < j; i++) { Player all = arrayOfPlayer[i];
/*  96 */             if (enable_Effects == Boolean.TRUE.booleanValue()) {
/*  97 */               all.playEffect(p.getLocation(), org.bukkit.Effect.valueOf(Config_Effect.toUpperCase()), 1);
/*     */             } else {
/*  99 */               return;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   private void onTrampolineJump1(PlayerMoveEvent e)
/*     */   {
/* 111 */     File file = new File("plugins/HiveJumpPads/Configs/", "Trampoline-Config.yml");
/* 112 */     FileConfiguration cfg = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
/*     */     
/* 114 */     Player p = e.getPlayer();
/*     */     
/* 116 */     String w = p.getWorld().getName();
/*     */     
/* 118 */     String Config_Sound = cfg.getString("HiveJumpPads.Trampolines.Sound");
/* 119 */     String Config_Effect = cfg.getString("HiveJumpPads.Trampolines.Effect");
/*     */     
/* 121 */     boolean enable_Sounds = cfg.getBoolean("HiveJumpPads.Trampolines.Enable Sounds");
/* 122 */     boolean enable_Effects = cfg.getBoolean("HiveJumpPads.Trampolines.Enable Effects");
/*     */     
/* 124 */     int TrampolinePressurePlate = cfg.getInt("HiveJumpPads.Trampolines.PressurePlate ID");
/* 125 */     int TrampolinePlattformBlock = cfg.getInt("HiveJumpPads.Trampolines.Block ID");
/*     */     
/* 127 */     double Height_multiplier = cfg.getDouble("HiveJumpPads.Trampolines.Jump height");
/*     */     
/* 129 */     Location loc = p.getLocation();
/* 130 */     if (cfg.getBoolean("HiveJumpPads.Trampolines.3x3 Field") == Boolean.FALSE.booleanValue())
/*     */     {
/* 132 */       if (this.pl.getConfig().getStringList("HiveJumpPads.Disabled Worlds for both Pads").contains(w))
/*     */       {
/* 134 */         if (cfg.getStringList("HiveJumpPads.Trampolines.Disabled Worlds").contains(w))
/*     */         {
/* 136 */           if (cfg.getBoolean("HiveJumpPads.Trampolines.Enable Trampoline") == Boolean.FALSE.booleanValue()) {
/* 137 */             return;
/*     */           }
/* 139 */           return;
/*     */         }
/* 141 */         return;
/*     */       }
/* 143 */       int pplate = loc.getWorld().getBlockAt(loc).getRelative(0, 0, 0).getTypeId();
/* 144 */       int under = loc.getWorld().getBlockAt(loc).getRelative(0, -1, 0).getTypeId();
/* 145 */       if ((pplate == TrampolinePressurePlate) && 
/* 146 */         (under == TrampolinePlattformBlock))
/*     */       {
/* 148 */         e.setCancelled(true);
/*     */         
/* 150 */         double Height = Height_multiplier / 9.0D;
/*     */         
/* 152 */         p.setVelocity(p.getVelocity().setY(Height));
/* 153 */         if (enable_Sounds == Boolean.TRUE.booleanValue())
/* 154 */           p.playSound(p.getLocation(), Sound.valueOf(Config_Sound.toUpperCase()), 1.0F, 1.0F);
/*     */         Player[] arrayOfPlayer;
/* 156 */         int j = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length; for (int i = 0; i < j; i++) { Player all = arrayOfPlayer[i];
/* 157 */           if (enable_Effects == Boolean.TRUE.booleanValue()) {
/* 158 */             all.playEffect(p.getLocation(), org.bukkit.Effect.valueOf(Config_Effect.toUpperCase()), 1);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              M:\BENUTZER\Downloads\HiveJumpPads_v1.9.3.jar!\eu\Xenedor\HiveJumpPads\Listener\TrampolineListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */