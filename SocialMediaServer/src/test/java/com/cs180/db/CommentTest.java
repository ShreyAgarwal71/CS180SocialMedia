package com.cs180.db;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * CommentTest
 * 
 * A class that provides test cases for the Comment class.
 * 
 * @author Zayan
 * @author Mahit Mehta
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
		Comment testComment = new Comment("This is a comment", "testUser", "11-3-24", 5678, 2, 1,
				new Comment[2]);

		assertNotNull(testComment, "Ensure the constructor is actually instantiating variables");
	}

	/**
	 * A test case for the Comment constructor.
	 * 
	 * @see Comment#Comment(String, String, String, int, int, int, Comment[])
	 */
	@Test
	public void testGetMessageComment() {
		Comment testComment = new Comment("This is a comment", "testUser", "11-3-24", 5678, 2, 1,
				new Comment[2]);

		assertEquals("This is a comment",
				testComment.getMessageComment(),
				"Ensure getMessageComment is correctly getting the comment message");
	}

	/**
	 * A test case for the Comment constructor.
	 * 
	 * @see Comment#Comment(String, String, String, int, int, int, Comment[])
	 */
	@Test
	public void testGetCommentUser() {
		String testUser = "testUser";
		Comment testComment = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, 1,
				new Comment[2]);

		assertEquals(testUser, testComment.getUsername(),
				"Ensure getUsername is correctly getting the comment username");
	}

	/**
	 * A test case for the Comment constructor.
	 * 
	 * @see Comment#Comment(String, String, String, int, int, int, Comment[])
	 */
	@Test
	public void testGetCommentDate() {
		Comment testComment = new Comment("This is a comment", "testUser", "11-3-24", 5678, 2, 1,
				new Comment[2]);

		assertEquals("11-3-24", testComment.getDate(), "Ensure getDate is correctly getting the comment date");
	}

	/**
	 * A test case for the Comment constructor.
	 * 
	 * @see Comment#Comment(String, String, String, int, int, int, Comment[])
	 */
	@Test
	public void testGetCommentID() {
		Comment testComment = new Comment("This is a comment", "testUser", "11-3-24", 5678, 2, 1,
				new Comment[2]);

		assertEquals(5678, testComment.getCommentId(),
				"Ensure getCommentID is correctly getting the comment ID");
	}

	/**
	 * A test case for the Comment constructor.
	 * 
	 * @see Comment#Comment(String, String, String, int, int, int, Comment[])
	 */
	@Test
	public void testGetCommentVotes() {
		Comment testComment = new Comment("This is a comment", "testUser", "11-3-24", 5678, 2, 1,
				new Comment[2]);

		assertEquals(1, testComment.getVotes(), "Ensure getVotes is correctly getting the comment votes");
	}

	/**
	 * A test case for the Comment constructor.
	 * 
	 * @see Comment#Comment(String, String, String, int, int, int, Comment[])
	 */
	@Test
	public void testGetCommentComments() {
		Comment testComment = new Comment("This is a comment", "testUser", "11-3-24", 5678, 2, 1,
				new Comment[2]);
		assertArrayEquals(new Comment[2],
				testComment.getComments(), "Ensure getComments is correctly getting the comment array");
	}

	/**
	 * A test case for the Comment constructor.
	 * 
	 * @see Comment#Comment(String, String, String, int, int, int, Comment[])
	 */
	@Test
	public void testSetMessageComment() {
		Comment testComment = new Comment("This is a comment", "testUser", "11-3-24", 5678, 2, 1,
				new Comment[2]);
		testComment.setMessageComment("Updated comment message");

		assertEquals("Updated comment message", testComment.getMessageComment(),
				"Ensure setMessageComment is correctly setting the comment message");
	}

	/**
	 * A test case for the Comment constructor.
	 * 
	 * @see Comment#Comment(String, String, String, int, int, int, Comment[])
	 */
	@Test
	public void testSetCommentUser() {
		Comment testComment = new Comment("This is a comment", "testUser", "11-3-24", 5678, 2, 1,
				new Comment[2]);
		testComment.setUsername("newUser");

		assertEquals("newUser", testComment.getUsername(),
				"Ensure setUsername is correctly setting the comment username");
	}

	/**
	 * A test case for the Comment constructor.
	 * 
	 * @see Comment#Comment(String, String, String, int, int, int, Comment[])
	 */
	@Test
	public void testSetCommentDate() {
		Comment testComment = new Comment("This is a comment", "testUsername", "11-3-24", 5678, 2, 1,
				new Comment[2]);
		testComment.setDate("11-4-24");

		assertEquals(
				"11-4-24", testComment.getDate(),
				"Ensure setDate is correctly setting the comment date");
	}

	/**
	 * A test case for the Comment constructor.
	 * 
	 * @see Comment#Comment(String, String, String, int, int, int, Comment[])
	 */
	@Test
	public void testSetCommentID() {
		Comment testComment = new Comment("This is a comment", "testUsername", "11-3-24", 5678, 2, 1,
				new Comment[2]);
		testComment.setCommentId(9876);

		assertEquals(9876, testComment.getCommentId(),
				"Ensure setCommentID is correctly setting the commentId");
	}

	/**
	 * A test case for the Comment constructor.
	 * 
	 * @see Comment#Comment(String, String, String, int, int, int, Comment[])
	 */
	@Test
	public void testSetCommentVotes() {
		Comment testComment = new Comment("This is a comment", "testUsername", "11-3-24", 5678, 2, 1,
				new Comment[2]);
		testComment.setVotes(10);

		assertEquals(10, testComment.getVotes(), "Ensure setVotes is correctly setting the comment votes");
	}

	/**
	 * A test case for the Comment constructor.
	 * 
	 * @see Comment#Comment(String, String, String, int, int, int, Comment[])
	 */
	@Test
	public void testSetCommentComments() {
		Comment testComment = new Comment("This is a comment", "testUsername", "11-3-24", 5678, 2, 1,
				new Comment[2]);
		testComment.setComments(new Comment[10]);

		assertArrayEquals(new Comment[10], testComment.getComments(),
				"Ensure setComments is correctly setting the comment array");
	}

	/**
	 * A test case for the Comment constructor.
	 * 
	 * @see Comment#Comment(String, String, String, int, int, int, Comment[])
	 */
	@Test
	public void testCommentEquals() {
		Comment testComment = new Comment("This is a comment", "testUsername", "11-3-24", 5678, 2, 1,
				new Comment[2]);
		Comment testDifferent = new Comment("Different comment", "testUsername", "11-3-24",
				5678, 2, 1, new Comment[2]);

		assertFalse(testComment.equals(testDifferent),
				"Ensure the Comment equals method is correctly returning false when comparing  two different Comments");
	}
}
