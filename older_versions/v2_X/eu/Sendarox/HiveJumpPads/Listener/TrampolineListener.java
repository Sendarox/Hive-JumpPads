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
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class TrampolineListener implements org.bukkit.event.Listener
/*     */ {
/*     */   private HiveJumpPads hjp;
/*     */   
/*     */   public TrampolineListener(HiveJumpPads hjp)
/*     */   {
/*  25 */     this.hjp = hjp;
/*     */   }
/*     */   
/*     */   @org.bukkit.event.EventHandler
/*     */   private void onTrampolineJump(PlayerMoveEvent e)
/*     */   {
/*  31 */     File file = new File("plugins/Hive JumpPads/JumpPads/", "Trampoline-Config.yml");
/*  32 */     FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
/*     */     
/*  34 */     File f = new File("plugins/Hive JumpPads/", "config.yml");
/*  35 */     FileConfiguration cfg1 = YamlConfiguration.loadConfiguration(f);
/*  36 */     this.hjp.getDataFolder();
/*  37 */     Player p = e.getPlayer();
/*     */     
/*  39 */     World Location = p.getWorld();
/*  40 */     Location pLoc = p.getLocation();
/*  41 */     if (cfg.getBoolean("HiveJumpPads.Trampoline.Enable Trampolines") == Boolean.FALSE.booleanValue()) {
/*  42 */       return;
/*     */     }
/*  44 */     if (cfg1.getStringList("HiveJumpPads.Disabed_Worlds_for_both_JumpPads").contains(Location.getName())) {
/*  45 */       return;
/*     */     }
/*  47 */     if (cfg.getStringList("HiveJumpPads.Trampolines.DisabledWorlds").contains(Location.getName())) {
/*  48 */       return;
/*     */     }
/*  50 */     if (cfg.getBoolean("HiveJumpPads.Trampoline.3x3 Field") == Boolean.FALSE.booleanValue()) {
/*  51 */       int PressurePlate_ID = pLoc.getWorld().getBlockAt(pLoc).getRelative(0, 0, 0).getTypeId();
/*  52 */       int ID = pLoc.getWorld().getBlockAt(pLoc).getRelative(0, -1, 0).getTypeId();
/*  53 */       Long time = Long.valueOf(System.currentTimeMillis());
/*     */       
/*  55 */       if (HiveJumpPads.cooldown.containsKey(p.getName())) {
/*  56 */         Long lastUsage = (Long)HiveJumpPads.cooldown.get(p.getName());
/*  57 */         if (lastUsage.longValue() + 1000L > time.longValue()) {
/*  58 */           return;
/*     */         }
/*     */       }
/*  61 */       if ((p.hasPermission("HiveJumpPads.use.Trampoline")) && 
/*  62 */         (PressurePlate_ID == cfg.getInt("HiveJumpPads.Trampoline.PressurePlateID")) && 
/*  63 */         (ID == cfg.getInt("HiveJumpPads.Trampoline.BlockID")))
/*     */       {
/*  65 */         double Height = cfg.getDouble("HiveJumpPads.Height.JumpHeight");
/*  66 */         double HeightBlock = Height / 6.3D;
/*     */         
/*  68 */         p.setVelocity(p.getVelocity().setY(HeightBlock));
/*  69 */         if ((cfg.getBoolean("HiveJumpPads.Sounds & Effects.Enable Sounds") != Boolean.FALSE.booleanValue()) && 
/*  70 */           (cfg.getBoolean("HiveJumpPads.Sounds & Effects.Enable Sounds") == Boolean.TRUE.booleanValue())) {
/*  71 */           p.playSound(p.getLocation(), Sound.valueOf(cfg.getString("HiveJumpPads.Sounds & Effects.Sound").toUpperCase()), 100.0F, 100.0F);
/*     */         }
/*     */         
/*  74 */         if (cfg.getBoolean("HiveJumpPads.Sounds & Effects.Enable Effects") != Boolean.FALSE.booleanValue()) {
/*  75 */           if (cfg.getBoolean("HiveJumpPads.Sounds & Effects.Enable Effects") == Boolean.TRUE.booleanValue()) {
/*  76 */             for (Player all : Bukkit.getOnlinePlayers()) {
/*  77 */               all.playEffect(p.getLocation(), org.bukkit.Effect.valueOf(cfg.getString("HiveJumpPads.Sounds & Effects.Effect")), 4);
/*     */             }
/*     */           }
/*     */           
/*  81 */           if (cfg.getBoolean("HiveJumpPads.Sounds & Effects.Enable message")) {
/*  82 */             p.sendMessage(TransanslateAlternateColors.colMsg(cfg.getString("HiveJumpPads.Sounds & Effects.message")));
/*     */           }
/*     */           
/*  85 */           HiveJumpPads.Jumped.put(p.getName(), Boolean.valueOf(true));
/*  86 */           HiveJumpPads.cooldown.put(p.getName(), time);
/*     */         }
/*     */         
/*     */       }
/*     */     }
/*  91 */     else if (cfg.getBoolean("HiveJumpPads.Trampoline.3x3 Field") == Boolean.TRUE.booleanValue())
/*     */     {
/*  93 */       if (p.hasPermission("HiveJumpPads.use.Trampoline"))
/*     */       {
/*  95 */         int ID = pLoc.getWorld().getBlockAt(pLoc).getRelative(0, 0, 0).getTypeId();
/*  96 */         int ID2 = pLoc.getWorld().getBlockAt(pLoc).getRelative(0, -1, 0).getTypeId();
/*  97 */         int ID3 = pLoc.getWorld().getBlockAt(pLoc).getRelative(1, -1, 0).getTypeId();
/*  98 */         int ID4 = pLoc.getWorld().getBlockAt(pLoc).getRelative(-1, -1, 0).getTypeId();
/*  99 */         int ID5 = pLoc.getWorld().getBlockAt(pLoc).getRelative(0, -1, 1).getTypeId();
/* 100 */         int ID6 = pLoc.getWorld().getBlockAt(pLoc).getRelative(0, -1, -1).getTypeId();
/* 101 */         int ID7 = pLoc.getWorld().getBlockAt(pLoc).getRelative(-1, -1, -1).getTypeId();
/* 102 */         int ID8 = pLoc.getWorld().getBlockAt(pLoc).getRelative(1, -1, 1).getTypeId();
/* 103 */         if ((ID == cfg.getInt("HiveJumpPads.Trampoline.PressurePlateID")) && 
/* 104 */           (ID2 == cfg.getInt("HiveJumpPads.Trampoline.BlockID")) && 
/* 105 */           (ID3 == cfg.getInt("HiveJumpPads.Trampoline.BlockID")) && 
/* 106 */           (ID4 == cfg.getInt("HiveJumpPads.Trampoline.BlockID")) && 
/* 107 */           (ID5 == cfg.getInt("HiveJumpPads.Trampoline.BlockID")) && 
/* 108 */           (ID6 == cfg.getInt("HiveJumpPads.Trampoline.BlockID")) && 
/* 109 */           (ID7 == cfg.getInt("HiveJumpPads.Trampoline.BlockID")) && 
/* 110 */           (ID8 == cfg.getInt("HiveJumpPads.Trampoline.BlockID")))
/*     */         {
/* 112 */           Long time = Long.valueOf(System.currentTimeMillis());
/* 113 */           if (HiveJumpPads.cooldown.containsKey(p.getName())) {
/* 114 */             Long lastUsage = (Long)HiveJumpPads.cooldown.get(p.getName());
/* 115 */             if (lastUsage.longValue() + 1000L > time.longValue()) {
/* 116 */               return;
/*     */             }
/*     */           }
/*     */           
/* 120 */           double Height = cfg.getDouble("HiveJumpPads.Height.JumpHeight");
/* 121 */           double HeightBlock = Height / 6.3D;
/*     */           
/* 123 */           p.setVelocity(p.getVelocity().setY(HeightBlock));
/* 124 */           if ((cfg.getBoolean("HiveJumpPads.Sounds & Effects.Enable Sounds") != Boolean.FALSE.booleanValue()) && 
/* 125 */             (cfg.getBoolean("HiveJumpPads.Sounds & Effects.Enable Sounds") == Boolean.TRUE.booleanValue())) {
/* 126 */             p.playSound(p.getLocation(), Sound.valueOf(cfg.getString("HiveJumpPads.Sounds & Effects.Sound").toUpperCase()), 100.0F, 100.0F);
/*     */           }
/*     */           
/* 129 */           if ((cfg.getBoolean("HiveJumpPads.Sounds & Effects.Enable Effects") != Boolean.FALSE.booleanValue()) && 
/* 130 */             (cfg.getBoolean("HiveJumpPads.Sounds & Effects.Enable Effects") == Boolean.TRUE.booleanValue())) {
/* 131 */             for (Player all : Bukkit.getOnlinePlayers()) {
/* 132 */               all.playEffect(p.getLocation(), org.bukkit.Effect.valueOf(cfg.getString("HiveJumpPads.Sounds & Effects.Effect").toUpperCase()), 4);
/*     */             }
/*     */             
/* 135 */             if (cfg.getBoolean("HiveJumpPads.Sounds & Effects.Enable message")) {
/* 136 */               p.sendMessage(TransanslateAlternateColors.colMsg(cfg.getString("HiveJumpPads.Sounds & Effects.message")));
/*     */             }
/*     */             
/* 139 */             HiveJumpPads.Jumped.put(p.getName(), Boolean.valueOf(true));
/* 140 */             HiveJumpPads.cooldown.put(p.getName(), time);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              M:\BENUTZER\Downloads\Hive_JumpPads_v.2.16.jar!\eu\Sendarox\HiveJumpPads\Listener\TrampolineListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */