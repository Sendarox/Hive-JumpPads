/**
 * HiveJumpPads, a Bukkit plugin to create 'Jump-Pads' on your Minecraft server.
 * ResourceHandler.java - {CLASS_DESCRIPTION}
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

package com.Sendarox.HiveJumpPads.Utils.Handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.Sendarox.HiveJumpPads.HiveJumpPads;

/** ResourceHandler.java - Resource exportation. */
public class ResourceHandler {

	/** Exports the given internal jar file to a system-file directory. 
	 *  @param _internal_jar_source Path to resource within the jar file.
	 *  @param _local_directory Path of the system-file directory.	**/
	public static String ExportResource(String _internal_jar_source, String _local_directory) throws Exception {
        
		InputStream	 	_in_stream	 = null;
        OutputStream 	_res_stream	 = null;
        String 		 	_jarDir;
       
        try {
            _in_stream = ResourceHandler.class.getResourceAsStream(_internal_jar_source);
            if(_in_stream == null) {
                throw new Exception(HiveJumpPads.getLanguage().getLanguageContent("hjp.exception.resource.failed").replace("%resource%", _internal_jar_source));
            }
            
            int    _rBytes;
            byte[] _buffer = new byte[4096];
            
            _jarDir 	= new File(ResourceHandler.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
            _res_stream = new FileOutputStream(_jarDir + _local_directory + _internal_jar_source.replace("/config_template", "").replace("/lang/", ""));
            
            while((_rBytes = _in_stream.read(_buffer)) > 0) {
                _res_stream.write(_buffer, 0, _rBytes);
            }
        } catch (Exception _e) {
        	throw _e;
        } finally {
            _in_stream.close();
            _res_stream.close();
        }
        
        return _jarDir+_local_directory+_internal_jar_source;
	}	
}
