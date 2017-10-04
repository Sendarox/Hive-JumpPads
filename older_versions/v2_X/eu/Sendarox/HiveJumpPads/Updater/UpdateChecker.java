/*    */ package eu.Sendarox.HiveJumpPads.Updater;
/*    */ 
/*    */ import eu.Sendarox.HiveJumpPads.HiveJumpPads;
/*    */ import java.io.InputStream;
/*    */ import java.io.PrintStream;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import javax.xml.parsers.DocumentBuilder;
/*    */ import javax.xml.parsers.DocumentBuilderFactory;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.PluginDescriptionFile;
/*    */ import org.w3c.dom.Document;
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.NodeList;
/*    */ 
/*    */ public class UpdateChecker
/*    */ {
/*    */   private HiveJumpPads hjp;
/*    */   private URL filesFeed;
/*    */   private String version;
/*    */   private String version2;
/*    */   private String link;
/*    */   
/*    */   public UpdateChecker(HiveJumpPads hjp, String url)
/*    */   {
/* 27 */     this.hjp = hjp;
/*    */     try
/*    */     {
/* 30 */       this.filesFeed = new URL(url);
/*    */     } catch (MalformedURLException e) {
/* 32 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean updateNeeded(Player p) {
/*    */     try {
/* 38 */       InputStream input = this.filesFeed.openConnection().getInputStream();
/* 39 */       Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
/*    */       
/* 41 */       Node latestFile = document.getElementsByTagName("item").item(0);
/* 42 */       NodeList children = latestFile.getChildNodes();
/*    */       
/* 44 */       this.version = children.item(1).getTextContent().replaceAll("[a-zA-Z +|]", "");
/* 45 */       this.link = children.item(3).getTextContent();
/*    */       
/* 47 */       if (!this.hjp.getDescription().getVersion().equals(this.version)) {
/* 48 */         p.sendMessage(" ");
/* 49 */         p.sendMessage("§8---------------------------------------------------");
/* 50 */         p.sendMessage("§a[§6Hive JumpPads§a]§7 A new version of Hive JumpPads is out");
/* 51 */         p.sendMessage("§a[§6Hive JumpPads§a]§c " + this.version + "§7 | §c" + this.link);
/* 52 */         p.sendMessage("§8---------------------------------------------------");
/* 53 */         p.sendMessage(" ");
/* 54 */         return true;
/*    */       }
/*    */     }
/*    */     catch (Exception e) {
/* 58 */       e.printStackTrace();
/*    */     }
/*    */     
/* 61 */     return false;
/*    */   }
/*    */   
/*    */   public boolean updateConsole()
/*    */   {
/*    */     try {
/* 67 */       InputStream input = this.filesFeed.openConnection().getInputStream();
/* 68 */       Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
/*    */       
/* 70 */       Node latestFile = doc.getElementsByTagName("item").item(0);
/* 71 */       NodeList children = latestFile.getChildNodes();
/*    */       
/* 73 */       this.version2 = children.item(1).getTextContent().replace("[a-zA-Z +|]", "");
/* 74 */       this.link = children.item(3).getTextContent();
/*    */       
/* 76 */       if (!this.hjp.getDescription().getVersion().equals(this.version2)) {
/* 77 */         System.out.println(eu.Sendarox.HiveJumpPads.Variables.Strings.HJP_CONSOLE + " A new version of HJP [" + this.version + "] is available");
/* 78 */         return true;
/*    */       }
/*    */     }
/*    */     catch (Exception e) {
/* 82 */       e.printStackTrace();
/*    */     }
/* 84 */     return false;
/*    */   }
/*    */   
/*    */   public String getVersion() {
/* 88 */     return this.version;
/*    */   }
/*    */   
/*    */   public String getLink() {
/* 92 */     return getLink();
/*    */   }
/*    */ }


/* Location:              M:\BENUTZER\Downloads\Hive_JumpPads_v.2.16.jar!\eu\Sendarox\HiveJumpPads\Updater\UpdateChecker.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */