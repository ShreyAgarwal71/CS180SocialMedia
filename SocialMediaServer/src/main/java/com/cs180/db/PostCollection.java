package com.cs180.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * A Collection class to manage posts
 * 
 * @author Ates Isfendiyaroglu and Mahit Mehta, L17
 *
 * @version November 2nd, 2024
 */
public class PostCollection implements Collection<Post> {
	private final String fileName;
	private ArrayList<Post> posts;
	private final ScheduledThreadPoolExecutor scheduler;
	private boolean needWrite = false;

	public PostCollection(String fileName, ScheduledThreadPoolExecutor scheduler) {
		this.fileName = fileName;
		Post[] o = this.readData(fileName);
		if (o == null) {
			posts = new ArrayList<>();
		} else {
			posts = new ArrayList<>(Arrays.asList(o));
		}

		this.scheduler = scheduler;
		this.scheduler.scheduleAtFixedRate(() -> {
			if (!needWrite)
				return;

			this.writePosts();
			needWrite = false;
		}, 0, Collection.ASYNC_WRITE_FREQ, java.util.concurrent.TimeUnit.SECONDS);
	}

	public void save() {
		if (!needWrite)
			return;
		this.writePosts();
	}

	/**
	 * Wrapper method for the Collection interface's writeData method.
	 * This methods fills up Collection.writeData()'s parameters automatically.
	 * Returns true on success, false on fail.
	 * 
	 * @return exitCode
	 */
	public boolean writePosts() {
		Post[] arr = new Post[posts.size()];
		posts.toArray(arr);
		return this.writeData(fileName, arr);
	}

	@Override
	public boolean addElement(Object obj) {
		boolean exitCode = false;
		if (!(obj instanceof Post))
			return exitCode;

		Post p = (Post) obj;
		posts.add(p);
		needWrite = true;
		exitCode = true;

		return exitCode;
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
			needWrite = true;
			exitCode = true;
		}

		return exitCode;
	}

	@Override
	public boolean updateElement(int index, Object newObj) {
		boolean exitCode = false;
		if (index >= posts.size() || index < 0 || !(newObj instanceof Post))
			return exitCode;

		posts.set(index, (Post) newObj);
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

		if (index < posts.size() && index >= 0) {
			posts.remove(index);
			needWrite = true;
			exitCode = true;
		}

		return exitCode;
	}

	@Override
	public boolean removeElement(int index) {
		boolean exitCode = false;
		if (index >= posts.size() || index < 0)
			return exitCode;

		posts.remove(index);
		needWrite = true;
		exitCode = true;

		return exitCode;
	}
}
