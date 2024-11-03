package com.cs180.db;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * A Collection class to manage posts
 * 
 * @author Ates Isfendiyaroglu and Mahit Mehta, L17
 *
 * @version November 2nd, 2024
 */
public class PostCollection extends BaseCollection<Post> {
	private final String fileName;

	public PostCollection(String fileName, ScheduledThreadPoolExecutor scheduler) {
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
		Post[] arr = new Post[this.records.size()];
		this.records.toArray(arr);
		this.records.unlockRead();

		return this.persistToDisk(fileName, arr);
	}

	/**
	 * @implNote: Not Thread Safe, Needs Locking
	 *
	 * @param post
	 * @return index
	 *         Return the index of the post with the same postID
	 *         Returns -1 if none of the posts have the same postID
	 */
	@Override
	public int indexOf(Post post) {
		int index = -1;

		for (int i = 0; i < this.records.size(); i++) {
			if (this.records.get(i).getPostId() == post.getPostId()) {
				index = i;
				break;
			}
		}

		return index;
	}

	/**
	 * Find all posts by a specific username
	 * 
	 * @param username
	 * @return List<Post>
	 */
	public List<Post> findByUsername(String username) {
		return this.findAll(post -> post.getUsername().equals(username));
	}
}
