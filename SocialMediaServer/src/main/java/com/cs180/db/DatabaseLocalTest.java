package com.cs180.db;

import org.junit.*;

public class DatabaseLocalTest {

    // Beginning of User Class Test Cases
    @Test
    public void testUserConstructor() {
        User test = new User("testUser", "password", "New User", "testUserEmail@email.com");
        Assert.assertNotNull("User Constructor is not instantiating variables", test);
    }

    @Test
    public void testGetUsername() {
        User test = new User("testUser", "password", "New User", "testUserEmail@email.com");
        Assert.assertEquals("Ensure getUsername is returning proper username.", "testUser", test.getUsername());
    }
    @Test
    public void testGetPassword() {
        User test = new User("testUser", "password", "New User", "testUserEmail@email.com");
        Assert.assertEquals("Ensure getPassword is returning proper password.", "password", test.getPassword());
    }
    @Test
    public void testGetDisplayName() {
        User test = new User("testUser", "password", "New User", "testUserEmail@email.com");
        Assert.assertEquals("Ensure getDisplayName is returning proper display name.", "New User", test.getDisplayName());
    }
    @Test
    public void testGetEmail() {
        User test = new User("testUser", "password", "New User", "testUserEmail@email.com");
        Assert.assertEquals("Ensure getEmail is returning proper email.", "testUserEmail@email.com", test.getEmail());
    }
    @Test
    public void testSetUsername() {
        User test = new User("testUser", "password", "New User", "testUserEmail@email.com");
        test.setUsername("newUsername");
        Assert.assertEquals("Ensure setUsername is properly setting new username.", "newUsername", test.getUsername());
    }
    @Test
    public void testPassword() {
        User test = new User("testUser", "password", "New User", "testUserEmail@email.com");
        test.setPassword("newPassword");
        Assert.assertEquals("Ensure setPassword is properly setting new password.", "newPassword", test.getPassword());
    }
    @Test
    public void testSetDisplayName() {
        User test = new User("testUser", "password", "New User", "testUserEmail@email.com");
        test.setDisplayName("Old User");
        Assert.assertEquals("Ensure setDisplayName is properly setting new display name.", "Old User", test.getDisplayName());
    }
    @Test
    public void testSetEmail() {
        User test = new User("testUser", "password", "New User", "testUserEmail@email.com");
        test.setEmail("newEmail@gmail.com");
        Assert.assertEquals("Ensure setEmail is properly setting new email.", "newEmail@gmail.com", test.getEmail());
    }

    @Test
    public void testUserEqualsMethod() {
        User test = new User("testUser", "password", "New User", "testUserEmail@email.com");
        User newTest = new User("testUser", "password", "New User", "testUserEmail@email.com");
        User testThree = new User("", "", "", "");
        Assert.assertTrue("Ensure the User equals method is returning true when comparing two identical User objects", test.equals(newTest));
        Assert.assertFalse("Ensure the User equals method is returning false when comparing two different User objects", test.equals(testThree));
    }
    @Test
    public void testUserToString() {
        User test = new User("testUser", "password", "New User", "testUserEmail@email.com");
        Assert.assertEquals("Ensure the User toString method is printing in the correct format","testUser,password,New User,testUserEmail@email.com" , test.toString());
    }



    // Beginning of Post Test Cases
    @Test
    public void testPostConstructor() {
        Post test = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3, "imageUrl.test", new Comment[3]);
        Assert.assertNotNull("Ensure the constructor is actually instantiating variables", test);
    }

    @Test
    public void testGetMessagePost() {
        Post test = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3, "imageUrl.test", new Comment[3]);
        Assert.assertEquals("Ensure getMessagePost is correctly getting the post message","I love CS180!", test.getMessagePost());
    }
    @Test
    public void testGetPostUsername() {
        Post test = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3, "imageUrl.test", new Comment[3]);
        Assert.assertEquals("Ensure getUsername is correctly getting the post username", "testUser", test.getUsername());
    }
    @Test
    public void testGetPostDate() {
        Post test = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3, "imageUrl.test", new Comment[3]);
        Assert.assertEquals("Ensure getDate is correctly getting the post date","12-12-24", test.getDate());
    }
    @Test
    public void testGetPostID() {
        Post test = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3, "imageUrl.test", new Comment[3]);
        Assert.assertEquals("Ensure getPostID is correctly getting the post id",1234, test.getPostID());
    }
    @Test
    public void testGetPostVotes() {
        Post test = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3, "imageUrl.test", new Comment[3]);
        Assert.assertEquals("Ensure getVotes is correctly getting the post votes",3, test.getVotes());
    }
    @Test
    public void testGetPostImageURL() {
        Post test = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3, "imageUrl.test", new Comment[3]);
        Assert.assertEquals("Ensure getImageURL is correctly getting the post image url","imageUrl.test", test.getImageURL());
    }
    @Test
    public void testGetPostComments() {
        Post test = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3, "imageUrl.test", new Comment[3]);
        Assert.assertArrayEquals("Ensure getComments is correctly getting the post comments",new Comment[3], test.getComments());
    }
    @Test
    public void testSetMessagePost() {
        Post test = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3, "imageUrl.test", new Comment[3]);
        test.setMessagePost("I hate CS180.");
        Assert.assertEquals("Ensure setMessagePost is correctly setting the post message","I hate CS180.", test.getMessagePost());
    }
    @Test
    public void testSetPostUsername() {
        Post test = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3, "imageUrl.test", new Comment[3]);
        test.setUsername("newUser");
        Assert.assertEquals("Ensure setUsername is correctly setting the post username","newUser", test.getUsername());
    }
    @Test
    public void testSetPostDate() {
        Post test = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3, "imageUrl.test", new Comment[3]);
        test.setDate("11-3-24");
        Assert.assertEquals("Ensure setDate is correctly setting the post date","11-3-24", test.getDate());
    }
    @Test
    public void testSetPostID() {
        Post test = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3, "imageUrl.test", new Comment[3]);
        test.setPostID(9876);
        Assert.assertEquals("Ensure setPostID is correctly setting the post id",9876, test.getPostID());
    }
    @Test
    public void testSetPostVotes() {
        Post test = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3, "imageUrl.test", new Comment[3]);
        test.setVotes(10);
        Assert.assertEquals("Ensure setVotes is correctly setting the post votes",10, test.getVotes());
    }
    @Test
    public void testSetPostImageURL() {
        Post test = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3, "imageUrl.test", new Comment[3]);
        test.setImageURL("newImageURL.com");
        Assert.assertEquals("Ensure setImageURL is correctly setting the post image url","newImageURL.com", test.getImageURL());
    }
    @Test
    public void testSetPostComments() {
        Post test = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3, "imageUrl.test", new Comment[3]);
        test.setComments(new Comment[10]);
        Assert.assertArrayEquals("Ensure setComments is correctly setting the post comment array",new Comment[10], test.getComments());
    }
    @Test
    public void testPostEquals() {
        Post test = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3, "imageUrl.test", new Comment[3]);
        Post testTwo = new Post("I hate CS180!", "testUser", "12-12-24", 1234, 3, "imageUrl.test", new Comment[3]);
        Assert.assertFalse("Ensure the Post equals method is correctly returning false when comparing two different Posts", test.equals(testTwo));
    }


    // Beginning of Comment Test Cases
    @Test
    public void testCommentConstructor() {
        User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
        Comment test = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, new Comment[2]);
        Assert.assertNotNull("Ensure the constructor is actually instantiating variables", test);
    }

    @Test
    public void testGetMessageComment() {
        User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
        Comment test = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, new Comment[2]);
        Assert.assertEquals("Ensure getMessageComment is correctly getting the comment message", "This is a comment", test.getMessageComment());
    }

    @Test
    public void testGetCommentUser() {
        User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
        Comment test = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, new Comment[2]);
        Assert.assertEquals("Ensure getUser is correctly getting the comment user", testUser, test.getUser());
    }

    @Test
    public void testGetCommentDate() {
        User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
        Comment test = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, new Comment[2]);
        Assert.assertEquals("Ensure getDate is correctly getting the comment date", "11-3-24", test.getDate());
    }

    @Test
    public void testGetCommentID() {
        User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
        Comment test = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, new Comment[2]);
        Assert.assertEquals("Ensure getCommentID is correctly getting the comment ID", 5678, test.getCommentID());
    }

    @Test
    public void testGetCommentVotes() {
        User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
        Comment test = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, new Comment[2]);
        Assert.assertEquals("Ensure getVotes is correctly getting the comment votes", 2, test.getVotes());
    }

    @Test
    public void testGetCommentComments() {
        User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
        Comment test = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, new Comment[2]);
        Assert.assertArrayEquals("Ensure getComments is correctly getting the comment array", new Comment[2], test.getComments());
    }

    @Test
    public void testSetMessageComment() {
        User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
        Comment test = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, new Comment[2]);
        test.setMessageComment("Updated comment message");
        Assert.assertEquals("Ensure setMessageComment is correctly setting the comment message", "Updated comment message", test.getMessageComment());
    }

    @Test
    public void testSetCommentUser() {
        User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
        Comment test = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, new Comment[2]);
        User newUser = new User("newUser", "password123", "Another User", "newUserEmail@email.com");
        test.setUser(newUser);
        Assert.assertEquals("Ensure setUser is correctly setting the comment user", newUser, test.getUser());
    }

    @Test
    public void testSetCommentDate() {
        User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
        Comment test = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, new Comment[2]);
        test.setDate("11-4-24");
        Assert.assertEquals("Ensure setDate is correctly setting the comment date", "11-4-24", test.getDate());
    }

    @Test
    public void testSetCommentID() {
        User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
        Comment test = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, new Comment[2]);
        test.setCommentID(9876);
        Assert.assertEquals("Ensure setCommentID is correctly setting the comment ID", 9876, test.getCommentID());
    }

    @Test
    public void testSetCommentVotes() {
        User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
        Comment test = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, new Comment[2]);
        test.setVotes(10);
        Assert.assertEquals("Ensure setVotes is correctly setting the comment votes", 10, test.getVotes());
    }

    @Test
    public void testSetCommentComments() {
        User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
        Comment test = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, new Comment[2]);
        test.setComments(new Comment[10]);
        Assert.assertArrayEquals("Ensure setComments is correctly setting the comment array", new Comment[10], test.getComments());
    }

    @Test
    public void testCommentEquals() {
        User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
        Comment test = new Comment("This is a comment", testUser, "11-3-24", 5678, 2, new Comment[2]);
        Comment testDifferent = new Comment("Different comment", testUser, "11-3-24", 5678, 2, new Comment[2]);
        Assert.assertFalse("Ensure the Comment equals method is correctly returning false when comparing two different Comments", test.equals(testDifferent));
    }


    // Beginning of Database Test Cases
}
