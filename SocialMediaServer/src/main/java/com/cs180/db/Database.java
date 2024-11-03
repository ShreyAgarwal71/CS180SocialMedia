package com.cs180.db;

import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A Database class to manage the Collection singletons
 *
 * @author Ates Isfendiyaroglu and Mahit Mehta, L17
 *
 * @version November 2nd, 2024
 */
public class Database {
	private static final ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(3);

	private static final String userFile = "users.txt";
	private static final String postFile = "posts.txt";
	private static final String commentFile = "comments.txt";

	private static final UserCollection uc = new UserCollection(userFile, scheduler);
	private static final PostCollection pc = new PostCollection(postFile, scheduler);
	private static final CommentCollection cc = new CommentCollection(commentFile, scheduler);

	private static final AtomicBoolean hasBeenInitialized = new AtomicBoolean(false);

	public Database() {
		if (hasBeenInitialized.compareAndSet(false, true)) {
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
		// populateTest();
		multiThreadTest();
	}

	private static void multiThreadTest() {
		ArrayList<Thread> threads = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(() -> {
				Database db = new Database();

				for (int j = 0; j < 1000; j++) {
					db.getUserCollection()
							.addElement(new User("user_" + j, "pass", "username_ " + j, "email_" + j));
				}
			});
			threads.add(t);
			t.start();
		}

		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("All threads have finished executing");

		Database db = new Database();

		System.out.println("Users in the database:" + db.getUserCollection().count());
	}

	private static void populateTest() {
		Database db = new Database();

		long start = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			User u = new User("user" + i, "pass" + i, "username: " + i, "email" + i);
			db.getUserCollection().addElement(u);
			System.out.println("User added: " + u);
		}

		for (int i = 0; i < 3; i++) {
			Post u = new Post("hello", "mahit", "11/11/11", 1, 0, "image", new Comment[0]);
			db.getPostCollection().addElement(u);
			System.out.println("post added: " + u);
		}

		for (int i = 0; i < 3; i++) {
			Comment u = new Comment("hello", new User("user" + i, "pass" + i, "username:" + i, "email" + i),
					"11/11/11", 1, 0, new Comment[0]);
			db.getCommentCollection().addElement(u);
			System.out.println("comment added: " + u);
		}
		System.out.println("Time to add data: " + (System.currentTimeMillis() - start) + "ms");

	}
}
