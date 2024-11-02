package com.cs180.db;

import java.util.ArrayList;
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
	private ArrayList<User> users;

	private final ScheduledThreadPoolExecutor scheduler;
	private boolean needWrite = false;

	public UserCollection(String fileName, ScheduledThreadPoolExecutor scheduler) {
		this.fileName = fileName;
		User[] o = this.readData(fileName);
		if (o == null) {
			users = new ArrayList<>();
		} else {
			users = new ArrayList<>(Arrays.asList(o));
		}

		this.scheduler = scheduler;
		this.scheduler.scheduleAtFixedRate(() -> {
			if (!needWrite)
				return;

			this.writeUsers();
			needWrite = false;
		}, 0, Collection.ASYNC_WRITE_FREQ, java.util.concurrent.TimeUnit.SECONDS);
	}

	public void save() {
		if (!needWrite)
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
		User[] arr = new User[users.size()];
		users.toArray(arr);
		return this.writeData(fileName, arr);
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
	public boolean addElement(Object obj) {
		boolean exitCode = false;
		if (!(obj instanceof User))
			return exitCode;

		users.add((User) obj);
		needWrite = true;
		exitCode = true;

		return exitCode;
	}

	@Override
	public boolean updateElement(Object target, Object newObj) {
		boolean exitCode = false;

		int index = this.indexOf(target);
		if (index == -1)
			return exitCode;

		if (index < users.size() && index >= 0) {
			users.set(index, (User) newObj);
			needWrite = true;
			exitCode = true;
		}

		return exitCode;
	}

	@Override
	public boolean updateElement(int index, Object newObj) {
		boolean exitCode = false;
		if (index >= users.size() || index < 0 || !(newObj instanceof User))
			return exitCode;

		users.set(index, (User) newObj);
		needWrite = true;
		exitCode = true;

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
			needWrite = true;
			exitCode = true;
		}

		return exitCode;
	}

	@Override
	public boolean removeElement(int index) {
		boolean exitCode = false;
		if (index >= users.size() || index < 0)
			return exitCode;

		users.remove(index);
		needWrite = true;
		exitCode = true;

		return exitCode;
	}
}
