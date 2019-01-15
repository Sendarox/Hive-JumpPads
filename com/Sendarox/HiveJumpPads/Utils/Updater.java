/**
 * HiveJumpPads, a Bukkit plugin to create 'Jump-Pads' on your Minecraft server.
 * Updater.java - automatic update-system
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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/** Updater.java - automatic update-system. */
public class Updater {

	private static final String TITLE_VALUE = "name";
    private static final String LINK_VALUE = "downloadUrl";
    private static final String TYPE_VALUE = "releaseType";
    private static final String VERSION_VALUE = "gameVersion";
    private static final String QUERY = "/servermods/files?projectIds=";
    private static final String HOST = "https://api.curseforge.com";
    private static final String USER_AGENT = "Updater (by Gravity)";
    private static final String DELIMETER = "^v|[\\s_-]v";
    private static final String[] NO_UPDATE_TAG = { "-DEV", "-PRE", "-SNAPSHOT" };
    private static final int BYTE_SIZE = 1024;
    private static final String API_KEY_DEFAULT = "API_KEY";

    private final Plugin plugin;
    private final UpdateType type;
    private final boolean announce;
    private final File file;
    private final File updateFolder;
    private final UpdateCallback callback;
    private int id = -1;
    private String apiKey = "apikey";

    private String versionName;
    private String versionLink;
    private String versionType;
    private String versionGameVersion;

    private URL url;
    private Thread thread;
    private Updater.UpdateResult result = Updater.UpdateResult.SUCCESS;

    
    public enum UpdateResult {
        
        SUCCESS,
        
        NO_UPDATE,
        
        DISABLED,
        
        FAIL_DOWNLOAD,
        
        FAIL_DBO,
        
        FAIL_NOVERSION,
        
        FAIL_BADID,
        
        FAIL_APIKEY,
        
        UPDATE_AVAILABLE
    }

    
    public enum UpdateType {
        
        DEFAULT,
        
        NO_VERSION_CHECK,
        
        NO_DOWNLOAD
    }

    
    public enum ReleaseType {
        
        ALPHA,
        
        BETA,
        
        RELEASE
    }

    
    public Updater(Plugin plugin, int id, File file, UpdateType type, boolean announce) {
        this(plugin, id, file, type, null, announce);
    }

    
    public Updater(Plugin plugin, int id, File file, UpdateType type, UpdateCallback callback) {
        this(plugin, id, file, type, callback, false);
    }

    
    public Updater(Plugin plugin, int id, File file, UpdateType type, UpdateCallback callback, boolean announce) {
        this.plugin = plugin;
        this.type = type;
        this.announce = announce;
        this.file = file;
        this.id = id;
        this.updateFolder = this.plugin.getServer().getUpdateFolderFile();
        this.callback = callback;

        String key = API_KEY_DEFAULT;
        if (API_KEY_DEFAULT.equalsIgnoreCase(key) || "".equals(key)) {
            key = null;
        }

        this.apiKey = key;
        
        try {
            this.url = new URL(Updater.HOST + Updater.QUERY + this.id);
        } catch (final MalformedURLException e) {
            this.plugin.getLogger().log(Level.SEVERE, "The project ID provided for updating, " + this.id + " is invalid.", e);
            this.result = UpdateResult.FAIL_BADID;
        }

        if (this.result != UpdateResult.FAIL_BADID) {
            this.thread = new Thread(new UpdateRunnable());
            this.thread.start();
        } else {
            runUpdater();
        }
    }

    
    public Updater.UpdateResult getResult() {
        this.waitForThread();
        return this.result;
    }

    
    public ReleaseType getLatestType() {
        this.waitForThread();
        if (this.versionType != null) {
            for (ReleaseType type : ReleaseType.values()) {
                if (this.versionType.equalsIgnoreCase(type.name())) {
                    return type;
                }
            }
        }
        return null;
    }

    
    public String getLatestGameVersion() {
        this.waitForThread();
        return this.versionGameVersion;
    }

    
    public String getLatestName() {
        this.waitForThread();
        return this.versionName;
    }

    
    public String getLatestFileLink() {
        this.waitForThread();
        return this.versionLink;
    }

    
    private void waitForThread() {
        if ((this.thread != null) && this.thread.isAlive()) {
            try {
                this.thread.join();
            } catch (final InterruptedException e) {
                this.plugin.getLogger().log(Level.SEVERE, null, e);
            }
        }
    }

    
    private void saveFile(String file) {
        final File folder = this.updateFolder;

        deleteOldFiles();
        if (!folder.exists()) {
            this.fileIOOrError(folder, folder.mkdir(), true);
        }
        downloadFile();

        final File dFile = new File(folder.getAbsolutePath(), file);
        if (dFile.getName().endsWith(".zip")) {
            this.unzip(dFile.getAbsolutePath());
        }
        if (this.announce) {
            this.plugin.getLogger().info("Finished updating.");
        }
    }

    
    private void downloadFile() {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            URL fileUrl = followRedirects(this.versionLink);
            final int fileLength = fileUrl.openConnection().getContentLength();
            in = new BufferedInputStream(fileUrl.openStream());
            fout = new FileOutputStream(new File(this.updateFolder, file.getName()));

            final byte[] data = new byte[Updater.BYTE_SIZE];
            int count;
            if (this.announce) {
                this.plugin.getLogger().info("About to download a new update: " + this.versionName);
            }
            long downloaded = 0;
            while ((count = in.read(data, 0, Updater.BYTE_SIZE)) != -1) {
                downloaded += count;
                fout.write(data, 0, count);
                final int percent = (int) ((downloaded * 100) / fileLength);
                if (this.announce && ((percent % 10) == 0)) {
                    this.plugin.getLogger().info("Downloading update: " + percent + "% of " + fileLength + " bytes.");
                }
            }
        } catch (Exception ex) {
            this.plugin.getLogger().log(Level.WARNING, "The auto-updater tried to download a new update, but was unsuccessful.", ex);
            this.result = Updater.UpdateResult.FAIL_DOWNLOAD;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException ex) {
                this.plugin.getLogger().log(Level.SEVERE, null, ex);
            }
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (final IOException ex) {
                this.plugin.getLogger().log(Level.SEVERE, null, ex);
            }
        }
    }

    private URL followRedirects(String location) throws IOException {
        URL resourceUrl, base, next;
        HttpURLConnection conn;
        String redLoc;
        while (true) {
            resourceUrl = new URL(location);
            conn = (HttpURLConnection) resourceUrl.openConnection();

            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0...");

            switch (conn.getResponseCode()) {
                case HttpURLConnection.HTTP_MOVED_PERM:
                case HttpURLConnection.HTTP_MOVED_TEMP:
                    redLoc = conn.getHeaderField("Location");
                    base = new URL(location);
                    next = new URL(base, redLoc);  // Deal with relative URLs
                    location = next.toExternalForm();
                    continue;
            }
            break;
        }
        return conn.getURL();
    }

    
    private void deleteOldFiles() {
        File[] list = listFilesOrError(this.updateFolder);
        for (final File xFile : list) {
            if (xFile.getName().endsWith(".zip")) {
                this.fileIOOrError(xFile, xFile.mkdir(), true);
            }
        }
    }

    
    private void unzip(String file) {
        final File fSourceZip = new File(file);
        try {
            final String zipPath = file.substring(0, file.length() - 4);
            ZipFile zipFile = new ZipFile(fSourceZip);
            Enumeration<? extends ZipEntry> e = zipFile.entries();
            while (e.hasMoreElements()) {
                ZipEntry entry = e.nextElement();
                File destinationFilePath = new File(zipPath, entry.getName());
                this.fileIOOrError(destinationFilePath.getParentFile(), destinationFilePath.getParentFile().mkdirs(), true);
                if (!entry.isDirectory()) {
                    final BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                    int b;
                    final byte[] buffer = new byte[Updater.BYTE_SIZE];
                    final FileOutputStream fos = new FileOutputStream(destinationFilePath);
                    final BufferedOutputStream bos = new BufferedOutputStream(fos, Updater.BYTE_SIZE);
                    while ((b = bis.read(buffer, 0, Updater.BYTE_SIZE)) != -1) {
                        bos.write(buffer, 0, b);
                    }
                    bos.flush();
                    bos.close();
                    bis.close();
                    final String name = destinationFilePath.getName();
                    if (name.endsWith(".jar") && this.pluginExists(name)) {
                        File output = new File(this.updateFolder, name);
                        this.fileIOOrError(output, destinationFilePath.renameTo(output), true);
                    }
                }
            }
            zipFile.close();
            moveNewZipFiles(zipPath);

        } catch (final IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "The auto-updater tried to unzip a new update file, but was unsuccessful.", e);
            this.result = Updater.UpdateResult.FAIL_DOWNLOAD;
        } finally {
            this.fileIOOrError(fSourceZip, fSourceZip.delete(), false);
        }
    }

    
    private void moveNewZipFiles(String zipPath) {
        File[] list = listFilesOrError(new File(zipPath));
        for (final File dFile : list) {
            if (dFile.isDirectory() && this.pluginExists(dFile.getName())) {
                final File oFile = new File(this.plugin.getDataFolder().getParent(), dFile.getName());
                final File[] dList = listFilesOrError(dFile);
                final File[] oList = listFilesOrError(oFile);
                for (File cFile : dList) {
                    boolean found = false;
                    for (final File xFile : oList) {
                        if (xFile.getName().equals(cFile.getName())) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        File output = new File(oFile, cFile.getName());
                        this.fileIOOrError(output, cFile.renameTo(output), true);
                    } else {
                        this.fileIOOrError(cFile, cFile.delete(), false);
                    }
                }
            }
            this.fileIOOrError(dFile, dFile.delete(), false);
        }
        File zip = new File(zipPath);
        this.fileIOOrError(zip, zip.delete(), false);
    }

    
    private boolean pluginExists(String name) {
        File[] plugins = listFilesOrError(new File("plugins"));
        for (final File file : plugins) {
            if (file.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    
    private boolean versionCheck() {
        final String title = this.versionName;
        if (this.type != UpdateType.NO_VERSION_CHECK) {
            final String localVersion = this.plugin.getDescription().getVersion();
            if (title.split(DELIMETER).length >= 2) {
                final String remoteVersion = title.split(DELIMETER)[title.split(DELIMETER).length - 1].split(" ")[0];

                if (this.hasTag(localVersion) || !this.shouldUpdate(localVersion, remoteVersion)) {
                    this.result = Updater.UpdateResult.NO_UPDATE;
                    return false;
                }
            } else {
                final String authorInfo = this.plugin.getDescription().getAuthors().isEmpty() ? "" : " (" + this.plugin.getDescription().getAuthors().get(0) + ")";
                this.plugin.getLogger().warning("The author of this plugin" + authorInfo + " has misconfigured their Auto Update system");
                this.plugin.getLogger().warning("File versions should follow the format 'PluginName vVERSION'");
                this.plugin.getLogger().warning("Please notify the author of this error.");
                this.result = Updater.UpdateResult.FAIL_NOVERSION;
                return false;
            }
        }
        return true;
    }

    
    public boolean shouldUpdate(String localVersion, String remoteVersion) {
        return !localVersion.equalsIgnoreCase(remoteVersion);
    }

    
    private boolean hasTag(String version) {
        for (final String string : Updater.NO_UPDATE_TAG) {
            if (version.contains(string)) {
                return true;
            }
        }
        return false;
    }

    
    private boolean read() {
        try {
            final URLConnection conn = this.url.openConnection();
            conn.setConnectTimeout(5000);

            if (this.apiKey != null) {
                conn.addRequestProperty("X-API-Key", this.apiKey);
            }
            conn.addRequestProperty("User-Agent", Updater.USER_AGENT);

            conn.setDoOutput(true);

            final BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final String response = reader.readLine();

            final JSONArray array = (JSONArray) JSONValue.parse(response);

            if (array.isEmpty()) {
                this.plugin.getLogger().warning("The updater could not find any files for the project id " + this.id);
                this.result = UpdateResult.FAIL_BADID;
                return false;
            }

            JSONObject latestUpdate = (JSONObject) array.get(array.size() - 1);
            this.versionName = (String) latestUpdate.get(Updater.TITLE_VALUE);
            this.versionLink = (String) latestUpdate.get(Updater.LINK_VALUE);
            this.versionType = (String) latestUpdate.get(Updater.TYPE_VALUE);
            this.versionGameVersion = (String) latestUpdate.get(Updater.VERSION_VALUE);

            return true;
        } catch (final IOException e) {
            if (e.getMessage().contains("HTTP response code: 403")) {
                this.plugin.getLogger().severe("dev.bukkit.org rejected the API key provided in plugins/Updater/config.yml");
                this.plugin.getLogger().severe("Please double-check your configuration to ensure it is correct.");
                this.result = UpdateResult.FAIL_APIKEY;
            } else {
                this.plugin.getLogger().severe("The updater could not contact dev.bukkit.org for updating.");
                this.plugin.getLogger().severe("If you have not recently modified your configuration and this is the first time you are seeing this message, the site may be experiencing temporary downtime.");
                this.result = UpdateResult.FAIL_DBO;
            }
            this.plugin.getLogger().log(Level.SEVERE, null, e);
            return false;
        }
    }

    
    private void fileIOOrError(File file, boolean result, boolean create) {
        if (!result) {
            this.plugin.getLogger().severe("The updater could not " + (create ? "create" : "delete") + " file at: " + file.getAbsolutePath());
        }
    }

    private File[] listFilesOrError(File folder) {
        File[] contents = folder.listFiles();
        if (contents == null) {
            this.plugin.getLogger().severe("The updater could not access files at: " + this.updateFolder.getAbsolutePath());
            return new File[0];
        } else {
            return contents;
        }
    }

    
    public interface UpdateCallback {
        
        void onFinish(Updater updater);
    }

    private class UpdateRunnable implements Runnable {
        @Override
        public void run() {
            runUpdater();
        }
    }

    private void runUpdater() {
        if (this.url != null && (this.read() && this.versionCheck())) {
            if ((this.versionLink != null) && (this.type != UpdateType.NO_DOWNLOAD)) {
                String name = this.file.getName();
                if (this.versionLink.endsWith(".zip")) {
                    name = this.versionLink.substring(this.versionLink.lastIndexOf("/") + 1);
                }
                this.saveFile(name);
            } else {
                this.result = UpdateResult.UPDATE_AVAILABLE;
            }
        }

        if (this.callback != null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    runCallback();
                }
            }.runTask(this.plugin);
        }
    }

    private void runCallback() {
        this.callback.onFinish(this);
    }
	
}
