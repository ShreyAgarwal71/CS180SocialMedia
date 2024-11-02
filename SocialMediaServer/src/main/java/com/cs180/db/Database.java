package com.cs180.db;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Database
 * 
 * This class represents the database that will handle all the collections
 * 
 * @author Mahit Mehta, L17
 *
 * @version November 2nd, 2024
 * 
 */
public class Database {
	private static final String userFile = "users.txt";
	private static final String postFile = "posts.txt";
	private static final String commentFile = "comments.txt";
	private final UserCollection uc;
	private final PostCollection pc;
	private final CommentCollection cc;

	public Database() {
		ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(3);

		uc = new UserCollection(userFile, scheduler);
		pc = new PostCollection(postFile, scheduler);
		cc = new CommentCollection(commentFile, scheduler);

		Runtime.getRuntime().addShutdownHook((new Thread() {
			public void run() {
				uc.save();
				pc.save();
				cc.save();
			}
		}));
	}

	public static void main(String[] args) {
		populateTest();
	}

	private static void populateTest() {
		Database db = new Database();

		for (int i = 0; i < 1000; i++) {
			User u = new User("user" + i, "pass" + i, "username: " + i, "email" + i);
			db.uc.addElement(u);
			System.out.println("User added: " + u);
		}

		for (int i = 0; i < 10; i++) {
			Post u = new Post("hello", "mahit", "11/11/11", 1, 0, "image", new Comment[0]);
			db.pc.addElement(u);
			System.out.println("post added: " + u);
		}

		for (int i = 0; i < 10; i++) {
			Comment u = new Comment("hello", new User("user" + i, "pass" + i, "username: " + i, "email" + i),
					"11/11/11", 1, 0, new Comment[0]);
			db.cc.addElement(u);
			System.out.println("comment added: " + u);
		}
	}

	/**
	 * I suggest making each collection singleton have its own socket
	 * in its own thread and thus achieveing concurrenty.
	 * This would mean the implementation of this class would be left for phase 2.
	 *
	 * TODO: Implement the class
	 */
}
