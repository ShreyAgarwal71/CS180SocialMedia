package com.cs180.db.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * UserTest
 * 
 * A class that provides test cases for the User class.
 * 
 * @author Zayan and Mahit Mehta and Shrey Agarwal
 * @version 11/12/2024
 */
public class UserTest {
	/**
	 * A test case for the User constructor.
	 *
	 * @see User#User(String, String, String, String, String)
	 */
	@Test
	public void testUserConstructor() {
		User testUser = new User("username", "password", "displayName", "bio", "email");

		assertNotNull(testUser, "Ensure the constructor is actually instantiating variables");
	}

	/**
	 * A test case for the User getters.
	 */
	@Test
	public void testGetters() {
		User testUser = new User("username", "password", "displayName", "bio", "email");
		testUser.setFollowers(new String[] { "follower1", "follower2" });
		testUser.setBlockedUsers(new String[] { "blockedUser1", "blockedUser2" });
		testUser.setFollowing(new String[] { "following1", "following2" });

		assertEquals(testUser.getUsername(), "username", "Ensure the getter is working for username");
		assertEquals(testUser.getPassword(), "password", "Ensure the getter is working for password");
		assertEquals(testUser.getDisplayName(), "displayName", "Ensure the getter is working for display name");
		assertEquals(testUser.getBio(), "bio", "Ensure the getter is working for bio");
		assertEquals(testUser.getEmail(), "email", "Ensure the getter is working for email");
		assertEquals(Arrays.toString(testUser.getFollowers()),
				Arrays.toString(new String[] { "follower1", "follower2" }),
				"Ensure the getter is working for followers");
		assertEquals(Arrays.toString(testUser.getBlockedUsers()),
				Arrays.toString(new String[] { "blockedUser1", "blockedUser2" }),
				"Ensure the getter is working for blocked users");
		assertEquals(Arrays.toString(testUser.getFollowing()),
				Arrays.toString(new String[] { "following1", "following2" }),
				"Ensure the getter is working for following");

	}

	/**
	 * A test case for the User setters.
	 */
	@Test
	public void testSetters() {
		User testUser = new User("username", "password", "displayName", "bio", "email");

		testUser.setUsername("newUsername");
		testUser.setPassword("newPassword");
		testUser.setDisplayName("newDisplayName");
		testUser.setBio("newBio");
		testUser.setEmail("newEmail");

		assertEquals(testUser.getUsername(), "newUsername", "Ensure the setter is working for username");
		assertEquals(testUser.getPassword(), "newPassword", "Ensure the setter is working for password");
		assertEquals(testUser.getDisplayName(), "newDisplayName", "Ensure the setter is working for display name");
		assertEquals(testUser.getBio(), "newBio", "Ensure the setter is working for bio");
		assertEquals(testUser.getEmail(), "newEmail", "Ensure the setter is working for email");
	}

	/**
	 * A test case for the User addFollower, removeFollower, followUser and
	 * unfollowUser method.
	 */
	@Test
	public void testFollowers() {
		User testUser = new User("username", "password", "displayName", "bio", "email");

		testUser.addFollower("follower1");
		testUser.addFollower("follower2");

		assertEquals(Arrays.toString(testUser.getFollowers()),
				Arrays.toString(new String[] { "follower1", "follower2" }),
				"Ensure the addFollower method is working");

		testUser.removeFollower("follower1");

		assertEquals(Arrays.toString(testUser.getFollowers()), Arrays.toString(new String[] { "follower2" }),
				"Ensure the removeFollower method is working");

		testUser.followUser("following1");

		assertEquals(Arrays.toString(testUser.getFollowing()), Arrays.toString(new String[] { "following1" }),
				"Ensure the followUser method is working");

		testUser.unfollowUser("following1");

		assertEquals(Arrays.toString(testUser.getFollowing()), Arrays.toString(new String[] {}),
				"Ensure the unfollowUser method is working");
	}

	/**
	 * A test case for the User addBlockedUser and removeBlockedUser method.
	 */
	@Test
	public void testBlockedUsers() {
		User testUser = new User("username", "password", "displayName", "bio", "email");

		testUser.addBlockedUser("blockedUser1");
		testUser.addBlockedUser("blockedUser2");

		assertEquals(Arrays.toString(testUser.getBlockedUsers()),
				Arrays.toString(new String[] { "blockedUser1", "blockedUser2" }),
				"Ensure the addBlockedUser method is working");

		testUser.removeBlockedUser("blockedUser1");

		assertEquals(Arrays.toString(testUser.getBlockedUsers()), Arrays.toString(new String[] { "blockedUser2" }),
				"Ensure the removeBlockedUser method is working");
	}

	/**
	 * A test case for the User equals and toString methods.
	 */
	@Test
	public void testEqualsToString() {
		User testUser = new User("username", "password", "displayName", "bio", "email");
		User testUser2 = new User("username", "password", "displayName", "bio", "email");
		User testUser3 = new User("username1", "password", "displayName", "bio", "email");

		assertTrue(testUser.equals(testUser2), "Ensure the equals method is working");
		assertFalse(testUser.equals(testUser3), "Ensure the equals method is working");

		assertEquals(testUser.toString(),
				"username,password,displayName,email",
				"Ensure the toString method is working");
	}
}
