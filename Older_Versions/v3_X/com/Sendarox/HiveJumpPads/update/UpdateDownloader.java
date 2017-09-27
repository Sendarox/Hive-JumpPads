package com.Sendarox.HiveJumpPads.update;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class UpdateDownloader {
	
	/**
	 * 
	 * Check for updates on BukkitDev for a given plugin.
	 * <p>
	 * <b>DEPRECATED</b>: This update checker is deprecated!
	 * <br>
	 * @author Sendarox
	 * @version 1.0
	 */
	
	private String url;
	private String file;
	
	@Deprecated
	public UpdateDownloader(String url, String file) {
		this.url = url;
		this.file = file;
	}
	
	@Deprecated
	public boolean download() { 
		try {
			URL url = new URL(this.url);
			ReadableByteChannel rbc = Channels.newChannel(url.openStream());
			FileOutputStream fos = new FileOutputStream(this.file);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
			rbc.close();
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	@Deprecated
	public String link(){
		return this.url;
	}
	
	@Deprecated
	public String file(){
		return this.file;
	}
}
