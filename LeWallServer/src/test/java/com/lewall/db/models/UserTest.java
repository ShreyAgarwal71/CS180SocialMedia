package com.lewall.db.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * UserTest
 *
 * A class that provides test cases for the User class.
 *
 * @author Shrey Agarwal and Mahit Mehta
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

		Set<String> followers = new HashSet<>();
		followers.add("follower1");
		followers.add("follower2");

		Set<String> blockedUsers = new HashSet<>();
		blockedUsers.add("blockedUser1");
		blockedUsers.add("blockedUser2");

		Set<String> following = new HashSet<>();
		following.add("following1");
		following.add("following2");

		testUser.setFollowers(followers);
		testUser.setBlockedUsers(blockedUsers);
		testUser.setFollowing(following);

		assertEquals("username", testUser.getUsername(), "Ensure the getter is working for username");
		assertEquals("password", testUser.getPassword(), "Ensure the getter is working for password");
		assertEquals("displayName", testUser.getDisplayName(), "Ensure the getter is working for display name");
		assertEquals("bio", testUser.getBio(), "Ensure the getter is working for bio");
		assertEquals("email", testUser.getEmail(), "Ensure the getter is working for email");
		assertEquals(followers, testUser.getFollowers(), "Ensure the getter is working for followers");
		assertEquals(blockedUsers, testUser.getBlockedUsers(), "Ensure the getter is working for blocked users");
		assertEquals(following, testUser.getFollowing(), "Ensure the getter is working for following");
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

		assertEquals("newUsername", testUser.getUsername(), "Ensure the setter is working for username");
		assertEquals("newPassword", testUser.getPassword(), "Ensure the setter is working for password");
		assertEquals("newDisplayName", testUser.getDisplayName(), "Ensure the setter is working for display name");
		assertEquals("newBio", testUser.getBio(), "Ensure the setter is working for bio");
		assertEquals("newEmail", testUser.getEmail(), "Ensure the setter is working for email");
	}

	/**
	 * A test case for the User addFollower, removeFollower, followUser, and
	 * unfollowUser methods.
	 */
	@Test
	public void testFollowers() {
		User testUser = new User("username", "password", "displayName", "bio", "email");

		testUser.addFollower("follower1");
		testUser.addFollower("follower2");
		Set<String> expectedFollowers = new HashSet<>();
		expectedFollowers.add("follower1");
		expectedFollowers.add("follower2");
		assertEquals(expectedFollowers, testUser.getFollowers(), "Ensure the addFollower method is working");

		testUser.removeFollower("follower1");
		expectedFollowers.remove("follower1");
		assertEquals(expectedFollowers, testUser.getFollowers(), "Ensure the removeFollower method is working");

		testUser.followUser("following1");
		Set<String> expectedFollowing = new HashSet<>();
		expectedFollowing.add("following1");
		assertEquals(expectedFollowing, testUser.getFollowing(), "Ensure the followUser method is working");

		testUser.unfollowUser("following1");
		expectedFollowing.remove("following1");
		assertEquals(expectedFollowing, testUser.getFollowing(), "Ensure the unfollowUser method is working");
	}

	/**
	 * A test case for the User addBlockedUser and removeBlockedUser methods.
	 */
	@Test
	public void testBlockedUsers() {
		User testUser = new User("username", "password", "displayName", "bio", "email");

		testUser.addBlockedUser("blockedUser1");
		testUser.addBlockedUser("blockedUser2");
		Set<String> expectedBlockedUsers = new HashSet<>();
		expectedBlockedUsers.add("blockedUser1");
		expectedBlockedUsers.add("blockedUser2");
		assertEquals(expectedBlockedUsers, testUser.getBlockedUsers(), "Ensure the addBlockedUser method is working");

		testUser.removeBlockedUser("blockedUser1");
		expectedBlockedUsers.remove("blockedUser1");
		assertEquals(expectedBlockedUsers, testUser.getBlockedUsers(),
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
		assertEquals("username,password,displayName,email", testUser.toString(),
				"Ensure the toString method is working");
	}
}
