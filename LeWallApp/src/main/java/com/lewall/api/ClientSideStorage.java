package com.lewall.api;

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
	private static HashMap<String, String> storage = new HashMap<>();

    private static final Gson gson = new GsonBuilder().create();

	/*
	 * TODO: Load data if available, else initialize new stuff if
	 */
	public ClientSideStorage() {

	}

	/*
	 * TODO: Save state to disk
	 * @return exitCode
	 */
	public boolean saveToDisk() {
		return false;
	}
	
	/*
	 * Fetches the requested data from the storage HasMap in JSON format
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
}

