package com.lewall.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

/*
 * Class to manage client-side storage in the application
 *
 * @Author Ates Isfendiyaroglu, L17
 * @version 14 November 2024
 *
 */
public class ClientSideStorage {
	private static Logger logger = LogManager.getLogger(ClientSideStorage.class);
	private static HashMap<String, String> storage;

    private static final Gson gson = new GsonBuilder().create();

	/*
	 * Reads data from the disk
	 *
	 * @return exitCode
	 */
	@SuppressWarnings("unchecked")
	public boolean readFromDisk() {
		String path = getOSDataBasePath().toString();
		
		File f = new File(path);
		if (!f.exists()){
			try {
				storage = new HashMap<>();
				f.createNewFile();
			} catch (IOException e) {
				logger.error(e);
				return false;
			}
		} else {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
				// TODO: Test this!
				storage = (HashMap<String, String>) ois.readObject();
			} catch (Exception e) {
				logger.error(e);
				return false;
			}
		}

		return true;
	}

	/*
	 * Saves current state to disk
	 *
	 * @return exitCode
	 */
	public static boolean saveToDisk() {
		if (storage == null || storage.isEmpty()) {
			logger.error("Cannot save unititialized client side data!");
			return false;
		}

		String path = getOSDataBasePath().toString();
		
		File f = new File(path);
		if (!f.exists()){
			logger.error("Client side storage file does not exist!");
			return false;
		}

		try (ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream(f))) {
			ous.writeObject(storage);
		} catch (Exception e) {
			logger.error(e);
			return false;
		}

		return true;
	}
	
	/*
	 * Fetches the requested data from the storage HasMap in JSON format
	 *
	 * @param key
	 * @return val
	 */
	public static String asJson(String key) {
		String val = storage.get(key);

		if (val == null) {
			logger.error("Invalid Key \"%s\" inputted to the Client-Side storage Hashmap!", key);
		} else {
			logger.info("Returned: \"%s\" from the Client-Side storage HasMap.");
			val = gson.toJson(val);
		}

		return val;
	}

	/*
	 * Gets the storage file location based on the OS
	 * @return path
	 */
	private static Path getOSDataBasePath() {
		String os = System.getProperty("os.name").toLowerCase();

		if (os.contains("win")) {
			return Paths.get(System.getenv("APPDATA"), "LeWall", "data.txt");
		} else if (os.contains("mac")) {
			return Paths.get(System.getProperty("user.home"), "/Library/Application Support", "LeWall", "data.txt");
		} else if (os.contains("nix") || os.contains("nux")) {
			return Paths.get(System.getProperty("user.home"), ".lewall", "data.txt");
		} else {
			return Paths.get(System.getProperty("user.home"), "LeWall", "data.txt");
		}
	}
}

