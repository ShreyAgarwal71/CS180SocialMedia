package com.cs180.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * A Collection class to manage comments
 * 
 * @author Ates Isfendiyaroglu and Mahit Mehta, L17
 *
 * @version November 2nd, 2024
 */
public class CommentCollection implements Collection<Comment> {
	private final String fileName;
	private ArrayList<Comment> comments;

	private final ScheduledThreadPoolExecutor scheduler;
	private boolean needWrite = false;

	public CommentCollection(String fileName, ScheduledThreadPoolExecutor scheduler) {
		this.fileName = fileName;
		Comment[] o = this.readData(fileName);
		if (o == null) {
			comments = new ArrayList<>();
		} else {
			comments = new ArrayList<>(Arrays.asList(o));
		}

		this.scheduler = scheduler;
		this.scheduler.scheduleAtFixedRate(() -> {
			if (!needWrite)
				return;

			this.writeComments();
			needWrite = false;
		}, 0, Collection.ASYNC_WRITE_FREQ, java.util.concurrent.TimeUnit.SECONDS);
	}

	public void save() {
		if (!needWrite)
			return;
		this.writeComments();
	}

	/**
	 * Wrapper method for the Collection interface's writeData method.
	 * This methods fills up Collection.writeData()'s parameters automatically.
	 * Returns true on success, false on fail.
	 * 
	 * @return exitCode
	 */
	public boolean writeComments() {
		Comment[] arr = new Comment[comments.size()];
		comments.toArray(arr);
		return this.writeData(fileName, arr);
	}

	@Override
	public boolean addElement(Object obj) {
		boolean exitCode = false;
		if (!(obj instanceof Comment))
			return exitCode;

		Comment c = (Comment) obj;
		comments.add(c);
		needWrite = true;
		exitCode = true;

		return exitCode;
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
			needWrite = true;
			exitCode = true;
		}

		return exitCode;
	}

	@Override
	public boolean updateElement(int index, Object newObj) {
		boolean exitCode = false;
		if (index >= comments.size() || index < 0 || !(newObj instanceof Comment))
			return exitCode;

		comments.set(index, (Comment) newObj);
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

		if (index < comments.size() && index >= 0) {
			comments.remove(index);
			needWrite = true;
			exitCode = true;
		}

		return exitCode;
	}

	@Override
	public boolean removeElement(int index) {
		boolean exitCode = false;
		if (index >= comments.size() || index < 0)
			return exitCode;

		comments.remove(index);
		needWrite = true;
		exitCode = true;

		return exitCode;
	}
}
