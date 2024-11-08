package com.cs180.db.models;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;

/**
 * CommentTest
 * 
 * A class that provides test cases for the Comment class.
 * 
 * @author Zayan and Mahit Mehta
 * @version 2024-11-03
 */
public class CommentTest {
	/**
	 * A test case for the Comment constructor.
	 *
	 * @see Comment#Comment(String, String, String, int, int, int, Comment[])
	 */
	@Test
	public void testCommentConstructor() {
		UUID userId = UUID.randomUUID();
		UUID postId = UUID.randomUUID();

		Comment testComment = new Comment(userId, postId, "This is a comment", "11-3-24",
				5678,
				new Comment[2]);

		assertNotNull(testComment, "Ensure the constructor is actually instantiating variables");
	}

	/**
	 * A test case for the Comment getters.
	 */
	@Test
	public void testGetters() {
		UUID userId = UUID.randomUUID();
		UUID postId = UUID.randomUUID();

		Comment[] comments = new Comment[2];
		Comment testComment = new Comment(userId, postId, "This is a comment", "11-3-24",
				5678,
				comments);

		assertEquals(testComment.getMessageComment(), "This is a comment",
				"Ensure the getter is working for message comment");
		assertEquals(testComment.getDate(), "11-3-24", "Ensure the getter is working for comment date");
		assertEquals(testComment.getPostId(), postId, "Ensure the getter is working for postId");
		assertEquals(testComment.getUserId(), userId, "Ensure the getter is working for userId");
		assertEquals(testComment.getVotes(), 5678, "Ensure the getter is working for votes");
		assertArrayEquals(testComment.getComments(), comments, "Ensure the getter is working for comments");
	}

	/**
	 * A test case for the Comment setters.
	 */
	@Test
	public void testSetters() {
		UUID userId = UUID.randomUUID();
		UUID postId = UUID.randomUUID();

		Comment[] comments = new Comment[2];
		Comment testComment = new Comment(userId, postId, "This is a comment", "11-3-24",
				5678,
				comments);

		testComment.setMessageComment("This is a new comment");
		testComment.setDate("11-4-24");
		testComment.setPostId(UUID.randomUUID());
		testComment.setUserId(UUID.randomUUID());
		testComment.setVotes(1234);
		testComment.setComments(new Comment[2]);

		assertEquals(testComment.getMessageComment(), "This is a new comment",
				"Ensure the setter is working for comment");
		assertEquals(testComment.getDate(), "11-4-24", "Ensure the setter is working for comment date");
		assertFalse(testComment.getPostId().equals(postId), "Ensure the setter is working for postId");
		assertFalse(testComment.getUserId().equals(userId), "Ensure the setter is working for userId");
		assertEquals(testComment.getVotes(), 1234, "Ensure the setter is working for votes");
		assertArrayEquals(testComment.getComments(), new Comment[2], "Ensure the setter is working for comments");
	}
}
