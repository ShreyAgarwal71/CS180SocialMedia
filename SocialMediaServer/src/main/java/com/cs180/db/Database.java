package com.cs180.db;

// TODO: Javadoc
public class Database {
	private static final String userFile = "users.txt";
	private static final String postFile = "posts.txt";
	private static final String commentFile = "comments.txt";
	private final UserCollection uc;
	private final PostCollection pc;
	private final CommentCollection cc;
	
	public Database() {
		uc = new UserCollection(userFile);
		pc = new PostCollection(postFile);
		cc = new CommentCollection(commentFile);
	}

	/**
	 * I suggest making each collection singleton have its own socket 
	 * in its own thread and thus achieveing concurrenty.
	 * This would mean the implementation of this class would be left for phase 2.
	 *
	 * TODO: Implement the class
	 */
}
