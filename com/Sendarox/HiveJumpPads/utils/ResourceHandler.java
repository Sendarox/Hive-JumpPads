package com.Sendarox.HiveJumpPads.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ResourceHandler {

	public String ExportResource(String resourceName, String directory) throws Exception {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        String jarFolder;
        try {
            stream = ResourceHandler.class.getResourceAsStream(resourceName);
            if(stream == null) {
                throw new Exception("[HiveJumpPads] Failed to export resource \"" + resourceName + "\" from .jar file.");
            }
            int readBytes;
            byte[] buffer = new byte[4096];
            jarFolder = new File(ResourceHandler.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
            resStreamOut = new FileOutputStream(jarFolder + directory + resourceName.replace("/config_template", ""));
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            stream.close();
            resStreamOut.close();
        }
        return jarFolder + directory + resourceName;
    }
	
}
