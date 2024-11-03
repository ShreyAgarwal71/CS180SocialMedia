package com.cs180.db;

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
	private RwLockArrayList<Comment> comments;

	private final ScheduledThreadPoolExecutor scheduler;
	private boolean needWrite = false;

	public CommentCollection(String fileName, ScheduledThreadPoolExecutor scheduler) {
		this.fileName = fileName;
		Comment[] arr = this.readData(fileName);
		if (arr == null) {
			this.comments = new RwLockArrayList<>();
		} else {
			this.comments = new RwLockArrayList<>(Arrays.asList(arr));
		}

		this.scheduler = scheduler;
		this.scheduler.scheduleAtFixedRate(() -> {
			if (!this.needWrite)
				return;

			this.writeComments();
			this.needWrite = false;
		}, 0, Collection.ASYNC_WRITE_FREQ, java.util.concurrent.TimeUnit.SECONDS);
	}

	public void save() {
		if (!this.needWrite)
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
		this.comments.lockRead();
		Comment[] arr = new Comment[this.comments.size()];
		this.comments.toArray(arr);
		this.comments.unlockRead();

		return this.writeData(fileName, arr);
	}

	/**
	 * @implNote: Not Thread Safe, Needs Locking
	 *
	 * @param Comment
	 * @return index
	 *         Return the index of the comment with the same commentID
	 *         Returns -1 if none of the comments have the same commentID
	 */
	public int indexOf(Comment comment) {
		int index = -1;

		for (int i = 0; i < this.comments.size(); i++) {
			if (this.comments.get(i).getCommentID() == comment.getCommentID()) {
				index = i;
				break;
			}
		}

		return index;
	}

	@Override
	public boolean addElement(Comment c) {
		boolean exitCode = false;

		this.comments.lockWrite();
		this.comments.add(c);
		this.comments.unlockWrite();

		this.needWrite = true;
		exitCode = true;

		return exitCode;
	}

	@Override
	public boolean updateElement(Comment target, Comment comment) {
		boolean exitCode = false;

		this.comments.lockWrite();

		int index = this.indexOf(target);
		if (index == -1) {
			this.comments.unlockWrite();
			return exitCode;
		}

		this.comments.set(index, comment);
		this.comments.unlockWrite();

		this.needWrite = true;
		exitCode = true;

		return exitCode;
	}

	@Override
	public boolean removeElement(Comment comment) {
		boolean exitCode = false;

		this.comments.lockWrite();

		int index = this.indexOf(comment);
		if (index == -1) {
			this.comments.unlockWrite();
			return exitCode;
		}

		this.comments.remove(index);
		this.comments.unlockWrite();

		this.needWrite = true;
		exitCode = true;

		return exitCode;
	}

	public int count() {
		this.comments.lockRead();
		int size = this.comments.size();
		this.comments.unlockRead();

		return size;
	}
}
