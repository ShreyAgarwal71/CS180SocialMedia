package com.cs180.db;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * A Collection class to manage comments
 * 
 * @author Ates Isfendiyaroglu and Mahit Mehta, L17
 *
 * @version November 2nd, 2024
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

	/**
	 * @implNote: Not Thread Safe, Needs Locking (Meant for internal use)
	 *
	 * @param Comment
	 * @return index
	 *         Return the index of the comment with the same commentID
	 *         Returns -1 if none of the comments have the same commentID
	 */
	public int indexOf(Comment comment) {
		int index = -1;

		if (comment == null) {
			return index;
		}

		for (int i = 0; i < this.records.size(); i++) {
			if (this.records.get(i).getCommentId() == comment.getCommentId()) {
				index = i;
				break;
			}
		}

		return index;
	}
}
