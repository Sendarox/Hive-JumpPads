/*     */ package eu.Sendarox.HiveJumpPads.Listener;
/*     */ 
/*     */ import eu.Sendarox.HiveJumpPads.HiveJumpPads;
/*     */ import eu.Sendarox.HiveJumpPads.TransanslateAlternateColors;
/*     */ import java.io.File;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class JumpPadListener implements org.bukkit.event.Listener
/*     */ {
/*     */   private HiveJumpPads hjp;
/*     */   
/*     */   public JumpPadListener(HiveJumpPads hjp)
/*     */   {
/*  24 */     this.hjp = hjp;
/*     */   }
/*     */   
/*     */ 
/*     */   @org.bukkit.event.EventHandler
/*     */   private void onJump(PlayerMoveEvent e)
/*     */   {
/*  31 */     File file = new File("plugins/Hive JumpPads/JumpPads/", "JumpPad-Config.yml");
/*  32 */     FileConfiguration cfg = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
/*     */     
/*  34 */     File f = new File("plugins/Hive JumpPads/", "config.yml");
/*  35 */     FileConfiguration cfg1 = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(f);
/*  36 */     this.hjp.getDataFolder();
/*  37 */     Player p = e.getPlayer();
/*     */     
/*  39 */     World location = p.getWorld();
/*  40 */     Location playerLoc = p.getLocation();
/*  41 */     if (cfg.getBoolean("HiveJumpPads.JumpPad.Enable JumpPads") == Boolean.FALSE.booleanValue()) {
/*  42 */       return;
/*     */     }
/*  44 */     if (cfg1.getStringList("HiveJumpPads.Disabed_Worlds_for_both_JumpPads").contains(location.getName())) {
/*  45 */       return;
/*     */     }
/*  47 */     if (cfg.getStringList("HiveJumpPads.JumpPads.DisabledWorlds").contains(location.getName())) {
/*  48 */       return;
/*     */     }
/*     */     
/*  51 */     if (cfg.getBoolean("HiveJumpPads.JumpPad.3x3 Field") == Boolean.FALSE.booleanValue()) {
/*  52 */       int PressurePlate_ID = playerLoc.getWorld().getBlockAt(playerLoc).getRelative(0, 0, 0).getTypeId();
/*  53 */       int ID = playerLoc.getWorld().getBlockAt(playerLoc).getRelative(0, -1, 0).getTypeId();
/*  54 */       if ((p.hasPermission("HiveJumpPads.use.JumpPad")) && 
/*  55 */         (PressurePlate_ID == cfg.getInt("HiveJumpPads.JumpPad.PressurePlateID")) && (ID == cfg.getInt("HiveJumpPads.JumpPad.BlockID"))) {
/*  56 */         Long time = Long.valueOf(System.currentTimeMillis());
/*  57 */         if (HiveJumpPads.cooldown.containsKey(p.getName())) {
/*  58 */           Long lastUsage = (Long)HiveJumpPads.cooldown.get(p.getName());
/*  59 */           if (lastUsage.longValue() + 1000L > time.longValue()) {
/*  60 */             return;
/*     */           }
/*     */         }
/*  63 */         double Height = cfg.getDouble("HiveJumpPads.Lenght & Height.JumpHeight");
/*  64 */         double Width = cfg.getDouble("HiveJumpPads.Lenght & Height.JumpLenght");
/*     */         
/*  66 */         double BlockHeight = Height / 19.5D;
/*  67 */         double BlockWidth = Width / 5.0D;
/*  68 */         p.setVelocity(p.getLocation().getDirection().setY(BlockHeight).multiply(BlockWidth));
/*     */         
/*  70 */         if (cfg.getBoolean("HiveJumpPads.Sound & Effects.Enable Sounds") == Boolean.TRUE.booleanValue()) {
/*  71 */           p.playSound(p.getLocation(), Sound.valueOf(cfg.getString("HiveJumpPads.Sound & Effects.Sound").toUpperCase()), 100.0F, 100.0F);
/*     */         }
/*     */         
/*  74 */         if (cfg.getBoolean("HiveJumpPads.Sound & Effects.Enable Effects") != Boolean.FALSE.booleanValue()) {
/*  75 */           for (Player all : Bukkit.getOnlinePlayers()) {
/*  76 */             all.playEffect(p.getLocation(), org.bukkit.Effect.valueOf(cfg.getString("HiveJumpPads.Sound & Effects.Effect").toUpperCase()), 4);
/*     */           }
/*     */         }
/*     */         
/*  80 */         if (cfg.getBoolean("HiveJumpPads.Sound & Effects.Enable message") == Boolean.TRUE.booleanValue()) {
/*  81 */           p.sendMessage(TransanslateAlternateColors.colMsg(cfg.getString("HiveJumpPads.Sound & Effects.message")));
/*     */         }
/*     */         
/*     */ 
/*  85 */         HiveJumpPads.Jumped.put(p.getName(), Boolean.valueOf(true));
/*  86 */         HiveJumpPads.cooldown.put(p.getName(), time);
/*     */       }
/*     */     }
/*  89 */     else if ((cfg.getBoolean("HiveJumpPads.JumpPad.3x3 Field") == Boolean.TRUE.booleanValue()) && 
/*  90 */       (p.hasPermission("HiveJumpPads.use.JumpPad"))) {
/*  91 */       int ID = playerLoc.getWorld().getBlockAt(playerLoc).getRelative(0, 0, 0).getTypeId();
/*  92 */       int ID2 = playerLoc.getWorld().getBlockAt(playerLoc).getRelative(0, -1, 0).getTypeId();
/*  93 */       int ID3 = playerLoc.getWorld().getBlockAt(playerLoc).getRelative(1, -1, 0).getTypeId();
/*  94 */       int ID4 = playerLoc.getWorld().getBlockAt(playerLoc).getRelative(-1, -1, 0).getTypeId();
/*  95 */       int ID5 = playerLoc.getWorld().getBlockAt(playerLoc).getRelative(0, -1, 1).getTypeId();
/*  96 */       int ID6 = playerLoc.getWorld().getBlockAt(playerLoc).getRelative(0, -1, -1).getTypeId();
/*  97 */       int ID7 = playerLoc.getWorld().getBlockAt(playerLoc).getRelative(-1, -1, -1).getTypeId();
/*  98 */       int ID8 = playerLoc.getWorld().getBlockAt(playerLoc).getRelative(1, -1, 1).getTypeId();
/*  99 */       if ((ID == cfg.getInt("HiveJumpPads.JumpPad.PressurePlateID")) && 
/* 100 */         (ID2 == cfg.getInt("HiveJumpPads.JumpPad.BlockID")) && 
/* 101 */         (ID3 == cfg.getInt("HiveJumpPads.JumpPad.BlockID")) && 
/* 102 */         (ID4 == cfg.getInt("HiveJumpPads.JumpPad.BlockID")) && 
/* 103 */         (ID5 == cfg.getInt("HiveJumpPads.JumpPad.BlockID")) && 
/* 104 */         (ID6 == cfg.getInt("HiveJumpPads.JumpPad.BlockID")) && 
/* 105 */         (ID7 == cfg.getInt("HiveJumpPads.JumpPad.BlockID")) && 
/* 106 */         (ID8 == cfg.getInt("HiveJumpPads.JumpPad.BlockID"))) {
/* 107 */         Long time = Long.valueOf(System.currentTimeMillis());
/* 108 */         if (HiveJumpPads.cooldown.containsKey(p.getName())) {
/* 109 */           Long lastUsage = (Long)HiveJumpPads.cooldown.get(p.getName());
/* 110 */           if (lastUsage.longValue() + 1000L > time.longValue()) {
/* 111 */             return;
/*     */           }
/*     */         }
/* 114 */         double Height = cfg.getDouble("HiveJumpPads.Lenght & Height.JumpHeight");
/* 115 */         double Width = cfg.getDouble("HiveJumpPads.Lenght & Height.JumpLenght");
/*     */         
/* 117 */         double BlockHeight = Height / 19.5D;
/* 118 */         double BlockWidth = Width / 5.0D;
/* 119 */         p.setVelocity(p.getLocation().getDirection().setY(BlockHeight).multiply(BlockWidth));
/*     */         
/* 121 */         if (cfg.getBoolean("HiveJumpPads.Sound & Effects.Enable Sounds") == Boolean.TRUE.booleanValue()) {
/* 122 */           p.playSound(p.getLocation(), Sound.valueOf(cfg.getString("HiveJumpPads.Sound & Effects.Sound").toUpperCase()), 100.0F, 100.0F);
/*     */         }
/*     */         
/* 125 */         if (cfg.getBoolean("HiveJumpPads.Sound & Effects.Enable Effects") == Boolean.TRUE.booleanValue()) {
/* 126 */           for (Player all : Bukkit.getOnlinePlayers()) {
/* 127 */             all.playEffect(p.getLocation(), org.bukkit.Effect.valueOf(cfg.getString("HiveJumpPads.Sound & Effects.Effect").toUpperCase()), 4);
/*     */           }
/*     */         }
/*     */         
/* 131 */         if (cfg.getBoolean("HiveJumpPads.Sound & Effects.Enable message") == Boolean.TRUE.booleanValue()) {
/* 132 */           p.sendMessage(TransanslateAlternateColors.colMsg(cfg.getString("HiveJumpPads.Sound & Effects.message")));
/*     */         }
/*     */         
/* 135 */         HiveJumpPads.Jumped.put(p.getName(), Boolean.valueOf(true));
/* 136 */         HiveJumpPads.cooldown.put(p.getName(), time);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              M:\BENUTZER\Downloads\Hive_JumpPads_v.2.16.jar!\eu\Sendarox\HiveJumpPads\Listener\JumpPadListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */