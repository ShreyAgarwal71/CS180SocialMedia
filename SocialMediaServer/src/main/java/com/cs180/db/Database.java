package com.cs180.db;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A Database class to manage the Collection singletons in the database. This
 * class is responsible for reading and writing user, post, and comment data to
 * and from the disk. It also provides methods to get the UserCollection, the
 * PostCollection, and the CommentCollection. The Database class is a singleton
 * class.
 *
 * @author Ates Isfendiyaroglu and Mahit Mehta
 * @version 2024-11-03
 */
public class Database {
	private static final ScheduledThreadPoolExecutor SCHEDULER = new ScheduledThreadPoolExecutor(3);

	private static final Object MAIN_LOCK = new Object();

	private static String userFileName = "users.txt";
	private static String postFileName = "posts.txt";
	private static String commentFileName = "comments.txt";

	private static UserCollection uc;
	private static PostCollection pc;
	private static CommentCollection cc;

	private static final AtomicBoolean HAS_BEEN_INITIALIZED = new AtomicBoolean(false);

	public static void init(String userFile, String postFile, String commentFile) {
		Database.userFileName = userFile;
		Database.postFileName = postFile;
		Database.commentFileName = commentFile;

		init();
	}

	public static void init() {
		synchronized (MAIN_LOCK) {
			if (uc == null) {
				uc = new UserCollection(Collection.getCollectionAbsolutePath(Database.userFileName), SCHEDULER);
				pc = new PostCollection(Collection.getCollectionAbsolutePath(Database.postFileName), SCHEDULER);
				cc = new CommentCollection(Collection.getCollectionAbsolutePath(Database.commentFileName),
						SCHEDULER);
			}
		}
	}

	public Database() {
		init();

		if (HAS_BEEN_INITIALIZED.compareAndSet(false, true)) {
			Runtime.getRuntime().addShutdownHook((new Thread() {
				public void run() {
					System.out.println("Database: Saving data");
					Database.uc.save();
					Database.pc.save();
					Database.cc.save();
					System.out.println("Database: Saved Data");
				}
			}));
		}
	}

	public UserCollection getUserCollection() {
		return Database.uc;
	}

	public PostCollection getPostCollection() {
		return Database.pc;
	}

	public CommentCollection getCommentCollection() {
		return Database.cc;
	}

	public static void main(String[] args) {
		System.out.println("Hello from Database!");
	}
}
