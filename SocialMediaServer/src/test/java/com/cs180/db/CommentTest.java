package com.cs180.db;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class CommentTest {
    @Test
    public void testCommentConstructor() {
        User testUser = new User("testUser", "password", "New User",
                "testUserEmail@email.com");
        Comment testComment = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, 1,
                new Comment[2]);

        assertNotNull(testComment, "Ensure the constructor is actually instantiating variables");
    }

    @Test
    public void testGetMessageComment() {
        User testUser = new User("testUser", "password", "New User",
                "testUserEmail@email.com");
        Comment testComment = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, 1,
                new Comment[2]);

        assertEquals("This is a comment",
                testComment.getMessageComment(), "Ensure getMessageComment is correctly getting the comment message");
    }

    @Test
    public void testGetCommentUser() {
        User testUser = new User("testUser", "password", "New User",
                "testUserEmail@email.com");
        Comment testComment = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, 1,
                new Comment[2]);

        assertEquals(testUser, testComment.getUser(), "Ensure getUser is correctly getting the comment user");
    }

    @Test
    public void testGetCommentDate() {
        User testUser = new User("testUser", "password", "New User",
                "testUserEmail@email.com");
        Comment testComment = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, 1,
                new Comment[2]);

        assertEquals("11-3-24", testComment.getDate(), "Ensure getDate is correctly getting the comment date");
    }

    @Test
    public void testGetCommentID() {
        User testUser = new User("testUser", "password", "New User",
                "testUserEmail@email.com");
        Comment testComment = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, 1,
                new Comment[2]);

        assertEquals(5678, testComment.getCommentId(), "Ensure getCommentID is correctly getting the comment ID");
    }

    @Test
    public void testGetCommentVotes() {
        User testUser = new User("testUser", "password", "New User",
                "testUserEmail@email.com");
        Comment testComment = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, 1,
                new Comment[2]);

        assertEquals(1, testComment.getVotes(), "Ensure getVotes is correctly getting the comment votes");
    }

    @Test
    public void testGetCommentComments() {
        User testUser = new User("testUser", "password", "New User",
                "testUserEmail@email.com");
        Comment testComment = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, 1,
                new Comment[2]);
        assertArrayEquals(new Comment[2],
                testComment.getComments(), "Ensure getComments is correctly getting the comment array");
    }

    @Test
    public void testSetMessageComment() {
        User testUser = new User("testUser", "password", "New User",
                "testUserEmail@email.com");
        Comment testComment = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, 1,
                new Comment[2]);
        testComment.setMessageComment("Updated comment message");

        assertEquals("Updated comment message", testComment.getMessageComment(),
                "Ensure setMessageComment is correctly setting the comment message");
    }

    @Test
    public void testSetCommentUser() {
        User testUser = new User("testUser", "password", "New User",
                "testUserEmail@email.com");
        Comment testComment = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, 1,
                new Comment[2]);
        User newUser = new User("newUser", "password123", "Another User",
                "newUserEmail@email.com");
        testComment.setUser(newUser);

        assertEquals(newUser, testComment.getUser(), "Ensure setUser is correctly setting the comment user");
    }

    @Test
    public void testSetCommentUserTODO() {
        User testUser = new User("testUser", "password", "New User",
                "testUserEmail@email.com");
        Comment testComment = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, 1,
                new Comment[2]);
        User newUser = new User("newUser", "password123", "Another User",
                "newUserEmail@email.com");
        testComment.setUser(newUser);

        assertEquals(newUser, testComment.getUser(), "Ensure setUser is correctly setting the comment user");
    }

    @Test
    public void testSetCommentDate() {
        User testUser = new User("testUser", "password", "New User",
                "testUserEmail@email.com");
        Comment testComment = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, 1,
                new Comment[2]);
        testComment.setDate("11-4-24");

        assertEquals(
                "11-4-24", testComment.getDate(), "Ensure setDate is correctly setting the comment date");
    }

    @Test
    public void testSetCommentID() {
        User testUser = new User("testUser", "password", "New User",
                "testUserEmail@email.com");
        Comment testComment = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, 1,
                new Comment[2]);
        testComment.setCommentId(9876);

        assertEquals(9876, testComment.getCommentId(), "Ensure setCommentID is correctly setting the commentId");
    }

    @Test
    public void testSetCommentVotes() {
        User testUser = new User("testUser", "password", "New User",
                "testUserEmail@email.com");
        Comment testComment = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, 1,
                new Comment[2]);
        testComment.setVotes(10);

        assertEquals(10, testComment.getVotes(), "Ensure setVotes is correctly setting the comment votes");
    }

    @Test
    public void testSetCommentComments() {
        User testUser = new User("testUser", "password", "New User",
                "testUserEmail@email.com");
        Comment testComment = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, 1,
                new Comment[2]);
        testComment.setComments(new Comment[10]);

        assertArrayEquals(new Comment[10], testComment.getComments(),
                "Ensure setComments is correctly setting the comment array");
    }

    @Test
    public void testCommentEquals() {
        User testUser = new User("testUser", "password", "New User",
                "testUserEmail@email.com");
        Comment testComment = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, 1,
                new Comment[2]);
        Comment testDifferent = new Comment("Different comment", testUser, "11-3-24",
                5678, 2, 1, new Comment[2]);

        assertFalse(testComment.equals(testDifferent),
                "Ensure the Comment equals method is correctly returning false when comparing  two different Comments");
    }
}
