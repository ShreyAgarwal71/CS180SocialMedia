package com.cs180.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import javax.xml.crypto.Data;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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

   // ================== Start -- PostCollection Tests ===================

   /**
    * Test primarily verifies that all posts for a given user are returned
    * 
    * {@link com.cs180.db.PostCollection#findByUsername(String)}
    */
   @Test
   public void queryPostsByUsername() {
      Database db = new Database();

      String testUserName = "testUserName";
      User testUser = new User(testUserName, "testPassword", "testDisplayName", "testEmail");
      db.getUserCollection().addElement(testUser);

      Post testPostOne = new Post("testMessageOne", testUserName, "testDateOne", 1, 0, "testImageURLOne");
      Post testPostTwo = new Post("testMessageTwo", testUserName, "testDateTwo", 2, 0, "testImageURLTwo");

      db.getPostCollection().addElement(testPostOne);
      db.getPostCollection().addElement(testPostTwo);

      List<Post> posts = db.getPostCollection().findByUsername(testUserName);

      assertEquals(2, posts.size(), "Expected 2 posts to be found");
   }

   /**
    * Test verifies that a duplicate post is not added to the collection
    * 
    */
   @Test
   public void preventsDuplicate() {
      Database db = new Database();

      String testUserName = "testUserName";

      Post testPostOne = new Post("testMessageOne", testUserName, "testDateOne", 1, 0, "testImageURLOne");
      Post testPostDuplicate = new Post("testMessageTwo", testUserName, "testDateTwo", 1, 0, "testImageURLTwo");

      db.getPostCollection().addElement(testPostOne);
      assertFalse(db.getPostCollection().addElement(testPostDuplicate), "Expected duplicate post to not be added");

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

      String testUserName = "testUserName";
      Post testPostOne = new Post("testMessageOne", testUserName, "testDateOne", 1, 0, "testImageURLOne");

      db.getPostCollection().addElement(testPostOne);

      int count = db.getPostCollection().count();
      assertEquals(1, count, "Expected 1 post to be found");

      db.getPostCollection().removeElement(testPostOne);
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

      String testUserName = "testUserName";
      Post testPostOne = new Post("testMessageOne", testUserName, "testDateOne", 1, 0, "testImageURLOne");

      db.getPostCollection().addElement(testPostOne);

      Post updatedPost = new Post("testMessageTwo", testUserName, "testDateTwo", 1, 0, "testImageURLTwo");

      db.getPostCollection().updateElement(testPostOne, updatedPost);

      Post post = db.getPostCollection().findOne(p -> p.getPostId() == testPostOne.getPostId());

      assertNotNull(post, "Expected post to be found");

      assertEquals("testImageURLTwo", post.getImageURL(), "Expected post image URL to be updated");
      assertEquals("testMessageTwo", post.getMessagePost(), "Expected post message to be updated");
   }

   // ================= End -- PostCollection Tests ====================

   // =============== Start -- CommentCollection Tests =================

   @Test
   public void queryCommentsByPostId() {
      Database db = new Database();

      String testUserName = "testUserName";
      int postId = 1;

      Comment testCommentOne = new Comment("testCommentOne", testUserName, "testDateOne", 0, postId, 0, new Comment[0]);
      Comment testCommentTwo = new Comment("testCommentTwo", testUserName, "testDateTwo", 1, postId, 0, new Comment[0]);

      db.getCommentCollection().addElement(testCommentOne);
      db.getCommentCollection().addElement(testCommentTwo);

      List<Comment> comments = db.getCommentCollection().commentsByPostId(postId);

      assertEquals(2, comments.size(), "Expected 2 comments to be found");
   }
}
