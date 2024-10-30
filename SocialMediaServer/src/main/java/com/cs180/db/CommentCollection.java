package com.cs180.db;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A Collection class to manage comments
 * 
 * @author Ates Isfendiyaroglu, L17
 *
 * @version 30 October 2024
 */
public class CommentCollection implements Collection {
	private final String fileName;
	private ArrayList<Comment> comments;

	public CommentCollection(String fileName) {
		this.fileName = fileName;
		Object[] o = this.readData(fileName);
		comments = new ArrayList<>(Arrays.asList((Comment[]) o));
	}

	// Wrapper for writeData for easier use
	public boolean writePosts() {
		Object[] temp = new Object[comments.size()];
		comments.toArray(temp);
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
