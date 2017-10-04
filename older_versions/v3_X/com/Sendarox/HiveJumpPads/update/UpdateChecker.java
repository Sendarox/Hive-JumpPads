package com.Sendarox.HiveJumpPads.update;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.Sendarox.HiveJumpPads.HiveJumpPads;

public class UpdateChecker {

	/**
	 * 
	 * Check for updates on BukkitDev for a given plugin.
	 * <p>
	 * <b>DEPRECATED</b>: This update checker is deprecated!
	 * <br>
	 * @author Sendarox
	 * @version 1.1
	 */
	
	private HiveJumpPads hjp;
	private URL filesFeed;
	
	private String v;
	@SuppressWarnings("unused")
	private String link;
	
	@Deprecated
	public UpdateChecker(HiveJumpPads hjp, String url) {
		this.hjp = hjp;
		try {
			this.filesFeed = new URL(url);
		} catch (MalformedURLException e){
			e.printStackTrace();
		}
	}
	
	@Deprecated
	public boolean updateIsAvailable() throws Exception {
		InputStream is = this.filesFeed.openConnection().getInputStream();
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
		
		Node latestFile = doc.getElementsByTagName("item").item(0);
		NodeList children = latestFile.getChildNodes();
		
		this.v = children.item(1).getTextContent().replaceAll("[a-zA-Z +|]", "");
		this.link = children.item(3).getTextContent();
		
		if(!hjp.getDescription().getVersion().equals(this.v)){
			return true;
		}
		return false;
	}
	
	@Deprecated
	public String getVersion(){
		return this.v;
	}
	
	@Deprecated
	public String getLink(){
		return this.getLink();
	}
}
