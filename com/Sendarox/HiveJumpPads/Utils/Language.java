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
 **/

package com.Sendarox.HiveJumpPads.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

/** Language.java - Language Handler. */
public class Language {

	private static HashMap<String, String> _langCache = new HashMap<String, String>();
	private String _lang;
	private File _langFile;
	
	public Language(String _lang) {
		this._lang = _lang;
	
		File _langFile = new File("plugins/HiveJumpPads/lang/"+_lang+".lang");
		this._langFile = _langFile;
	}
	
	/** Gets the corresponding language string. **/
	public String getLanguageContent(String _lId) {
		if(_langCache.containsKey(_lId)) {
			return _langCache.get(_lId);
		}
		try(BufferedReader _br = new BufferedReader(new FileReader(_langFile))){
			String _line;
			while((_line = _br.readLine()) != null) {
				if(_line.startsWith(_lId)) {
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
	
	/** Returns the currently used language. **/
	public String getLanguage() {
		return _lang;
	}
	
	/** Adds a Key-Value pair to the Cache-Map. **/
	private void addEntryToCache(String _langLine) {
		String[] _kvPair = _langLine.split("=");
		_langCache.put(_kvPair[0], _kvPair[1]);
	}
	
	/** Clears the Language Message Cache. **/
	public void clearLanguageCache() {
		_langCache.clear();
	}
	
}
