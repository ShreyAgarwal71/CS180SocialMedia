package com.cs180.db.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;

/**
 * PostTest
 * 
 * A class that provides test cases for the Post class.
 * 
 * @author Zayan and Mahit Mehta and Shrey Agarwal
 * @version 11/12/2024
 */
public class PostTest {
	/**
	 * A test case for the Post constructor.
	 *
	 * @see Post#Post(String, String, String, int, int, String)
	 */
	@Test
	public void testPostConstructor() {
		Post testPost = new Post(UUID.randomUUID(), "testUser", "12-12-24", 1234,
				"https://www.mahitm.com/cdn/v1/post/1234");

		assertNotNull(testPost, "Ensure the constructor is actually instantiating variables");
	}

	/**
	 * A test case for the Post getters.
	 */
	@Test
	public void testGetters() {
		UUID userId = UUID.randomUUID();
		Post testPost = new Post(userId, "testMessage", "12-12-24", 1234,
				"https://www.mahitm.com/cdn/v1/post/1234");

		testPost.setUsersLiked(new String[2]);
		assertEquals(testPost.getDate(), "12-12-24", "Ensure the getter is working for post date");
		assertEquals(testPost.getImageURL(), "https://www.mahitm.com/cdn/v1/post/1234",
				"Ensure the getter is working for imageURL");
		assertEquals(testPost.getUserId(), userId, "Ensure the getter is working for userId");
		assertEquals(testPost.getLikes(), 1234, "Ensure the getter is working for votes");
		assertEquals(testPost.getMessagePost(), "testMessage", "Ensure the getter is working for messagePost");
		assertNotNull(testPost.getUsersLiked(), "Ensure the getter is working for usersLiked");

	}

	/**
	 * A test case for the Post setters.
	 */
	@Test
	public void testSetters() {
		UUID userId = UUID.randomUUID();
		Post testPost = new Post(userId, "testMessage", "12-12-24", 1234,
				"https://www.mahitm.com/cdn/v1/post/1234");

		testPost.setDate("12-12-25");
		testPost.setImageURL("https://www.mahitm.com/cdn/v1/post/1235");
		testPost.setUserId(UUID.randomUUID());
		testPost.setLikes(1235);
		testPost.setMessagePost("testMessage2");

		assertEquals(testPost.getDate(), "12-12-25", "Ensure the setter is working for post date");
		assertEquals(testPost.getImageURL(), "https://www.mahitm.com/cdn/v1/post/1235",
				"Ensure the setter is working for imageURL");
		assertFalse(testPost.getUserId().equals(userId), "Ensure the setter is working for userId");
		assertEquals(testPost.getLikes(), 1235, "Ensure the setter is working for votes");
		assertEquals(testPost.getMessagePost(), "testMessage2", "Ensure the setter is working for messagePost");
	}

	/**
	 * A test case for the Post addLike and removeLike method.
	 */
	@Test
	public void testAddRemoveLike() {
		UUID userId = UUID.randomUUID();
		Post testPost = new Post(userId, "testMessage", "12-12-24", 1234,
				"https://www.mahitm.com/cdn/v1/post/1234");

		testPost.addLike(userId.toString());
		assertEquals(testPost.getLikes(), 1235, "Ensure the addLike method is working");

		testPost.removeLike(userId.toString());
		assertEquals(testPost.getLikes(), 1234, "Ensure the removeLike method is working");
	}

	/**
	 * A test case for the Post equals and toString method.
	 */
	@Test
	public void testEqualsToString() {
		UUID userId = UUID.randomUUID();
		Post testPost = new Post(userId, "testMessage", "12-12-24", 1234,
				"https://www.mahitm.com/cdn/v1/post/1234");

		Post testPost2 = new Post(userId, "testMessage", "12-12-24", 1234,
				"https://www.mahitm.com/cdn/v1/post/1234");

		assertEquals(testPost, testPost2, "Ensure the equals method is working");

		assertEquals(testPost.toString(), testPost2.toString(), "Ensure the toString method is working");
	}
}
