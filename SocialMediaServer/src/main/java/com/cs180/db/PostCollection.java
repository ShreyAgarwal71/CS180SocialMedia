package com.cs180.db;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A Collection class to manage posts
 * 
 * @author Ates Isfendiyaroglu, L17
 *
 * @version 30 October 2024
 */
public class PostCollection implements Collection {
	private final String fileName;
	private ArrayList<Post> posts;

	public PostCollection(String fileName) {
		this.fileName = fileName;
		Object[] o = this.readData(fileName);
		posts = new ArrayList<>(Arrays.asList((Post[]) o));
	}

	// Wrapper for writeData for easier use
	public boolean writePosts() {
		Object[] temp = new Object[posts.size()];
		posts.toArray(temp);
		return this.writeData(fileName, temp);
	}
}
