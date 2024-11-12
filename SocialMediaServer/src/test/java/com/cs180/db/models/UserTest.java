package com.cs180.db.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

		assertEquals(testUser.getUsername(), "username", "Ensure the getter is working for username");
		assertEquals(testUser.getPassword(), "password", "Ensure the getter is working for password");
		assertEquals(testUser.getDisplayName(), "displayName", "Ensure the getter is working for display name");
		assertEquals(testUser.getBio(), "bio", "Ensure the getter is working for bio");
		assertEquals(testUser.getEmail(), "email", "Ensure the getter is working for email");
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
}
