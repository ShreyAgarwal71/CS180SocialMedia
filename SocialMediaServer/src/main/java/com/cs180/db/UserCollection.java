package com.cs180.db;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A Collection class to manage users
 * 
 * @author Ates Isfendiyaroglu, L17
 *
 * @version 30 October 2024
 */
public class UserCollection implements Collection {
	private final String fileName;
	private ArrayList<User> users;

	public UserCollection(String fileName) {
		this.fileName = fileName;
		Object[] o = this.readData(fileName);
		users = new ArrayList<>(Arrays.asList((User[]) o));
	}

	// Wrapper for writeData for easier use
	public boolean writePosts() {
		Object[] temp = new Object[users.size()];
		users.toArray(temp);
		return this.writeData(fileName, temp);
	}

	@Override
	public Object[] readData(String fileName) {
		return Collection.super.readData(fileName);
	}

	@Override
	public boolean writeData(String fileName, Object[] data) {
		return Collection.super.writeData(fileName, data);
	}
}
