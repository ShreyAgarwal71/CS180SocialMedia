package com.cs180.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.cs180.db.helpers.Collection;
import com.cs180.db.models.Comment;
import com.cs180.db.models.Post;
import com.cs180.db.models.User;

/**
 * DatabaseTest
 * 
 * A class that provides functional tests for the Database class.
 * 
 * @author Mahit Mehta
 * @version 2024-11-03
 */
public class DatabaseTest {
	private static final String tempUserFile = "tmp_users.txt";
	private static final String tempPostFile = "tmp_posts.txt";
	private static final String tempCommentFile = "tmp_comments.txt";

	@BeforeAll
	public static void setUpDB() {
		Database.init(tempUserFile, tempPostFile, tempCommentFile);
	}

	@AfterEach
	public void resetTempDatabase() {
		Database db = new Database();

		db.getUserCollection().clear();
		db.getPostCollection().clear();
		db.getCommentCollection().clear();

		db.getUserCollection().save();
		db.getPostCollection().save();
		db.getCommentCollection().save();
	}

	@AfterAll
	public static void tearDownDatabase() {
		Path dataPath = Collection.getOSDataBasePath();

		File userFile = new File(dataPath.resolve(tempUserFile).toString());
		File postFile = new File(dataPath.resolve(tempPostFile).toString());
		File commentFile = new File(dataPath.resolve(tempCommentFile).toString());

		userFile.delete();
		postFile.delete();
		commentFile.delete();
	}

	// ======================= Multithreading Tests =======================
	@Test
	/**
	 * Test verifies that multiple threads can write users to the database without
	 * any issues
	 */
	public void writeMultiThread() {
		ArrayList<Thread> threads = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			final int threadNum = i;
			Thread t = new Thread(() -> {
				Database db = new Database();

				for (int j = 0; j < 1000; j++) {
					db.getUserCollection()
							.addUser(
									new User("username_" + (j + threadNum * 1000), "pass", "displayName_ " + j, "bio",
											"email_" + (j + threadNum * 1000)));
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

		Database db = new Database();
		assertEquals(10000, db.getUserCollection().count(), "Expected 10000 users to be found");
	}

	@Test
	/**
	 * Test verifies that multiple threads can delete users from the database
	 * without any issues
	 *
	 * Should output 0 users in the database if ran successfully
	 */
	public void deleteMultiThread() {
		ArrayList<Thread> threadsAppend = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			final int threadNum = i;
			Thread t = new Thread(() -> {
				Database db = new Database();

				for (int j = 0; j < 1000; j++) {
					db.getUserCollection()
							.addElement(
									new User("username_" + (j + threadNum * 1000), "pass", "displayName_ " + j, "bio",
											"email_" + (j + threadNum * 1000)));
				}
			});
			threadsAppend.add(t);
			t.start();
		}

		for (Thread t : threadsAppend) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		ArrayList<Thread> threadsDelete = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			final int threadNum = i;
			Thread t = new Thread(() -> {
				Database db = new Database();

				for (int j = 0; j < 1000; j++) {
					db.getUserCollection()
							.removeElement(
									db.getUserCollection().findByUsername("username_" + (j + threadNum * 1000))
											.getId());
				}
			});
			threadsDelete.add(t);
			t.start();
		}

		for (Thread t : threadsDelete) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Database db = new Database();
		assertEquals(0, db.getUserCollection().count());
	}

	// ====================== End Multithreading Tests =====================

	// ================== Start -- PostCollection Tests ===================

	/**
	 * Test primarily verifies that all posts for a given user are returned
	 * 
	 * {@link com.cs180.db.collections.PostCollection#findByUserId(UUID)}
	 */
	@Test
	public void queryPostsByUsername() {
		Database db = new Database();

		User testUser = new User("testUsername", "testPassword", "testDisplayName", "bio", "testEmail");
		db.getUserCollection().addElement(testUser);

		Post testPostOne = new Post(testUser.getId(), "testMessageOne", "testDateOne", 0, "testImageURLOne");
		Post testPostTwo = new Post(testUser.getId(), "testMessageTwo", "testDateTwo", 0, "testImageURLTwo");

		db.getPostCollection().addElement(testPostOne);
		db.getPostCollection().addElement(testPostTwo);

		List<Post> posts = db.getPostCollection().findByUserId(testUser.getId());

		assertEquals(2, posts.size(), "Expected 2 posts to be found");
	}

	/**
	 * Test verifies that a duplicate post is not added to the collection
	 * 
	 */
	@Test
	public void preventsDuplicatePosts() {
		Database db = new Database();

		UUID userId = UUID.randomUUID();

		Post testPostOne = new Post(userId, "testMessageOne", "testDateOne", 0, "testImageURLOne");

		db.getPostCollection().addElement(testPostOne);
		assertFalse(db.getPostCollection().addElement(testPostOne), "Expected duplicate post to not be added");

		int count = db.getPostCollection().count();

		assertEquals(1, count, "Expected 1 post to be found");
	}

	/**
	 * Test verifies that a post is removed from the collection
	 * 
	 */
	@Test
	public void removesPost() {
		Database db = new Database();

		UUID userId = UUID.randomUUID();
		Post testPostOne = new Post(userId, "testMessageOne", "testDateOne", 0, "testImageURLOne");

		db.getPostCollection().addElement(testPostOne);

		int count = db.getPostCollection().count();
		assertEquals(1, count, "Expected 1 post to be found");

		db.getPostCollection().removeElement(testPostOne.getId());
		count = db.getPostCollection().count();
		assertEquals(0, count, "Expected 0 posts to be found");
	}

	/**
	 * Test verifies that a post is updated in the collection
	 * 
	 */
	@Test
	public void updatesPost() {
		Database db = new Database();

		UUID userId = UUID.randomUUID();
		Post testPostOne = new Post(userId, "testMessageOne", "testDateOne", 0, "testImageURLOne");

		db.getPostCollection().addElement(testPostOne);

		testPostOne.setImageURL("testImageURLTwo");
		testPostOne.setMessagePost("testMessageTwo");

		db.getPostCollection().updateElement(testPostOne.getId(), testPostOne);

		Post post = db.getPostCollection().findById(testPostOne.getId());
		assertNotNull(post, "Expected post to be found");

		assertEquals("testImageURLTwo", post.getImageURL(), "Expected post image URL to be updated");
		assertEquals("testMessageTwo", post.getMessagePost(), "Expected post message to be updated");
	}

	// ================= End -- PostCollection Tests ====================

	// =============== Start -- CommentCollection Tests =================

	@Test
	public void queryCommentsByPostId() {
		Database db = new Database();

		UUID userId = UUID.randomUUID();
		UUID postId = UUID.randomUUID();

		Comment testCommentOne = new Comment(userId, postId, "testCommentOne", "testDateOne", 0);
		Comment testCommentTwo = new Comment(userId, postId, "testCommentTwo", "testDateTwo", 1);

		db.getCommentCollection().addElement(testCommentOne);
		db.getCommentCollection().addElement(testCommentTwo);

		List<Comment> comments = db.getCommentCollection().commentsByPostId(postId);

		assertEquals(2, comments.size(), "Expected 2 comments to be found");
	}

	/**
	 * Test verifies that duplicate comments are not added to the collection
	 */
	@Test
	public void preventsDuplicateComments() {
		Database db = new Database();

		UUID userId = UUID.randomUUID();
		UUID postId = UUID.randomUUID();

		Comment testComment = new Comment(userId, postId, "testCommentOne", "testDateOne", 0);

		db.getCommentCollection().addElement(testComment);
		assertFalse(db.getCommentCollection().addElement(testComment),
				"Expected duplicate comment to not be added");

		int count = db.getCommentCollection().count();

		assertEquals(1, count, "Expected 1 comment to be found");
	}

	/**
	 * Test verifies that a comment is removed from the collection
	 */
	@Test
	public void removesComment() {
		Database db = new Database();

		UUID userId = UUID.randomUUID();
		UUID postId = UUID.randomUUID();

		Comment testComment = new Comment(userId, postId, "testCommentOne", "testDateOne", 1);

		db.getCommentCollection().addElement(testComment);

		int count = db.getCommentCollection().count();
		assertEquals(1, count, "Expected 1 comment to be found");

		db.getCommentCollection().removeElement(testComment.getId());
		count = db.getCommentCollection().count();
		assertEquals(0, count, "Expected 0 comments to be found");
	}

	/**
	 * Test verifies that a comment is updated in the collection
	 */
	@Test
	public void updatesComment() {
		Database db = new Database();

		UUID userId = UUID.randomUUID();
		UUID postId = UUID.randomUUID();

		Comment testComment = new Comment(userId, postId, "testCommentOne", "testDateOne", 1);

		db.getCommentCollection().addElement(testComment);

		testComment.setMessageComment("testCommentTwo");
		testComment.setLikes(1);

		db.getCommentCollection().updateElement(testComment.getId(), testComment);

		Comment comment = db.getCommentCollection().findById(testComment.getId());
		assertNotNull(comment, "Expected comment to be found");

		assertEquals("testCommentTwo", comment.getMessageComment(), "Expected comment to be updated");
		assertEquals(1, comment.getLikes(), "Expected comment votes to be updated");
	}

	// =============== End -- CommentCollection Tests =================

	// =============== Start -- UserCollection Tests =================

	/**
	 * Test verifies that a user can be found by username
	 * {@link com.cs180.db.collections.UserCollection#findByUsername(String)}
	 */
	@Test
	public void queryUserByUsername() {
		Database db = new Database();

		String testUserName = "testUserName";
		User testUser = new User(testUserName, "testPassword", "testDisplayName", "testbio", "testEmail");

		db.getUserCollection().addElement(testUser);

		User user = db.getUserCollection().findByUsername(testUserName);

		assertNotNull(user, "Expected user to be found");
	}

	/**
	 * Test ensures that a duplicate user (user with same email, username, or id) is
	 * not added to the collection
	 */
	@Test
	public void preventsDuplicateUsers() {
		Database db = new Database();

		String testUserName = "testUserName";
		User testUser = new User(testUserName, "testPassword", "testDisplayName", "testbio", "testEmail");

		db.getUserCollection().addUser(testUser);
		assertFalse(db.getUserCollection().addUser(testUser), "Expected duplicate user to not be added");

		// Same username, different email
		User testUserTwo = new User(testUserName, "testPassword", "testDisplayName", "testbio", "testEmailTwo");
		assertFalse(db.getUserCollection().addUser(testUserTwo), "Expected user with same username to not be added");

		// Different username, same email
		User testUserThree = new User("testUserNameTwo", "testPassword", "testDisplayName", "testbio", "testEmail");
		assertFalse(db.getUserCollection().addUser(testUserThree), "Expected user with same email to not be added");

		int count = db.getUserCollection().count();

		assertEquals(1, count, "Expected 1 user to be found");
	}

	/**
	 * Test verifies that a user is removed from the collection
	 */
	@Test
	public void removesUser() {
		Database db = new Database();

		String testUserName = "testUserName";
		User testUser = new User(testUserName, "testPassword", "testDisplayName", "testbio", "testEmail");

		db.getUserCollection().addElement(testUser);

		int count = db.getUserCollection().count();
		assertEquals(1, count, "Expected 1 user to be found");

		db.getUserCollection().removeElement(testUser.getId());
		count = db.getUserCollection().count();
		assertEquals(0, count, "Expected 0 users to be found");
	}

	/**
	 * Test verifies that a user is updated in the collection
	 */

	@Test
	public void updatesUser() {
		Database db = new Database();

		String testUserName = "testUserName";
		User testUser = new User(testUserName, "testPassword", "testDisplayName", "testbio", "testEmail");

		db.getUserCollection().addElement(testUser);

		testUser.setDisplayName("testDisplayNameTwo");
		testUser.setEmail("testEmailTwo");

		db.getUserCollection().updateElement(testUser.getId(), testUser);

		User user = db.getUserCollection().findByUsername(testUserName);

		assertNotNull(user, "Expected user to be found");

		assertEquals("testDisplayNameTwo", user.getDisplayName(), "Expected user display name to be updated");
		assertEquals("testEmailTwo", user.getEmail(), "Expected user email to be updated");
	}

	// =============== End -- UserCollection Tests =================
}
