/**
 * HiveJumpPads, a Bukkit plugin to create 'Jump-Pads' on your Minecraft server.
 * Language.java - HiveJumpPads Language Handler.
 * Copyright (C) 2013-2019 Sendarox 
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 **/

package com.Sendarox.HiveJumpPads.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.logging.Level;

import com.Sendarox.HiveJumpPads.HiveJumpPads;
import com.Sendarox.HiveJumpPads.Utils.Handler.TranslateAlternateColors;

/** Language.java - Language Handler. */
public class Language {

	private static HashMap<String, String> _langCache = new HashMap<String, String>();
	private String _lang;
	private File _langFile;
	
	private String _plg_prefix;
	private String _plg_bprefix;
	private String _plg_cprefix;
	
	/** Language-Class constructor. Builds the base from the given language-string (_lang).
	 *  @param _lang Sets the language to initialize. (e.g. 'en_us.lang' -> 'en_us'). **/
	public Language(String _lang) {
		this._lang 		  = _lang;
		File _langFile	  = new File("plugins/HiveJumpPads/lang/"+_lang+".lang");
		
		if(!_langFile.exists()) {
			HiveJumpPads._hjp_logger.log(Level.SEVERE, "[HiveJumpPads] WOOOPS! Failed while loading your language file!"
					+ "\n\nIt looks like it's missing!"
					+ "\n-> Please ensure that it's located in 'plugins/HiveJumpPads/lang/xx_xx.lang'!"
					+ "\n-> Disabling HiveJumpPads!");
			
			return;
		} else {
			HiveJumpPads._hjp_logger.log(Level.INFO, "[HiveJumpPads] Your current language is '"+this.getLanguage()+"'!");
			this._langFile    = _langFile;
			
			this._plg_prefix  = getLanguageContent("hjp.general.prefix");
			this._plg_cprefix = getLanguageContent("hjp.general.cprefix");
			this._plg_bprefix = getLanguageContent("hjp.general.bprefix");
		}
	}
	
	/** Gets the corresponding language string. 
	 *  @param _lId The key of which the value is about to be obtained. 
	 *  @return Returns the value of the given key. **/
	public String getLanguageContent(String _lId) {
		
		if(_langCache.containsKey(_lId)) {
			return _langCache.get(_lId);
		}
		
		try(BufferedReader _br = new BufferedReader(new FileReader(_langFile))){
			String _line;
			while((_line = _br.readLine()) != null) {
				if(_line.startsWith(_lId)) {
					_line = TranslateAlternateColors.translateColorCodes(_line);
					addEntryToCache(_line);
					_br.close();
					return _langCache.get(_lId);
				}
			}
		} catch(Exception _e) {
			_e.printStackTrace();
			return "#"+_lang+"#"+_lId+"=null;";
		}
		return "#"+_lang+"#"+_lId+"=null;";
	}
	
	/** Returns the currently used language. 
	 *  @return Returns the language that's currently in use. **/
	public String getLanguage() {
		return _lang;
	}
	
	/** Adds a Key-Value pair to the Cache-Map. 
	 *  @param _langLine The whole line from the language file. (e.g. 'x.y.z=abcd'). **/
	private void addEntryToCache(String _langLine) {
		String[] _kvPair = _langLine.split("=");
		String   _cStrg  = replaceStringPlaceholder(_kvPair[1]);
		_langCache.put(_kvPair[0], _cStrg);
	}
	
	/** Replaces the placeholders within the obtained language strings. 
	 *  @param _langValue The value of a language line. (e.g. 'Current version %ver%'). 
	 *  @return Returns the translated String. **/
	private String replaceStringPlaceholder(String _langValue) {
		if(_langValue.contains("%ver%")) {
			_langValue = _langValue.replace("%ver%", HiveJumpPads.getDescriptionFile().getVersion());
		}
		if(_langValue.contains("%plugin_name%")) {
			_langValue = _langValue.replace("%plugin_name%", HiveJumpPads.getDescriptionFile().getName());
		}
		if(_langValue.contains("%cprefix%")) {
			_langValue = _langValue.replace("%cprefix%", _plg_cprefix);
		}
		if(_langValue.contains("%bprefix%")) {
			_langValue = _langValue.replace("%bprefix%", _plg_bprefix);
		}
		if(_langValue.contains("%prefix%")) {
			_langValue = _langValue.replace("%prefix%", _plg_prefix);
		}
		return _langValue;
	}
	
	/** Clears the Language Message Cache. **/
	public void clearLanguageCache() {
		_langCache.clear();
	}
	
}
