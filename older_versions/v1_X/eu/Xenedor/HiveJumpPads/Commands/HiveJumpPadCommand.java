/*     */ package eu.Xenedor.HiveJumpPads.Commands;
/*     */ 
/*     */ import eu.Xenedor.HiveJumpPads.ConfigReload;
/*     */ import eu.Xenedor.HiveJumpPads.HiveJumpPads;
/*     */ import eu.Xenedor.HiveJumpPads.TranslateAlternateColors;
/*     */ import java.io.File;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ 
/*     */ public class HiveJumpPadCommand
/*     */ {
/*     */   private HiveJumpPads pl;
/*     */   private ConfigReload cr;
/*     */   Command cmd;
/*     */   String[] args;
/*     */   
/*     */   public HiveJumpPadCommand(Command cmd, String[] args, HiveJumpPads pl)
/*     */   {
/*  23 */     this.cmd = cmd;
/*  24 */     this.args = args;
/*  25 */     this.pl = pl;
/*     */   }
/*     */   
/*  28 */   private String HiveJumpPads_Prefix = "§6[§aHiveJumpPads§6]§7 ";
/*  29 */   private String HiveJumpPads_ConsolePrefix = "[HiveJumpPads] ";
/*  30 */   private String HiveJumpPads_Version = "1.9.1";
/*     */   
/*     */   public boolean onExecute(CommandSender sender, Command cmd, String label, String[] args)
/*     */   {
/*  34 */     File config = new File(this.pl.getDataFolder(), "Config.yml");
/*  35 */     File Messages = new File(this.pl.getDataFolder(), "Messages.yml");
/*  36 */     File JumpPad = new File(this.pl.getDataFolder() + "/Configs/", "JumpPad-Config.yml");
/*  37 */     File Trampoline = new File(this.pl.getDataFolder() + "/Configs/", "Trampoline-Config.yml");
/*     */     
/*  39 */     FileConfiguration cfg = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(Messages);
/*     */     
/*  41 */     String NoPermissions = TranslateAlternateColors.ColoredMessage(cfg.getString("HiveJumpPads.Messages.No Permissions"));
/*  42 */     String Reload = TranslateAlternateColors.ColoredMessage(cfg.getString("HiveJumpPads.Messages.Reload"));
/*  43 */     final String ReloadComplete = TranslateAlternateColors.ColoredMessage(cfg.getString("HiveJumpPads.Messages.Reload Complete"));
/*  44 */     if ((sender instanceof Player))
/*     */     {
/*  46 */       if (args.length == 0)
/*     */       {
/*  48 */         Player p = (Player)sender;
/*  49 */         p.sendMessage(this.HiveJumpPads_Prefix + "HiveJumpPads Version:§6 " + this.HiveJumpPads_Version);
/*     */       }
/*  51 */       else if (args.length == 1)
/*     */       {
/*  53 */         if (args[0].equalsIgnoreCase("Info"))
/*     */         {
/*  55 */           Player p = (Player)sender;
/*  56 */           p.sendMessage(this.HiveJumpPads_Prefix + "HiveJumpPads Version:§6 " + this.HiveJumpPads_Version);
/*     */         }
/*  58 */         else if ((args[0].equalsIgnoreCase("Version")) || (args[0].equalsIgnoreCase("ver")))
/*     */         {
/*  60 */           Player p = (Player)sender;
/*     */           
/*  62 */           p.sendMessage(this.HiveJumpPads_Prefix + "HiveJumpPads Version: §6" + this.HiveJumpPads_Version);
/*  63 */           p.sendMessage(" ");
/*  64 */           p.sendMessage(this.HiveJumpPads_Prefix + "Running on:§6 " + Bukkit.getVersion());
/*     */         }
/*  66 */         else if (args[0].equalsIgnoreCase("Download"))
/*     */         {
/*  68 */           Player p = (Player)sender;
/*     */           
/*  70 */           p.sendMessage("§7------------< §aHiveJumpPads Downlaod§7 >------------");
/*  71 */           p.sendMessage(" ");
/*  72 */           p.sendMessage("§6http://dev.Bukkit.org/bukkit-plugins/hive-jump-pads/");
/*     */         }
/*  74 */         else if (args[0].equalsIgnoreCase("Help"))
/*     */         {
/*  76 */           Player p = (Player)sender;
/*     */           
/*  78 */           p.sendMessage("§7------------< §aHiveJumpPads Help§7 >-------------");
/*  79 */           p.sendMessage(" ");
/*  80 */           p.sendMessage("§6/HiveJumpPads Info§7 - A Info about the Plugin");
/*  81 */           p.sendMessage("§6/HiveJumpPads Argruments§7 - Command Argruments");
/*  82 */           p.sendMessage("§6/HiveJumpPads Version §7- Plugin Version");
/*  83 */           p.sendMessage("§6/HiveJumpPads Download §7- Plugin Download");
/*  84 */           p.sendMessage("§6/HiveJumpPads Config§7 - Config Settigns");
/*     */         }
/*  86 */         else if ((args[0].equalsIgnoreCase("Argruments")) || (args[0].equalsIgnoreCase("args")))
/*     */         {
/*  88 */           if (sender.hasPermission("HiveJumpPads.Command.Argruments"))
/*     */           {
/*  90 */             Player p = (Player)sender;
/*     */             
/*  92 */             p.sendMessage("§7-------------<§a HiveJumpPads Args§7 >-------------");
/*  93 */             p.sendMessage(" ");
/*  94 */             p.sendMessage("§7Anivable Argruments for §6HiveJumpPads§7:");
/*  95 */             p.sendMessage(" ");
/*  96 */             p.sendMessage("§6Info§7, §6Argruments§7,§6 Version§7,§6 Download§7 and");
/*  97 */             p.sendMessage("§6Config");
/*     */           }
/*     */           else
/*     */           {
/* 101 */             Player p = (Player)sender;
/*     */             
/* 103 */             p.sendMessage(NoPermissions);
/*     */           }
/*     */         }
/* 106 */         else if ((args[0].equalsIgnoreCase("config")) || (args[0].equalsIgnoreCase("configs")))
/*     */         {
/* 108 */           if (sender.hasPermission("HiveJumpPads.Command.Config"))
/*     */           {
/* 110 */             Player p = (Player)sender;
/*     */             
/* 112 */             p.sendMessage("§7-------------<§a HiveJumpPads Config§7 >-------------");
/* 113 */             p.sendMessage(" ");
/* 114 */             if (config.exists()) {
/* 115 */               p.sendMessage("§6Config.yml§7 - §aLoaded");
/*     */             } else {
/* 117 */               p.sendMessage("§6Config.yml§7 - §cUnloaded");
/*     */             }
/* 119 */             if (Messages.exists()) {
/* 120 */               p.sendMessage("§6Messages.yml§7 - §aLoaded");
/*     */             } else {
/* 122 */               p.sendMessage("§6Messages.yml§7 - §cUnloaded");
/*     */             }
/* 124 */             if (JumpPad.exists()) {
/* 125 */               p.sendMessage("§6JumpPad-Config.yml§7 - §aLoaded");
/*     */             } else {
/* 127 */               p.sendMessage("§6JumpPad-Config.yml§7 - §cUnloaded");
/*     */             }
/* 129 */             if (Trampoline.exists()) {
/* 130 */               p.sendMessage("§6Trampoline-Config.yml§7 - §aLoaded");
/*     */             } else {
/* 132 */               p.sendMessage("§6Trampoline-Config.yml§7 - §cUnloaded");
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 137 */             Player p = (Player)sender;
/*     */             
/* 139 */             p.sendMessage(NoPermissions);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 144 */           sender.sendMessage("...");
/*     */         }
/* 146 */         if (args.length == 2) {
/* 147 */           if ((args[0].equalsIgnoreCase("config")) || (args[0].equalsIgnoreCase("configs")))
/*     */           {
/* 149 */             if (sender.hasPermission("HiveJumpPads.Command.Config")) {
/* 150 */               if (args[1].equalsIgnoreCase("reload"))
/*     */               {
/* 152 */                 final Player p = (Player)sender;
/*     */                 
/* 154 */                 p.sendMessage(this.HiveJumpPads_Prefix + Reload);
/* 155 */                 this.cr.ReloadAll();
/*     */                 
/* 157 */                 Bukkit.getScheduler().scheduleSyncDelayedTask(this.pl, new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 161 */                     p.sendMessage(HiveJumpPadCommand.this.HiveJumpPads_Prefix + ReloadComplete);
/* 162 */                     p.sendMessage(" ");
/* 163 */                     p.sendMessage(HiveJumpPadCommand.this.HiveJumpPads_Prefix + "§6INFO:§7 You can use Argruments (§6/hjp reload <Config | JumpPad | Trampoline | Messages | All>§7)");
/*     */                   }
/* 165 */                 }, 20L);
/*     */               }
/*     */               else
/*     */               {
/* 169 */                 Player p = (Player)sender;
/*     */                 
/* 171 */                 p.sendMessage(this.HiveJumpPads_Prefix);
/*     */               }
/*     */             }
/*     */           }
/* 175 */           else if (((args[0].equalsIgnoreCase("argruments")) || (args[0].equalsIgnoreCase("args"))) && 
/* 176 */             (sender.hasPermission("HiveJumpPads.Command.Argruments"))) {
/* 177 */             if (args[1].equalsIgnoreCase("Config")) {
/* 178 */               sender.sendMessage(" ");
/*     */             } else {
/* 180 */               sender.sendMessage(" ");
/*     */             }
/*     */           }
/*     */         }
/* 184 */         if (args.length == 3) {
/* 185 */           if ((args[0].equalsIgnoreCase("config")) || (args[0].equalsIgnoreCase("configs")))
/*     */           {
/* 187 */             if ((sender.hasPermission("HiveJumpPads.Command.Config")) && 
/* 188 */               (args[1].equalsIgnoreCase("reload"))) {
/* 189 */               if (args[2].equalsIgnoreCase("all"))
/*     */               {
/* 191 */                 final Player p = (Player)sender;
/*     */                 
/* 193 */                 p.sendMessage(Reload);
/*     */                 
/* 195 */                 Bukkit.getScheduler().scheduleSyncDelayedTask(this.pl, new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 199 */                     HiveJumpPadCommand.this.cr.ReloadAll();
/* 200 */                     HiveJumpPadCommand.this.cr.ConfigReload();
/* 201 */                     HiveJumpPadCommand.this.cr.MessagesReload();
/* 202 */                     HiveJumpPadCommand.this.cr.JumpPadReload();
/* 203 */                     HiveJumpPadCommand.this.cr.TrampolineReload();
/* 204 */                     p.sendMessage(ReloadComplete);
/*     */                   }
/* 206 */                 }, 30L);
/*     */               }
/* 208 */               else if (args[2].equalsIgnoreCase("Config"))
/*     */               {
/* 210 */                 final Player p = (Player)sender;
/*     */                 
/* 212 */                 p.sendMessage(Reload);
/*     */                 
/* 214 */                 Bukkit.getScheduler().scheduleSyncDelayedTask(this.pl, new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 218 */                     HiveJumpPadCommand.this.cr.ConfigReload();
/* 219 */                     p.sendMessage(ReloadComplete);
/*     */                   }
/* 221 */                 }, 20L);
/*     */               }
/* 223 */               else if (args[2].equalsIgnoreCase("Messages"))
/*     */               {
/* 225 */                 final Player p = (Player)sender;
/*     */                 
/* 227 */                 p.sendMessage(Reload);
/*     */                 
/* 229 */                 Bukkit.getScheduler().scheduleSyncDelayedTask(this.pl, new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 233 */                     HiveJumpPadCommand.this.cr.MessagesReload();
/* 234 */                     p.sendMessage(ReloadComplete);
/*     */                   }
/* 236 */                 }, 20L);
/*     */               }
/* 238 */               else if (args[2].equalsIgnoreCase("JumpPad"))
/*     */               {
/* 240 */                 final Player p = (Player)sender;
/*     */                 
/* 242 */                 p.sendMessage(Reload);
/*     */                 
/* 244 */                 Bukkit.getScheduler().scheduleSyncDelayedTask(this.pl, new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 248 */                     HiveJumpPadCommand.this.cr.MessagesReload();
/* 249 */                     p.sendMessage(ReloadComplete);
/*     */                   }
/* 251 */                 }, 20L);
/*     */               }
/* 253 */               else if (args[2].equalsIgnoreCase("Trampoline"))
/*     */               {
/* 255 */                 final Player p = (Player)sender;
/*     */                 
/* 257 */                 p.sendMessage(Reload);
/*     */                 
/* 259 */                 Bukkit.getScheduler().scheduleSyncDelayedTask(this.pl, new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 263 */                     HiveJumpPadCommand.this.cr.MessagesReload();
/* 264 */                     p.sendMessage(ReloadComplete);
/*     */                   }
/* 266 */                 }, 20L);
/*     */               }
/*     */               else
/*     */               {
/* 270 */                 Player p = (Player)sender;
/*     */                 
/* 272 */                 p.sendMessage(this.HiveJumpPads_Prefix + "This Command is not Vailid.");
/* 273 */                 p.sendMessage(" ");
/* 274 */                 p.sendMessage(this.HiveJumpPads_Prefix + "Please use §6/HiveJumpPads Config Reload <all | Config | Messages | JumpPad | Trampoline>");
/*     */               }
/*     */             }
/*     */           }
/* 278 */           else if (((args[0].equalsIgnoreCase("argruments")) || (args[0].equalsIgnoreCase("args"))) && 
/* 279 */             (sender.hasPermission("HiveJumpPads.Command.Argruments")) && (
/* 280 */             (args[1].equalsIgnoreCase("config")) || (args[0].equalsIgnoreCase("configs")))) {
/* 281 */             if (args[2].equalsIgnoreCase("reload"))
/*     */             {
/* 283 */               Player p = (Player)sender;
/*     */               
/* 285 */               p.sendMessage("§7-------------<§a HiveJumpPads Args§7 >-------------");
/* 286 */               p.sendMessage(" ");
/* 287 */               p.sendMessage("§7Anivable Argruments:");
/* 288 */               p.sendMessage(" ");
/* 289 */               p.sendMessage("§6Config§7,§6 Messages§7,§6 JumpPads§7, §6Trampolines§7,");
/* 290 */               p.sendMessage("§6all");
/*     */             }
/* 292 */             else if (args[2].equalsIgnoreCase("show"))
/*     */             {
/* 294 */               Player p = (Player)sender;
/*     */               
/* 296 */               p.sendMessage("§7-------------<§a HiveJumpPads Args§7 >-------------");
/* 297 */               p.sendMessage(" ");
/* 298 */               p.sendMessage("§7Anivable Argruments:");
/* 299 */               p.sendMessage(" ");
/* 300 */               p.sendMessage("§6Config§7,§6 Messages§7,§6 JumpPads§7, §6Trampolines§7,");
/* 301 */               p.sendMessage("§6all");
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     else {
/* 308 */       System.out.println(this.HiveJumpPads_ConsolePrefix + "Only users can use the Command.");
/*     */     }
/* 310 */     return true;
/*     */   }
/*     */ }


/* Location:              M:\BENUTZER\Downloads\HiveJumpPads_v1.9.3.jar!\eu\Xenedor\HiveJumpPads\Commands\HiveJumpPadCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */