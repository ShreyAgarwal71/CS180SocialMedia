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

	/**
	 * Wrapper method for the Collection interface's writeData method.
	 * This methods fills up Collection.writeData()'s parameters automatically.
	 * Returns true on success, false on fail.
	 * @return exitCode
	 */
	public boolean writeUsers() {
		Object[] temp = new Object[users.size()];
		users.toArray(temp);
		return this.writeData(fileName, temp);
	}

	/**
	 * Returns the index of the first User with the provided username field
	 * from the users ArrayList.
	 * Returns -1 if none of the User instances in the ArrayList has a 
	 * matching username field.
	 *
	 * @param username
	 * @return index
	 */
	public int indexOf(String username) {
		int index = -1;
		
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equals(username)) {
				index = i;
				break;
			}
		}

		return index;
	}
	
	@Override
	public int indexOf(Object obj) {
		int index = -1;
		if (!(obj instanceof User))
			return index;
		User u = (User) obj;

		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).equals(u)) {
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

		if (index < users.size() && index >= 0) {
			users.set(index, (User) newObj);
			exitCode = this.writeUsers();
		}
		
		return exitCode;
	}

	@Override
	public boolean updateElement(int index, Object newObj) {
		boolean exitCode = false;
		if (index >= users.size() || index < 0 || !(newObj instanceof User))
			return exitCode;

		users.set(index, (User) newObj);
		exitCode = this.writeUsers();
			
		return exitCode;
	}

	@Override
	public boolean removeElement(Object obj) {
		boolean exitCode = false;

		int index = this.indexOf(obj);
		if (index == -1)
			return exitCode;

		if (index < users.size() && index >= 0) {
			users.remove(index);
			exitCode = this.writeUsers();
		}

		return exitCode;
	}

	@Override
	public boolean removeElement(int index) {
		boolean exitCode = false;
		if (index >= users.size() || index < 0)
			return exitCode;

		users.remove(index);
		exitCode = this.writeUsers();
			
		return exitCode;
	}
}
