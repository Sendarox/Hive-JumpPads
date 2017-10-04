/*     */ package eu.Sendarox.HiveJumpPads;
/*     */ 
/*     */ import eu.Sendarox.HiveJumpPads.Variables.Strings;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.FileConfigurationOptions;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ 
/*     */ public class Configuration
/*     */ {
/*     */   public static void Konfiguration()
/*     */   {
/*  15 */     File f = new File("plugins/Hive JumpPads/", "config.yml");
/*  16 */     FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
/*     */     
/*  18 */     if (f.exists()) {
/*  19 */       cfg.options().header("Hive JumpPads v" + Strings.HJP_VERSION + " | Main Configuration | Developed by Sendarox\n");
/*     */       
/*     */ 
/*  22 */       cfg.addDefault("HiveJumpPads.Disable_GlobalFallDamage", Boolean.valueOf(false));
/*  23 */       cfg.addDefault("HiveJumpPads.Disable_JumpPadFallDamage", Boolean.valueOf(true));
/*  24 */       cfg.addDefault("HiveJumpPads.Use_Updater", Boolean.valueOf(true));
/*  25 */       cfg.addDefault("HiveJumpPads.Disabed_Worlds_for_both_JumpPads", new String[] {
/*  26 */         "World 1", 
/*  27 */         "World 2", 
/*  28 */         "World 3" });
/*     */       
/*     */ 
/*  31 */       cfg.options().copyDefaults(true);
/*  32 */       cfg.options().copyHeader(true);
/*     */     }
/*     */     else {
/*  35 */       System.out.println(Strings.HJP_CONSOLE + f.getName() + " does not exist!");
/*  36 */       System.out.println("--> Creating a new one.");
/*     */       
/*  38 */       cfg.options().header("Hive JumpPads v" + Strings.HJP_VERSION + " | Main Configuration | Developed by Sendarox\n");
/*     */       
/*  40 */       cfg.addDefault("HiveJumpPads.Disable_GlobalFallDamage", Boolean.valueOf(false));
/*  41 */       cfg.addDefault("HiveJumpPads.Disable_JumpPadFallDamage", Boolean.valueOf(true));
/*  42 */       cfg.addDefault("HiveJumpPads.Use_Updater", Boolean.valueOf(true));
/*     */       
/*  44 */       cfg.addDefault("HiveJumpPads.Disabed_Worlds_for_both_JumpPads", new String[] {
/*  45 */         "World 1", 
/*  46 */         "World 2", 
/*  47 */         "World 3" });
/*     */       
/*     */ 
/*  50 */       cfg.options().copyDefaults(true);
/*  51 */       cfg.options().copyHeader(true);
/*     */     }
/*     */     try
/*     */     {
/*  55 */       cfg.save(f);
/*     */     } catch (IOException e) {
/*  57 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static void MessagesConfiguration()
/*     */   {
/*  64 */     File f = new File("plugins/Hive JumpPads/", "messages.yml");
/*  65 */     FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
/*     */     
/*  67 */     if (f.exists())
/*     */     {
/*  69 */       cfg.options().header("Hive JumpPads v" + Strings.HJP_VERSION + " | Messages Configuration | Developed by Sendarox\n");
/*     */       
/*  71 */       cfg.addDefault("HiveJumpPads.Messages.NoPermissions", "&cYou don't have enough Permissions to execute this Command.");
/*  72 */       cfg.addDefault("HiveJumpPads.Messages.CommandSynatax", "&cThis Command [/hjp %cmd%] isn't right. Please use \"/hjp help\" for help.");
/*     */       
/*     */ 
/*     */ 
/*  76 */       cfg.options().copyDefaults(true);
/*  77 */       cfg.options().copyHeader(true);
/*     */     }
/*     */     else {
/*  80 */       System.out.println(Strings.HJP_CONSOLE + f.getName() + " does not exist!");
/*  81 */       System.out.println("--> Creating a new one.");
/*     */       
/*  83 */       cfg.options().header("Hive JumpPads v" + Strings.HJP_VERSION + " | Messages Configuration | Developed by Sendarox\n");
/*     */       
/*  85 */       cfg.addDefault("HiveJumpPads.Messages.NoPermissions", "&cYou don't have enough Permissions to execute this Command.");
/*  86 */       cfg.addDefault("HiveJumpPads.Messages.CommandSynatax", "&cThis Command [/hjp %cmd%] isn't right. Please use \"/hjp help\" for help.");
/*     */       
/*     */ 
/*     */ 
/*  90 */       cfg.options().copyDefaults(true);
/*  91 */       cfg.options().copyHeader(true);
/*     */       try
/*     */       {
/*  94 */         cfg.save(f);
/*     */       }
/*     */       catch (IOException localIOException1) {}
/*     */     }
/*     */     try {
/*  99 */       cfg.save(f);
/*     */     } catch (IOException e) {
/* 101 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void JumpPadConfiguration()
/*     */   {
/* 107 */     File f = new File("plugins/Hive JumpPads/JumpPads/", "JumpPad-Config.yml");
/* 108 */     FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
/*     */     
/* 110 */     if (f.exists()) {
/* 111 */       cfg.options().header("Hive JumpPads v" + Strings.HJP_VERSION + " | JumpPad Configuration | Developed by Sendarox\n" + 
/* 112 */         "\nAvailable Sounds and Effects:\n" + 
/* 113 */         "\n" + Strings.HJP_SOUND + "\n" + 
/* 114 */         Strings.HJP_EFFECTS + "\n");
/*     */       
/* 116 */       cfg.addDefault("HiveJumpPads.JumpPad.Enable JumpPads", Boolean.valueOf(true));
/* 117 */       cfg.addDefault("HiveJumpPads.JumpPad.3x3 Field", Boolean.valueOf(true));
/* 118 */       cfg.addDefault("HiveJumpPads.JumpPad.BlockID", Integer.valueOf(152));
/* 119 */       cfg.addDefault("HiveJumpPads.JumpPad.PressurePlateID", Integer.valueOf(70));
/* 120 */       cfg.addDefault("HiveJumpPads.Lenght & Height.JumpLenght", Double.valueOf(35.0D));
/* 121 */       cfg.addDefault("HiveJumpPads.Lenght & Height.JumpHeight", Double.valueOf(4.0D));
/* 122 */       cfg.addDefault("HiveJumpPads.Sound & Effects.Enable Sounds", Boolean.valueOf(true));
/* 123 */       cfg.addDefault("HiveJumpPads.Sound & Effects.Enable Effects", Boolean.valueOf(true));
/* 124 */       cfg.addDefault("HiveJumpPads.Sound & Effects.Enable message", Boolean.valueOf(false));
/* 125 */       cfg.addDefault("HiveJumpPads.Sound & Effects.Sound", "WITHER_SHOOT");
/* 126 */       cfg.addDefault("HiveJumpPads.Sound & Effects.Effect", "SMOKE");
/* 127 */       cfg.addDefault("HiveJumpPads.Sound & Effects.message", "&aWOOOOOSH!!");
/* 128 */       cfg.addDefault("HiveJumpPads.JumpPads.DisabledWorlds", new String[] {
/* 129 */         "World 1", 
/* 130 */         "World 2", 
/* 131 */         "World 3" });
/*     */       
/*     */ 
/* 134 */       cfg.options().copyDefaults(true);
/* 135 */       cfg.options().copyHeader(true);
/*     */     }
/*     */     else {
/* 138 */       System.out.println(Strings.HJP_CONSOLE + f.getName() + " does not exist!");
/* 139 */       System.out.println("--> Creating a new one.");
/*     */       
/* 141 */       cfg.options().header("Hive JumpPads v" + Strings.HJP_VERSION + " | JumpPad Configuration | Developed by Sendarox\n" + 
/* 142 */         "\nAvailable Sounds and Effects:\n" + 
/* 143 */         "\n" + Strings.HJP_SOUND + "\n" + 
/* 144 */         Strings.HJP_EFFECTS + "\n");
/*     */       
/* 146 */       cfg.addDefault("HiveJumpPads.JumpPad.Enable JumpPads", Boolean.valueOf(true));
/* 147 */       cfg.addDefault("HiveJumpPads.JumpPad.3x3 Field", Boolean.valueOf(true));
/* 148 */       cfg.addDefault("HiveJumpPads.JumpPad.BlockID", Integer.valueOf(152));
/* 149 */       cfg.addDefault("HiveJumpPads.JumpPad.PressurePlateID", Integer.valueOf(70));
/* 150 */       cfg.addDefault("HiveJumpPads.Lenght & Height.JumpLenght", Double.valueOf(35.0D));
/* 151 */       cfg.addDefault("HiveJumpPads.Lenght & Height.JumpHeight", Double.valueOf(4.0D));
/* 152 */       cfg.addDefault("HiveJumpPads.Sound & Effects.Enable Sounds", Boolean.valueOf(true));
/* 153 */       cfg.addDefault("HiveJumpPads.Sound & Effects.Enable Effects", Boolean.valueOf(true));
/* 154 */       cfg.addDefault("HiveJumpPads.Sound & Effects.Enable message", Boolean.valueOf(false));
/* 155 */       cfg.addDefault("HiveJumpPads.Sound & Effects.Sound", "WITHER_SHOOT");
/* 156 */       cfg.addDefault("HiveJumpPads.Sound & Effects.Effect", "SMOKE");
/* 157 */       cfg.addDefault("HiveJumpPads.Sound & Effects.message", "&aWOOOOOSH!!");
/* 158 */       cfg.addDefault("HiveJumpPads.JumpPads.DisabledWorlds", new String[] {
/* 159 */         "World 1", 
/* 160 */         "World 2", 
/* 161 */         "World 3" });
/*     */       
/*     */ 
/* 164 */       cfg.options().copyDefaults(true);
/* 165 */       cfg.options().copyHeader(true);
/*     */       try
/*     */       {
/* 168 */         cfg.save(f);
/*     */       } catch (IOException localIOException1) {}
/*     */     }
/* 171 */     try { cfg.save(f);
/*     */     } catch (IOException e) {
/* 173 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static void TrampolineConfiguration()
/*     */   {
/* 180 */     File f = new File("plugins/Hive JumpPads/JumpPads/", "Trampoline-Config.yml");
/* 181 */     FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
/*     */     
/* 183 */     if (f.exists()) {
/* 184 */       cfg.options().header("Hive JumpPads v" + Strings.HJP_VERSION + " | Trampoline Configuration | Developed by Sendarox\n" + 
/* 185 */         "\nAvailable Sounds and Effects:\n" + 
/* 186 */         Strings.HJP_SOUND + "\n" + 
/* 187 */         Strings.HJP_EFFECTS + "\n");
/*     */       
/* 189 */       cfg.addDefault("HiveJumpPads.Trampoline.Enable Trampolines", Boolean.valueOf(true));
/* 190 */       cfg.addDefault("HiveJumpPads.Trampoline.3x3 Field", Boolean.valueOf(true));
/* 191 */       cfg.addDefault("HiveJumpPads.Trampoline.BlockID", Integer.valueOf(152));
/* 192 */       cfg.addDefault("HiveJumpPads.Trampoline.PressurePlateID", Integer.valueOf(147));
/* 193 */       cfg.addDefault("HiveJumpPads.Height.JumpHeight", Double.valueOf(10.0D));
/* 194 */       cfg.addDefault("HiveJumpPads.Sounds & Effects.Enable Sounds", Boolean.valueOf(false));
/* 195 */       cfg.addDefault("HiveJumpPads.Sounds & Effects.Enable Effects", Boolean.valueOf(true));
/* 196 */       cfg.addDefault("HiveJumpPads.Sounds & Effects.Enable message", Boolean.valueOf(false));
/* 197 */       cfg.addDefault("HiveJumpPads.Sounds & Effects.Sound", "WITHER_SHOOT");
/* 198 */       cfg.addDefault("HiveJumpPads.Sounds & Effects.Effect", "SMOKE");
/* 199 */       cfg.addDefault("HiveJumpPads.Sounds & Effects.message", "&aGET UP.");
/* 200 */       cfg.addDefault("HiveJumpPads.Trampolines.DisabledWorlds", new String[] {
/* 201 */         "World 1", 
/* 202 */         "World 2", 
/* 203 */         "World 3" });
/*     */     }
/*     */     else
/*     */     {
/* 207 */       System.out.println(Strings.HJP_CONSOLE + f.getName() + " does not exist!");
/* 208 */       System.out.println("--> Creating a new one.");
/*     */       
/* 210 */       cfg.options().header("Hive JumpPads v" + Strings.HJP_VERSION + " | Trampoline Configuration | Developed by Sendarox\n" + 
/* 211 */         "\n Available Sounds and Effects:" + 
/* 212 */         Strings.HJP_SOUND + "\n" + 
/* 213 */         Strings.HJP_EFFECTS + "\n");
/*     */       
/* 215 */       cfg.addDefault("HiveJumpPads.Trampoline.Enable Trampolines", Boolean.valueOf(true));
/* 216 */       cfg.addDefault("HiveJumpPads.Trampoline.3x3 Field", Boolean.valueOf(true));
/* 217 */       cfg.addDefault("HiveJumpPads.Trampoline.BlockID", Integer.valueOf(152));
/* 218 */       cfg.addDefault("HiveJumpPads.Trampoline.PressurePlateID", Integer.valueOf(147));
/* 219 */       cfg.addDefault("HiveJumpPads.Height.JumpHeight", Double.valueOf(10.0D));
/* 220 */       cfg.addDefault("HiveJumpPads.Sounds & Effects.Enable Sounds", Boolean.valueOf(false));
/* 221 */       cfg.addDefault("HiveJumpPads.Sounds & Effects.Enable Effects", Boolean.valueOf(true));
/* 222 */       cfg.addDefault("HiveJumpPads.Sounds & Effects.Enable message", Boolean.valueOf(false));
/* 223 */       cfg.addDefault("HiveJumpPads.Sounds & Effects.Sound", "WITHER_SHOOT");
/* 224 */       cfg.addDefault("HiveJumpPads.Sounds & Effects.Effect", "SMOKE");
/* 225 */       cfg.addDefault("HiveJumpPads.Sounds & Effects.message", "&aGET UP.");
/* 226 */       cfg.addDefault("HiveJumpPads.Trampolines.DisabledWorlds", new String[] {
/* 227 */         "World 1", 
/* 228 */         "World 2", 
/* 229 */         "World 3" });
/*     */       
/*     */ 
/* 232 */       cfg.options().copyDefaults(true);
/* 233 */       cfg.options().copyDefaults(true);
/*     */       try {
/* 235 */         cfg.save(f);
/*     */       } catch (IOException localIOException1) {}
/*     */     }
/* 238 */     try { cfg.save(f);
/*     */     } catch (IOException e) {
/* 240 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              M:\BENUTZER\Downloads\Hive_JumpPads_v.2.16.jar!\eu\Sendarox\HiveJumpPads\Configuration.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */