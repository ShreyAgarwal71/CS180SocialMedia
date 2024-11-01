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

	/**
	 * Wrapper method for the Collection interface's writeData method.
	 * This methods fills up Collection.writeData()'s parameters automatically.
	 * Returns true on success, false on fail.
	 * @return exitCode
	 */
	public boolean writeComments() {
		Object[] temp = new Object[comments.size()];
		comments.toArray(temp);
		return this.writeData(fileName, temp);
	}

	/**
	 * Returns the index of the first Comment with the provided commentID field
	 * from the comments ArrayList.
	 * Returns -1 if none of the Comment instances in the ArrayList has a 
	 * matching commentID field.
	 *
	 * @param commentID
	 * @return index
	 */
	public int indexOf(int commentID) {
		int index = -1;
		
		for (int i = 0; i < comments.size(); i++) {
			if (comments.get(i).getCommentID() == commentID) {
				index = i;
				break;
			}
		}

		return index;
	}
	
	@Override
	public int indexOf(Object obj) {
		int index = -1;
		if (!(obj instanceof Comment))
			return index;
		Comment c = (Comment) obj;

		for (int i = 0; i < comments.size(); i++) {
			if (comments.get(i).equals(c)) {
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

		if (index < comments.size() && index >= 0) {
			comments.set(index, (Comment) newObj);
			exitCode = this.writeComments();
		}
		
		return exitCode;
	}

	@Override
	public boolean updateElement(int index, Object newObj) {
		boolean exitCode = false;
		if (index >= comments.size() || index < 0 || !(newObj instanceof Comment))
			return exitCode;

		comments.set(index, (Comment) newObj);
		exitCode = this.writeComments();
			
		return exitCode;
	}

	@Override
	public boolean removeElement(Object obj) {
		boolean exitCode = false;

		int index = this.indexOf(obj);
		if (index == -1)
			return exitCode;

		if (index < comments.size() && index >= 0) {
			comments.remove(index);
			exitCode = this.writeComments();
		}

		return exitCode;
	}

	@Override
	public boolean removeElement(int index) {
		boolean exitCode = false;
		if (index >= comments.size() || index < 0)
			return exitCode;

		comments.remove(index);
		exitCode = this.writeComments();
			
		return exitCode;
	}
}
