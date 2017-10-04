/*     */ package eu.Xenedor.HiveJumpPads.Listener;
/*     */ 
/*     */ import eu.Xenedor.HiveJumpPads.HiveJumpPads;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Effect;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class JumpPadListener implements org.bukkit.event.Listener
/*     */ {
/*     */   private HiveJumpPads pl;
/*     */   
/*     */   public JumpPadListener(HiveJumpPads pl)
/*     */   {
/*  28 */     this.pl = pl;
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   private void onJump(PlayerInteractEvent e)
/*     */   {
/*  34 */     File file = new File("plugins/HiveJumpPads/Configs/", "JumpPad-Config.yml");
/*  35 */     FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
/*     */     
/*  37 */     Player p = e.getPlayer();
/*  38 */     Action a = e.getAction();
/*     */     
/*  40 */     String w = p.getWorld().getName();
/*  41 */     if (cfg.getBoolean("HiveJumpPads.JumpPads.3x3 Field") == Boolean.TRUE.booleanValue())
/*     */     {
/*  43 */       if (cfg.getStringList("HiveJumpPads.JumpPads.Disabled World").contains(w))
/*     */       {
/*  45 */         if (this.pl.getConfig().getStringList("HiveJumpPads.Disabled Worlds for both Pads").contains(w))
/*     */         {
/*  47 */           if (cfg.getBoolean("HiveJumpPads.JumpPads.Enable Jump Pads") == Boolean.FALSE.booleanValue()) {
/*  48 */             return;
/*     */           }
/*  50 */           return;
/*     */         }
/*  52 */         return;
/*     */       }
/*  54 */       String Config_Sound = cfg.getString("HiveJumpPads.JumpPads.Sound");
/*  55 */       String Config_Effect = cfg.getString("HiveJumpPads.JumpPads.Effect");
/*     */       
/*  57 */       boolean enable_Sounds = cfg.getBoolean("HiveJumpPads.JumpPads.Enable Sounds");
/*  58 */       boolean enable_Effects = cfg.getBoolean("HiveJumpPads.JumpPads.Enable Effects");
/*     */       
/*  60 */       int JumpPadPressurePlate = cfg.getInt("HiveJumpPads.JumpPads.PressurePlate ID");
/*  61 */       int JumpPadPlattformBlock = cfg.getInt("HiveJumpPads.JumpPads.Block ID");
/*     */       
/*  63 */       double Height_multiplier = cfg.getDouble("HiveJumpPads.JumpPads.Jump height");
/*  64 */       double Lenght_multiplier = cfg.getDouble("HiveJumpPads.JumpPads.Jump lenght");
/*  65 */       if (a.equals(Action.PHYSICAL))
/*     */       {
/*  67 */         Block b = e.getClickedBlock();
/*  68 */         if (b.getTypeId() == JumpPadPressurePlate)
/*     */         {
/*  70 */           Block under = b.getRelative(0, -1, 0);
/*  71 */           Block right = b.getRelative(0, -1, 1);
/*  72 */           Block left = b.getRelative(1, -1, 0);
/*  73 */           Block north = b.getRelative(-1, -1, 0);
/*  74 */           Block south = b.getRelative(0, -1, -1);
/*     */           
/*  76 */           Block north_west = b.getRelative(1, -1, 1);
/*  77 */           Block north_east = b.getRelative(-1, -1, -1);
/*  78 */           Block south_east = b.getRelative(1, -1, -1);
/*  79 */           Block south_west = b.getRelative(-1, -1, 1);
/*  80 */           if ((under.getTypeId() == JumpPadPlattformBlock) && 
/*  81 */             (right.getTypeId() == JumpPadPlattformBlock) && 
/*  82 */             (left.getTypeId() == JumpPadPlattformBlock) && 
/*  83 */             (north.getTypeId() == JumpPadPlattformBlock) && 
/*  84 */             (south.getTypeId() == JumpPadPlattformBlock) && 
/*  85 */             (north_west.getTypeId() == JumpPadPlattformBlock) && 
/*  86 */             (north_east.getTypeId() == JumpPadPlattformBlock) && 
/*  87 */             (south_east.getTypeId() == JumpPadPlattformBlock) && 
/*  88 */             (south_west.getTypeId() == JumpPadPlattformBlock))
/*     */           {
/*  90 */             double Height = Height_multiplier / 15.0D;
/*  91 */             double Lenght = Lenght_multiplier / 8.0D;
/*     */             
/*  93 */             p.setVelocity(p.getLocation().getDirection().setY(Height).multiply(Lenght));
/*  94 */             if (enable_Sounds == Boolean.TRUE.booleanValue())
/*  95 */               p.playSound(p.getLocation(), Sound.valueOf(Config_Sound.toUpperCase()), 1.0F, 1.0F); else {
/*     */               return;
/*     */             }
/*     */             Player[] arrayOfPlayer;
/*  99 */             int j = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length; for (int i = 0; i < j; i++) { Player all = arrayOfPlayer[i];
/* 100 */               if (enable_Effects == Boolean.TRUE.booleanValue()) {
/* 101 */                 all.playEffect(p.getLocation(), Effect.valueOf(Config_Effect.toUpperCase()), 0);
/*     */               } else {
/* 103 */                 return;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void onJump_1x1(PlayerInteractEvent e)
/*     */   {
/* 116 */     File file = new File("plugins/HiveJumpPads/Configs/", "JumpPad-Config.yml");
/* 117 */     FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
/*     */     
/* 119 */     Player p = e.getPlayer();
/* 120 */     Action a = e.getAction();
/*     */     
/* 122 */     World w = p.getWorld();
/* 123 */     if (cfg.getBoolean("HiveJumpPads.JumpPads.3x3 Field") == Boolean.FALSE.booleanValue())
/*     */     {
/* 125 */       if (cfg.getStringList("HiveJumpPads.JumpPads.Disabled World").contains(w.getName()))
/*     */       {
/* 127 */         if (cfg.getBoolean("HiveJumpPads.JumpPads.Enable Jump Pads") == Boolean.FALSE.booleanValue())
/*     */         {
/* 129 */           if (this.pl.getConfig().getStringList("HiveJumpPads.JumpPads.Enable Jump Pads").contains(w)) {
/* 130 */             return;
/*     */           }
/* 132 */           return;
/*     */         }
/* 134 */         return;
/*     */       }
/* 136 */       String Config_Effect = cfg.getString("HiveJumpPads.JumpPads.Effect").toUpperCase();
/* 137 */       String Config_Sound = cfg.getString("HiveJumpPads.JumpPads.Sound").toUpperCase();
/*     */       
/* 139 */       boolean Enable_Sounds = cfg.getBoolean("HiveJumpPads.JumpPads.Enable Sounds");
/* 140 */       boolean Enable_Effects = cfg.getBoolean("HiveJumpPads.JumpPads.Enable Effects");
/*     */       
/* 142 */       int JumpPadPlate = cfg.getInt("HiveJumpPads.JumpPads.PressurePlate ID");
/* 143 */       int JumpPadPlattform = cfg.getInt("HiveJumpPads.JumpPads.Block ID");
/*     */       
/* 145 */       double Height_multiplier = cfg.getDouble("HiveJumpPads.JumpPads.Jump height");
/* 146 */       double Lenght_multiplier = cfg.getDouble("HiveJumpPads.JumpPads.Jump lenght");
/* 147 */       if (a.equals(Action.PHYSICAL))
/*     */       {
/* 149 */         Block b = e.getClickedBlock();
/* 150 */         Block pplate = b.getRelative(BlockFace.SELF);
/* 151 */         if (pplate.getTypeId() == JumpPadPlate)
/*     */         {
/* 153 */           Block under = b.getRelative(0, -1, 0);
/* 154 */           if (under.getTypeId() == JumpPadPlattform)
/*     */           {
/* 156 */             e.setCancelled(true);
/*     */             
/* 158 */             double Height = Height_multiplier / 15.0D;
/* 159 */             double Lenght = Lenght_multiplier / 8.0D;
/*     */             
/* 161 */             p.setVelocity(p.getLocation().getDirection().setY(Height).multiply(Lenght));
/* 162 */             if (Enable_Sounds == Boolean.TRUE.booleanValue())
/* 163 */               p.playSound(p.getLocation(), Sound.valueOf(Config_Sound), 1.0F, 1.0F); else {
/*     */               return;
/*     */             }
/*     */             Player[] arrayOfPlayer;
/* 167 */             int j = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length; for (int i = 0; i < j; i++) { Player all = arrayOfPlayer[i];
/* 168 */               if (Enable_Effects == Boolean.TRUE.booleanValue()) {
/* 169 */                 all.playEffect(p.getLocation(), Effect.valueOf(Config_Effect), 0);
/*     */               } else {
/* 171 */                 return;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              M:\BENUTZER\Downloads\HiveJumpPads_v1.9.3.jar!\eu\Xenedor\HiveJumpPads\Listener\JumpPadListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */