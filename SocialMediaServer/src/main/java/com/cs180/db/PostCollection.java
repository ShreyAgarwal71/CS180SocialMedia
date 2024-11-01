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

	/**
	 * Wrapper method for the Collection interface's writeData method.
	 * This methods fills up Collection.writeData()'s parameters automatically.
	 * Returns true on success, false on fail.
	 * @return exitCode
	 */
	public boolean writePosts() {
		Object[] temp = new Object[posts.size()];
		posts.toArray(temp);
		return this.writeData(fileName, temp);
	}
	
	/**
	 * Returns the index of the first Post with the provided postID field
	 * from the posts ArrayList.
	 * Returns -1 if none of the Post instances in the ArrayList has a 
	 * matching postID field.
	 *
	 * @param postID
	 * @return index
	 */
	public int indexOf(int postID) {
		int index = -1;
		
		for (int i = 0; i < posts.size(); i++) {
			if (posts.get(i).getPostID() == postID) {
				index = i;
				break;
			}
		}

		return index;
	}

	@Override
	public int indexOf(Object obj) {
		int index = -1;
		if (!(obj instanceof Post))
			return index;
		Post p = (Post) obj;

		for (int i = 0; i < posts.size(); i++) {
			if (posts.get(i).equals(p)) {
				index = i;
				break;
			}
		}
		
		return index;
	}

	@Override
	public boolean updateElement(Object target, Object newObj) {
		boolean exitCode = false;

		int index = this.indexOf(target);
		if (index == -1)
			return exitCode;

		if (index < posts.size() && index >= 0) {
			posts.set(index, (Post) newObj);
			exitCode = this.writePosts();
		}
		
		return exitCode;
	}

	@Override
	public boolean updateElement(int index, Object newObj) {
		boolean exitCode = false;
		if (index >= posts.size() || index < 0 || !(newObj instanceof Post))
			return exitCode;

		posts.set(index, (Post) newObj);
		exitCode = this.writePosts();
			
		return exitCode;
	}

	@Override
	public boolean removeElement(Object obj) {
		boolean exitCode = false;

		int index = this.indexOf(obj);
		if (index == -1)
			return exitCode;

		if (index < posts.size() && index >= 0) {
			posts.remove(index);
			exitCode = this.writePosts();
		}

		return exitCode;
	}

	@Override
	public boolean removeElement(int index) {
		boolean exitCode = false;
		if (index >= posts.size() || index < 0)
			return exitCode;

		posts.remove(index);
		exitCode = this.writePosts();
			
		return exitCode;
	}
}
