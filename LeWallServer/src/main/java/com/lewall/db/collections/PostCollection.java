package com.lewall.db.collections;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.lewall.db.helpers.BaseCollection;
import com.lewall.db.models.Post;

/**
 * A Collection class to manage posts in the database. This class is responsible
 * for reading and writing post data to and from the disk. It also provides
 * methods to find posts by their postID.
 * 
 * @author Ates Isfendiyaroglu and Mahit Mehta
 * @version 11/15/2024
 * 
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
	 * Find all posts by a specific userId
	 * 
	 * @param userId
	 * @return List<Post>
	 */
	public List<Post> findByUserId(UUID userId) {
		return this.findAll(post -> post.getUserId().equals(userId));
	}
}
