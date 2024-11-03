package com.cs180.db;

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
	private RwLockArrayList<Post> posts;
	private final ScheduledThreadPoolExecutor scheduler;
	private boolean needWrite = false;

	public PostCollection(String fileName, ScheduledThreadPoolExecutor scheduler) {
		this.fileName = fileName;
		Post[] arr = this.readData(fileName);
		if (arr == null) {
			this.posts = new RwLockArrayList<>();
		} else {
			this.posts = new RwLockArrayList<>(Arrays.asList(arr));
		}

		this.scheduler = scheduler;
		this.scheduler.scheduleAtFixedRate(() -> {
			if (!this.needWrite)
				return;

			this.writePosts();
			this.needWrite = false;
		}, 0, Collection.ASYNC_WRITE_FREQ, java.util.concurrent.TimeUnit.SECONDS);
	}

	public void save() {
		if (!this.needWrite)
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
		this.posts.lockRead();
		Post[] arr = new Post[this.posts.size()];
		this.posts.toArray(arr);
		this.posts.unlockRead();

		return this.writeData(fileName, arr);
	}

	/**
	 * @implNote: Not Thread Safe, Needs Locking
	 *
	 * @param post
	 * @return index
	 *         Return the index of the post with the same postID
	 *         Returns -1 if none of the posts have the same postID
	 */
	private int indexOf(Post post) {
		int index = -1;

		for (int i = 0; i < posts.size(); i++) {
			if (this.posts.get(i).getPostID() == post.getPostID()) {
				index = i;
				break;
			}
		}

		return index;
	}

	@Override
	public boolean addElement(Post post) {
		boolean exitCode = false;

		this.posts.lockWrite();
		this.posts.add(post);
		this.posts.unlockWrite();

		this.needWrite = true;
		exitCode = true;

		return exitCode;
	}

	@Override
	public boolean updateElement(Post target, Post post) {
		boolean exitCode = false;

		this.posts.lockWrite();

		int index = this.indexOf(target);
		if (index == -1) {
			this.posts.unlockWrite();
			return exitCode;
		}

		this.posts.set(index, post);
		this.posts.unlockWrite();

		this.needWrite = true;
		exitCode = true;

		return exitCode;
	}

	@Override
	public boolean removeElement(Post post) {
		boolean exitCode = false;

		this.posts.lockWrite();

		int index = this.indexOf(post);
		if (index == -1) {
			this.posts.unlockWrite();
			return exitCode;
		}

		this.posts.remove(index);
		this.posts.unlockWrite();

		this.needWrite = true;
		exitCode = true;

		return exitCode;
	}

	@Override
	public int count() {
		this.posts.lockRead();
		int size = this.posts.size();
		this.posts.unlockRead();

		return size;
	}
}
