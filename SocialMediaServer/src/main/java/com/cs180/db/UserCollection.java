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
public class UserCollection implements Collection<User> {
	private final String fileName;
	private RwLockArrayList<User> users;

	private final ScheduledThreadPoolExecutor scheduler;
	private boolean needWrite = false;

	public UserCollection(String fileName, ScheduledThreadPoolExecutor scheduler) {
		this.fileName = fileName;
		User[] arr = this.readData(fileName);
		if (arr == null) {
			users = new RwLockArrayList<User>();
		} else {
			users = new RwLockArrayList<User>(Arrays.asList(arr));
		}

		this.scheduler = scheduler;
		this.scheduler.scheduleAtFixedRate(() -> {
			if (!this.needWrite)
				return;

			this.writeUsers();
			this.needWrite = false;
		}, 0, Collection.ASYNC_WRITE_FREQ, java.util.concurrent.TimeUnit.SECONDS);
	}

	public void save() {
		if (!this.needWrite)
			return;

		this.writeUsers();
	}

	/**
	 * Wrapper method for the Collection interface's writeData method.
	 * This methods fills up Collection.writeData()'s parameters automatically.
	 * Returns true on success, false on fail.
	 * 
	 * @return exitCode
	 */
	public boolean writeUsers() {
		this.users.lockRead();
		User[] arr = new User[this.users.size()];
		this.users.toArray(arr);
		this.users.unlockRead();

		return writeData(fileName, arr);
	}

	/**
	 * @implNote: Not Thread Safe, Needs Locking
	 *
	 * @param User
	 * @return index
	 *         Return the index of the user with the same username
	 *         Returns -1 if none of the users have the same username
	 */
	private int indexOf(User user) {
		int index = -1;

		for (int i = 0; i < users.size(); i++) {
			if (this.users.get(i).getUsername().equals(user.getUsername())) {
				index = i;
				break;
			}
		}

		return index;
	}

	public boolean addElement(User user) {
		boolean exitCode = false;

		this.users.lockWrite();
		this.users.add(user);
		this.users.unlockWrite();

		this.needWrite = true;
		exitCode = true;

		return exitCode;
	}

	public boolean updateElement(User target, User user) {
		boolean exitCode = false;

		this.users.lockWrite();

		int index = this.indexOf(target);
		if (index == -1) {
			this.users.unlockWrite();
			return exitCode;
		}

		this.users.set(index, user);
		this.users.unlockWrite();

		this.needWrite = true;
		exitCode = true;

		return exitCode;
	}

	public boolean removeElement(User user) {
		boolean exitCode = false;

		this.users.lockWrite();

		int index = this.indexOf(user);
		if (index == -1) {
			this.users.unlockWrite();
			return exitCode;
		}

		this.users.remove(index);
		this.users.unlockWrite();

		this.needWrite = true;
		exitCode = true;

		return exitCode;
	}
}
