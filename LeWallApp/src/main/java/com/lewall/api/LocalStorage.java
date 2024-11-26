package com.lewall.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cloudinary.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lewall.App;
import com.lewall.dtos.UserDTO;

/**
 * Class to manage client-side storage in the application
 *
 * @Author Ates Isfendiyaroglu and Mahit Mehta
 * @version 16 November 2024
 *
 */
public class LocalStorage {
	private static Logger logger = LogManager.getLogger(LocalStorage.class);
	private static HashMap<String, String> storage;

    private static final GsonBuilder builder = new GsonBuilder();
    private static final Gson gson = builder.create();

	private static final String FILE_NAME = "state.ser";

	private static final AtomicBoolean HAS_BEEN_INITIALIZED = new AtomicBoolean(false);

	private static final ScheduledThreadPoolExecutor SCHEDULER = new ScheduledThreadPoolExecutor(3);
	private static final AtomicBoolean NEED_WRITE = new AtomicBoolean(false);
	private static final int ASYNC_WRITE_FREQ = 5;

	public static void init() {
		loadFromDisk();

		if (HAS_BEEN_INITIALIZED.compareAndSet(false, true)) {
			Runtime.getRuntime().addShutdownHook((new Thread() {
				public void run() {
					logger.info("Saving data");
					saveToDisk();
					logger.info("Saved Data");
				}
			}));

			SCHEDULER.scheduleAtFixedRate(() -> {
				if (!NEED_WRITE.get())
					return;

				saveToDisk();
				NEED_WRITE.set(false);
			}, 0, ASYNC_WRITE_FREQ, java.util.concurrent.TimeUnit.SECONDS);
		}
	}

	/**
	 * Reads data from the disk if file exists
	 *
	 * @return exitCode
	 */
	@SuppressWarnings("unchecked")
	private static boolean loadFromDisk() {
		Path basePath = getOSDataBasePath();
		String absoluteFilePath = basePath.resolve(FILE_NAME).toString();

		File file = new File(absoluteFilePath);
		if (!file.exists()) {
			storage = new HashMap<>();
			return true;
		}

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			storage = (HashMap<String, String>) ois.readObject();
		} catch (Exception e) {
			logger.error(e);
			return false;
		}

		return true;
	}

	/**
	 * Saves current state to disk
	 *
	 * @return exitCode
	 */
	private static boolean saveToDisk() {
		if (!HAS_BEEN_INITIALIZED.get() || storage == null) {
			logger.error("Cannot save unititialized client side data!");
			return false;
		}

		Path basePath = getOSDataBasePath();
		String absoluteFilePath = basePath.resolve(FILE_NAME).toString();
		File file = new File(absoluteFilePath);

		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				logger.debug("Creating parent directories for file: " + absoluteFilePath);
				file.getParentFile().mkdirs();
			}

			logger.debug("Will create new file: " + absoluteFilePath);
		}

		try (ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream(file))) {
			ous.writeObject(storage);
		} catch (Exception e) {
			logger.error(e);
			return false;
		}

		return true;
	}

	/**
	 * Adds the inputted key-value pair to the storage HasMap
	 *
	 * @param key
	 * 
	 * @param val
	 */
	public static String set(String key, String val) {
		NEED_WRITE.set(true);
		return storage.put(key, val);
	}

	public static String get(String key) {
		return storage.get(key);
	}

	/**
	 * Fethes the requested data from the storage HashMap
	 *
	 * @return value
	 */
	public static <T> T get(String key, Class<T> type) {
		String serializedJSON = storage.get(key);	
		if (serializedJSON == null) {
			return null;
		}

		try {
			return gson.fromJson(serializedJSON, type);
		} catch (JSONException e) {
			logger.error("Type (" + type.getName() + ") mismatch for key: " + key);
			return null;
		}
	}

	/**
	 * Removes the requested data from the storage HashMap
	 * 
	 */
	public static void clear() {
		NEED_WRITE.set(true);
		storage.clear();
	}

	/**
	 * Removes the requested data from the storage HashMap
	 * 
	 * @return
	 */
	public static String remove(String key) {
		NEED_WRITE.set(true);
		return storage.remove(key);
	}

	/**
	 * s
	 * Gets the storage file location based on the OS
	 * 
	 * @return path
	 */
	private static Path getOSDataBasePath() {
		String os = System.getProperty("os.name").toLowerCase();

		if (os.contains("win")) {
			return Paths.get(System.getenv("APPDATA"), App.ID);
		} else if (os.contains("mac")) {
			return Paths.get(System.getProperty("user.home"), "/Library/Application Support", App.ID);
		} else if (os.contains("nix") || os.contains("nux")) {
			return Paths.get(System.getProperty("user.home"), String.format(".%s", App.ID.toLowerCase()));
		} else {
			return Paths.get(System.getProperty("user.home"), App.ID);
		}
	}
}
