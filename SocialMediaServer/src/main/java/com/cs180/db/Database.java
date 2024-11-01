package com.cs180.db;

/**
 * A Database class to manage the Collection singletons
 *
 * @author Ates Isfendiyaroglu, L17
 *
 * @version 1 November 2024
 */
public class Database {
	private static final String userFile = "users.txt";
	private static final String postFile = "posts.txt";
	private static final String commentFile = "comments.txt";

	private static final Object ucLock = new Object();
	private static final Object pcLock = new Object();
	private static final Object ccLock = new Object();

	private final UserCollection uc;
	private final PostCollection pc;
	private final CommentCollection cc;
	
	public Database() {
		uc = new UserCollection(userFile);
		pc = new PostCollection(postFile);
		cc = new CommentCollection(commentFile);
	}

	/**
	 * Method to call when the server asks to remove an element
	 * from the UserCollection.
	 * IMPORTANT: DO NOT GIVE PRIMITIVE TYPE PARAMETERS, USE WRAPPER CLASSES!!!
	 * USE LONG FOR INDEX VALUES!!!
	 * (ex. user Integer x instead of int x)
	 *
	 * @param obj 
	 * @return exitCode
	 */
	public boolean removeUser(Object obj) {
		boolean exitCode = false;
		synchronized (ucLock) {
			if (obj instanceof String) {
				int index = uc.indexOf((String) obj);
				exitCode = uc.removeElement(index);
			} else if (obj instanceof Long) {
				exitCode = uc.removeElement(((Long) obj).intValue());
			} else if (obj instanceof User) {
				int index = uc.indexOf(obj);
				exitCode = uc.removeElement(index);
			}
		}
		return exitCode;
	}

	/**
	 * Method to call when the server asks to remove an element
	 * from the PostCollection.
	 * IMPORTANT: DO NOT GIVE PRIMITIVE TYPE PARAMETERS, USE WRAPPER CLASSES!!!
	 * (ex. user Integer x instead of int x)
	 *
	 * @param obj 
	 * @return exitCode
	 */
	public boolean removePost(Object obj) {
		boolean exitCode = false;
		synchronized (pcLock) {
			if (obj instanceof Integer) {
				int index = pc.indexOf(((Integer) obj).intValue());
				exitCode = pc.removeElement(index);
			} else if (obj instanceof Long) {
				exitCode = pc.removeElement(((Long) obj).intValue());
			} else if (obj instanceof Post) {
				int index = pc.indexOf(obj);
				exitCode = pc.removeElement(index);
			}
		}
		return exitCode;
	}

	/**
	 * Method to call when the server asks to remove an element
	 * from the CommentCollection.
	 * IMPORTANT: DO NOT GIVE PRIMITIVE TYPE PARAMETERS, USE WRAPPER CLASSES!!!
	 * (ex. user Integer x instead of int x)
	 *
	 * @param obj 
	 * @return exitCode
	 */
	public boolean removeComment(Object obj) {
		boolean exitCode = false;
		synchronized (ccLock) {
			if (obj instanceof Integer) {
				int index = cc.indexOf(((Integer) obj).intValue());
				exitCode = cc.removeElement(index);
			} else if (obj instanceof Long) {
				exitCode = cc.removeElement(((Long) obj).intValue());
			} else if (obj instanceof Comment) {
				int index = cc.indexOf(obj);
				exitCode = cc.removeElement(index);
			}
		}
		return exitCode;
	}
}
