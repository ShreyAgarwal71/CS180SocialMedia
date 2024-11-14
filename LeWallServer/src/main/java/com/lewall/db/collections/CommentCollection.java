package com.lewall.db.collections;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.lewall.db.helpers.BaseCollection;
import com.lewall.db.models.Comment;

/**
 * A Collection class to manage comments in the database. This class is
 * responsible for reading and writing comment data to and from the disk. It
 * also provides methods to find comments by their postId.
 * 
 * @author Ates Isfendiyaroglu and Mahit Mehta
 * @version 2024-11-03
 */
public class CommentCollection extends BaseCollection<Comment> {
	private final String fileName;

	public CommentCollection(String fileName, ScheduledThreadPoolExecutor scheduler) {
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
		Comment[] arr = new Comment[this.records.size()];
		this.records.toArray(arr);
		this.records.unlockRead();

		return this.persistToDisk(fileName, arr);
	}

	public List<Comment> commentsByPostId(UUID postId) {
		return this.findAll(c -> c.getPostId().equals(postId));
	}
}
