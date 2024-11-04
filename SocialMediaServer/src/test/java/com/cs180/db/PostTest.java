package com.cs180.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * A class that provides test cases for the Post class.
 * 
 * @author Zayan, L17
 * 
 * @version November 2nd, 2024
 */
public class PostTest {

        /**
         * A test case for the Post constructor.
         * 
         * @see Post#Post(String, String, String, int, int, String)
         */
        @Test
        public void testPostConstructor() {
                Post testPost = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3,
                                "https://www.mahitm.com/cdn/v1/post/1234");

                assertNotNull(testPost, "Ensure the constructor is actually instantiating variables");
        }

        /**
         * A test case for the Post constructor.
         * 
         * @see Post#Post(String, String, String, int, int, String)
         */
        @Test
        public void testGetMessagePost() {
                Post testPost = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3,
                                "https://www.mahitm.com/cdn/v1/post/1234");

                assertEquals("I love CS180!", testPost.getMessagePost(),
                                "Ensure getMessagePost is correctly getting the post message");
        }

        /**
         * A test case for the Post constructor.
         * 
         * @see Post#Post(String, String, String, int, int, String)
         */
        @Test
        public void testGetPostUsername() {
                Post testPost = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3,
                                "https://www.mahitm.com/cdn/v1/post/1234");

                assertEquals("testUser",
                                testPost.getUsername(), "Ensure getUsername is correctly getting the post");
        }

        /**
         * A test case for the Post constructor.
         * 
         * @see Post#Post(String, String, String, int, int, String)
         */
        @Test
        public void testGetPostDate() {
                Post testPost = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3,
                                "https://www.mahitm.com/cdn/v1/post/1234");

                assertEquals(
                                "12-12-24", testPost.getDate(), "Ensure getDate is correctly getting the post date");
        }

        /**
         * A test case for the Post constructor.
         * 
         * @see Post#Post(String, String, String, int, int, String)
         */
        @Test
        public void testGetPostID() {
                Post testPost = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3,
                                "https://www.mahitm.com/cdn/v1/post/1234");

                assertEquals(
                                1234, testPost.getPostId(), "Ensure getPostID is correctly getting the post id");
        }

        /**
         * A test case for the Post constructor.
         * 
         * @see Post#Post(String, String, String, int, int, String)
         */
        @Test
        public void testGetPostVotes() {
                Post testPost = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3,
                                "https://www.mahitm.com/cdn/v1/post/1234");

                assertEquals(3,
                                testPost.getVotes(), "Ensure getVotes is correctly getting the post votes");
        }

        /**
         * A test case for the Post constructor.
         * 
         * @see Post#Post(String, String, String, int, int, String)
         */
        @Test
        public void testGetPostImageURL() {
                Post testPost = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3,
                                "https://www.mahitm.com/cdn/v1/post/1234");

                assertEquals("https://www.mahitm.com/cdn/v1/post/1234",
                                testPost.getImageURL(), "Ensure getImageURL is correctly getting the post image url");
        }

        /**
         * A test case for the Post constructor.
         * 
         * @see Post#Post(String, String, String, int, int, String)
         */
        @Test
        public void testSetMessagePost() {
                Post testPost = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3,
                                "https://www.mahitm.com/cdn/v1/post/1234");
                testPost.setMessagePost("I hate CS180.");

                assertEquals("I hate CS180.",
                                testPost.getMessagePost(),
                                "Ensure setMessagePost is correctly setting the post message");
        }

        /**
         * A test case for the Post constructor.
         * 
         * @see Post#Post(String, String, String, int, int, String)
         */
        @Test
        public void testSetPostUsername() {
                Post testPost = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3,
                                "https://www.mahitm.com/cdn/v1/post/1234");

                testPost.setUsername("newUser");
                assertEquals("newUser", testPost.getUsername(),
                                "Ensure setUsername is correctly setting the postusername");
        }

        /**
         * A test case for the Post constructor.
         * 
         * @see Post#Post(String, String, String, int, int, String)
         */
        @Test
        public void testSetPostDate() {
                Post testPost = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3,
                                "https://www.mahitm.com/cdn/v1/post/1234");

                testPost.setDate("11-3-24");
                assertEquals(
                                "11-3-24", testPost.getDate(), "Ensure setDate is correctly setting the post date");
        }

        /**
         * A test case for the Post constructor.
         * 
         * @see Post#Post(String, String, String, int, int, String)
         */
        @Test
        public void testSetPostID() {
                Post testPost = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3,
                                "https://www.mahitm.com/cdn/v1/post/1234");

                testPost.setPostId(9876);
                assertEquals(
                                9876, testPost.getPostId(), "Ensure setPostID is correctly setting the post id");
        }

        /**
         * A test case for the Post constructor.
         * 
         * @see Post#Post(String, String, String, int, int, String)
         */
        @Test
        public void testSetPostIDTODO() {
                Post testPost = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3,
                                "https://www.mahitm.com/cdn/v1/post/1234");

                testPost.setPostId(9876);
                assertEquals(
                                9876, testPost.getPostId(), "Ensure setPostID is correctly setting the post id");
        }

        /**
         * A test case for the Post constructor.
         * 
         * @see Post#Post(String, String, String, int, int, String)
         */
        @Test
        public void testSetPostVotes() {
                Post testPost = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3,
                                "https://www.mahitm.com/cdn/v1/post/1234");

                testPost.setVotes(10);
                assertEquals(
                                10, testPost.getVotes(), "Ensure setVotes is correctly setting the post votes");
        }

        /**
         * A test case for the Post constructor.
         * 
         * @see Post#Post(String, String, String, int, int, String)
         */
        @Test
        public void testSetPostImageURL() {
                Post testPost = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3,
                                "https://www.mahitm.com/cdn/v1/post/1234");

                String updatedImageURL = "\"https://www.cloudinary.com/cdn/v1/post/1234";
                testPost.setImageURL(updatedImageURL);
                assertEquals(updatedImageURL,
                                testPost.getImageURL(), "Ensure setImageURL is correctly setting the post image URL");
        }

        /**
         * A test case for the Post constructor.
         * 
         * @see Post#Post(String, String, String, int, int, String)
         */
        @Test
        public void testPostEquals() {
                Post testPost = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3,
                                "https://www.mahitm.com/cdn/v1/post/1234");

                Post testPostTwo = new Post("I hate CS180!", "testUser", "12-12-24", 1234, 3,
                                "imageUrl.testPost");
                assertFalse(testPost.equals(testPostTwo),
                                "Ensure the Post equals method is correctly returning false when comparing two different Posts");

                Post testPostDuplicate = new Post("I love CS180!", "testUser", "12-12-24", 1234, 3,
                                "https://www.mahitm.com/cdn/v1/post/1234");
                assertEquals(testPostDuplicate, testPost);
        }
}
