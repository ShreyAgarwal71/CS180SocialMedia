package com.cs180.db;

import java.util.Arrays;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * A Collection class to manage users
 * 
 * @author Ates Isfendiyaroglu and Mahit Mehta, L17
 *
 * @version November 2nd, 2024
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
	 * @implNote: Not Thread Safe, Needs Locking
	 *
	 * @param User
	 * @return index
	 *         Return the index of the user with the same username
	 *         Returns -1 if none of the users have the same username
	 */
	@Override
	public int indexOf(User user) {
		int index = -1;

		for (int i = 0; i < this.records.size(); i++) {
			if (this.records.get(i).getUsername().equals(user.getUsername())) {
				index = i;
				break;
			}
		}

		return index;
	}
}
