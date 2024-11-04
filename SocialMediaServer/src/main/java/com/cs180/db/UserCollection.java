package com.cs180.db;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * A Collection class to manage users in the database of our program. This class
 * is responsible for reading and writing user data to and from the disk. It
 * also provides methods to find users by their username.
 * 
 * @author Ates Isfendiyaroglu
 * @author Mahit Mehta
 * @version 2024-11-03
 * 
 */
public class UserCollection extends BaseCollection<User> {
	private final String fileName;

	public UserCollection(String fileName, ScheduledThreadPoolExecutor scheduler) {
		super(fileName, scheduler);
		this.fileName = fileName;
	}

	/**
	 * Wrapper method for the Collection interface's persistToDisk method.
	 * 
	 * @return exitCode
	 */
	@Override
	public boolean writeRecords() {
		this.records.lockRead();
		User[] arr = new User[this.records.size()];
		this.records.toArray(arr);
		this.records.unlockRead();

		return this.persistToDisk(fileName, arr);
	}

	/**
	 * @implNote: Not Thread Safe, Needs Locking (Meant for internal use)
	 *
	 * @param User
	 * @return index
	 *         Return the index of the user with the same username
	 *         Returns -1 if none of the users have the same username
	 */
	@Override
	public int indexOf(User user) {
		int index = -1;

		if (user == null) {
			return index;
		}

		for (int i = 0; i < this.records.size(); i++) {
			if (this.records.get(i).getUsername().equals(user.getUsername())) {
				index = i;
				break;
			}
		}

		return index;
	}

	/**
	 * Find a user by username
	 * 
	 * @param username
	 * @return user
	 *         Return the user with the same username
	 *         Returns null if none of the users have the same username
	 */
	public User findByUsername(String username) {
		return this.findOne(user -> user.getUsername().equals(username));
	}
}
