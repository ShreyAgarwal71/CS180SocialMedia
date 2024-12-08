package com.lewall.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.lewall.api.BadRequest;
import com.lewall.db.Database;
import com.lewall.db.models.Post;
import com.lewall.db.models.User;

/**
 * A class to test the UserService class
 */
public class UserServiceTest {
	/**
	 * Initializes a temporary database to not mess up the production database files
	 */
	@BeforeAll
	public static void createTestDB() {
		Database.init("temporary_u.txt", "temporary_p.txt", "temporary_c.txt");
	}

	/**
	 * Cleans up the test database
	 */
	@AfterAll
	public static void cleanupTestDB() {
		Database.cleanup("temporary_u.txt", "temporary_p.txt", "temporary_c.txt");
	}

	/**
	 * Tests the follow functionality
	 */
	@Test
	public void followTest() {
		// Valid Follow
		User u1 = new User("bruh1", "123", "b1", "b", "bruh1@foo.bar");
		User u2 = new User("bruh2", "123", "b2", "b", "bruh2@foo.bar");
		UserService.db.getUserCollection().addUser(u1);
		UserService.db.getUserCollection().addUser(u2);

		User u2Updated = UserService.follow(u1.getId(), u2.getId());
		assertTrue(u2Updated.getFollowers().contains(u1.getId().toString()));

		// Invalid follow
		Executable e = new Executable() {
			@Override
			public void execute() throws Throwable {
				User u4 = new User("nulasd", "gl", "k", "kas", "piwa@ad.zxc");
				UserService.db.getUserCollection().addUser(u4);
				User u3 = new User("null-bruh", "g", "bruh", "asdf", "qwe@asd.zxc");
				UserService.follow(u4.getId(), u3.getId());
			}
		};

		assertThrows(BadRequest.class, e);
	}

	/**
	 * Tests the unfollow functionlity
	 */
	@Test
	public void unfollowTest() {
		// Valid Unfollow
		User u1 = new User("bruh21", "123", "b21", "b", "bruh21@foo.bar");
		User u2 = new User("bruh22", "123", "b22", "b", "bruh22@foo.bar");
		UserService.db.getUserCollection().addUser(u1);
		UserService.db.getUserCollection().addUser(u2);

		UserService.follow(u1.getId(), u2.getId());
		User u2Updated = UserService.unfollow(u1.getId(), u2.getId());
		assertFalse(u2Updated.getFollowers().contains(u1.getId().toString()));

		// Invalid Unfollow
		Executable e = new Executable() {
			@Override
			public void execute() throws Throwable {
				User u = UserService.db.getUserCollection().findByUsername("bruh21");
				User u3 = new User("-bruh-", "g", "gbruh", "asdf", "qsqwe@asd.zxc");
				UserService.unfollow(u.getId(), u3.getId());
			}
		};

		assertThrows(BadRequest.class, e);
	}

	/**
	 * Tests the block functionlity
	 */
	@Test
	public void blockTest() {
		// Valid Block
		User u1 = new User("bruh321", "123", "b321", "b", "bruh21z@foo.bar");
		User u2 = new User("bruh322", "123", "b322", "b", "bruh22z@foo.bar");
		UserService.db.getUserCollection().addUser(u1);
		UserService.db.getUserCollection().addUser(u2);

		UserService.follow(u1.getId(), u2.getId());
		assertTrue(UserService.block(u1.getId(), u2.getId()));

		// Invalid Block
		Executable e = new Executable() {
			@Override
			public void execute() throws Throwable {
				User u4 = new User("aghagh", "gl", "k", "asdf", "poqwes@asd.zxc");
				UserService.db.getUserCollection().addUser(u4);
				User u3 = new User("hjkl", "gl", "hjbruh", "asdf", "hjqwe@asd.zxc");
				UserService.block(u4.getId(), u3.getId());
			}
		};

		assertThrows(BadRequest.class, e);
	}

	/**
	 * Tests the unblock functionlity
	 */
	@Test
	public void unblockTest() {
		// Valid Unblock
		User u1 = new User("bruhzxc", "123", "z321", "b", "bruhzx@foo.bar");
		User u2 = new User("bruhbxc", "123", "x322", "b", "bruhzb@foo.bar");
		UserService.db.getUserCollection().addUser(u1);
		UserService.db.getUserCollection().addUser(u2);

		UserService.follow(u1.getId(), u2.getId());
		UserService.block(u1.getId(), u2.getId());
		assertTrue(UserService.unblock(u1.getId(), u2.getId()));

		// Invalid Block
		Executable e = new Executable() {
			@Override
			public void execute() throws Throwable {
				User u = UserService.db.getUserCollection().findByUsername("bruhzxc");
				User u3 = new User("ljh", "gl", "pois", "asdf", "upoi@asd.zxc");
				UserService.unblock(u.getId(), u3.getId());
			}
		};

		assertThrows(BadRequest.class, e);
	}

	/**
	 * Tests the update profile name functionlity
	 */
	@Test
	public void updateProfileNameTest() {
		// Valid Update
		User u1 = new User("bayern", "123", "Munich", "bm", "bayern@fc.de");
		UserService.db.getUserCollection().addUser(u1);
		String name = "Leverkusen";
		boolean result = UserService.updateProfileName(u1.getId(), name);

		assertTrue(result);
		assertTrue(u1.getDisplayName().equals(name));

		// Invalid Update
		Executable e = new Executable() {
			@Override
			public void execute() throws Throwable {
				User u2 = new User("uglq", "fl", "qqewkn", "asdf", "sndal@asd.zxc");
				UserService.updateProfileName(u2.getId(), "Leverkusen");
			}
		};

		assertThrows(BadRequest.class, e);
	}

	/**
	 * Tests the update profile bio functionlity
	 */
	@Test
	public void updateProfileBioTest() {
		// Valid Update
		User u1 = new User("gustav", "123", "kljh", "initial", "gustav@hog.nl");
		UserService.db.getUserCollection().addUser(u1);
		String bio = "second";
		boolean result = UserService.updateProfileBio(u1.getId(), bio);

		assertTrue(result);
		assertTrue(u1.getBio().equals(bio));

		// Invalid Update

		Executable e = new Executable() {
			@Override
			public void execute() throws Throwable {
				User u2 = new User("carl", "lkja", "kral", "initial", "koral@asdje.zpc");
				UserService.updateProfileBio(u2.getId(), "second");
			}
		};

		assertThrows(BadRequest.class, e);
	}

	/**
	 * Tests the getFollowingPosts method
	 */
	@Test
	public void getFollowingPostsTest() {
		User u1 = new User("user1", "qweqwe", "user1", "bio", "user1@hj.kl");
		User u2 = new User("user2", "qweqwe", "user2", "bio", "user2@hj.kl");
		User u3 = new User("user3", "qweqwe", "user3", "bio", "user3@hj.kl");
		User u4 = new User("user4", "qweqwe", "user4", "bio", "user3@hj.kl");

		UserService.db.getUserCollection().addUser(u1);
		UserService.db.getUserCollection().addUser(u2);
		UserService.db.getUserCollection().addUser(u3);
		UserService.db.getUserCollection().addUser(u4);

		UserService.follow(u1.getId(), u2.getId());
		UserService.follow(u1.getId(), u3.getId());

		String[] expectedMsg = { "msg1", "msg2", "msg3", "msg4" };
		String[] expectedDate = { "11/22/2024", "11/21/2024", "11/20/2024", "11/19/2024" };
		Post p1 = new Post(u2.getId(), "msg1", "11/22/2024", 0, 0, null, "RandomClass");
		Post p2 = new Post(u2.getId(), "msg2", "11/21/2024", 0, 0, null, "RandomClass1");
		Post p3 = new Post(u3.getId(), "msg3", "11/20/2024", 0, 0, null, "RandomClass");
		Post p4 = new Post(u3.getId(), "msg4", "11/19/2024", 0, 0, null, "RandomClass");
		Post p5 = new Post(u4.getId(), "msg5", "11/18/2024", 0, 0, null, "RandomClass");
		Post p6 = new Post(u4.getId(), "msg6", "11/17/2024", 0, 0, null, "RandomClass1");

		UserService.db.getPostCollection().addElement(p1);
		UserService.db.getPostCollection().addElement(p2);
		UserService.db.getPostCollection().addElement(p3);
		UserService.db.getPostCollection().addElement(p4);
		UserService.db.getPostCollection().addElement(p5);
		UserService.db.getPostCollection().addElement(p6);

		List<Post> results = UserService.getFollowingPosts(u1.getId());

		assertEquals(expectedMsg.length, results.size());

		for (int i = 0; i < results.size(); i++) {
			System.out.println(expectedMsg[i] + " " + results.get(i).getMessagePost());
			assertTrue(expectedMsg[i].equals(results.get(i).getMessagePost()));
			assertTrue(expectedDate[i].equals(results.get(i).getDate()));
		}
	}

	/**
	 * Tests getting posts by class ID
	 */
	@Test
	public void getClassFeedTest() {
		User u1 = new User("mj", "96bulls", "mjordan", "bio", "mj@bulls.us");
		UserService.db.getUserCollection().addUser(u1);

		String[] expectedMsg = { "msg1", "msg3" };
		String[] expectedDate = { "11/17/2024", "11/19/2024" };
		Post p1 = new Post(u1.getId(), "msg1", "11/17/2024", 0, 0, null, "RandomClass3");
		Post p2 = new Post(u1.getId(), "msg2", "11/18/2024", 0, 0, null, "RandomClass1");
		Post p3 = new Post(u1.getId(), "msg3", "11/19/2024", 0, 0, null, "RandomClass3");

		UserService.db.getPostCollection().addElement(p1);
		UserService.db.getPostCollection().addElement(p2);
		UserService.db.getPostCollection().addElement(p3);

		List<Post> results = UserService.getClassFeed(u1.getId(), "RandomClass3");
		assertEquals(expectedMsg.length, results.size());

		for (int i = 0; i < results.size(); i++) {
			assertTrue(expectedMsg[i].equals(results.get(i).getMessagePost()));
			assertTrue(expectedDate[i].equals(results.get(i).getDate()));
		}
	}
}
