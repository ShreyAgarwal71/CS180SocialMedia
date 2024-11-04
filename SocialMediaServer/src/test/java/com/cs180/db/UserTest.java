package com.cs180.db;

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
 * @author Zayan and Mahit Mehta
 * @version 2024-11-03
 */
public class UserTest {
	/**
	 * A test case for the User constructor.
	 * 
	 * @see User#User(String, String, String, String)
	 */
	@Test
	public void testUserConstructor() {
		User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
		assertNotNull(testUser, "User Constructor is not instantiating variables properly.");
	}

	/**
	 * A test case for the User constructor.
	 * 
	 * @see User#User(String, String, String, String)
	 */
	@Test
	public void testGetUsername() {
		User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
		assertEquals("testUser", testUser.getUsername(), "Ensure getUsername is returning proper username.");
	}

	/**
	 * A test case for the User constructor.
	 * 
	 * @see User#User(String, String, String, String)
	 */
	@Test
	public void testGetPassword() {
		User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
		assertEquals("password", testUser.getPassword(), "Ensure getPassword is returning proper password.");
	}

	/**
	 * A test case for the User constructor.
	 * 
	 * @see User#User(String, String, String, String)
	 */
	@Test
	public void testGetDisplayName() {
		User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
		assertEquals("New User",
				testUser.getDisplayName(), "Ensure getDisplayName is returning proper display name.");
	}

	/**
	 * A test case for the User constructor.
	 * 
	 * @see User#User(String, String, String, String)
	 */
	@Test
	public void testGetEmail() {
		User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
		assertEquals("testUserEmail@email.com", testUser.getEmail(), "Ensure getEmail is returning proper email.");
	}

	/**
	 * A test case for the User constructor.
	 * 
	 * @see User#User(String, String, String, String)
	 */
	@Test
	public void testSetUsername() {
		User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
		testUser.setUsername("newUsername");
		assertEquals("newUsername", testUser.getUsername(), "Ensure setUsername is properly setting new username.");
	}

	/**
	 * A test case for the User constructor.
	 * 
	 * @see User#User(String, String, String, String)
	 */
	@Test
	public void testPassword() {
		User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
		testUser.setPassword("newPassword");
		assertEquals("newPassword", testUser.getPassword(), "Ensure setPassword is properly setting new password.");
	}

	/**
	 * A test case for the User constructor.
	 * 
	 * @see User#User(String, String, String, String)
	 */
	@Test
	public void testSetDisplayName() {
		User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
		testUser.setDisplayName("Old User");
		assertEquals("Old User",
				testUser.getDisplayName(), "Ensure setDisplayName is properly setting new display name.");
	}

	/**
	 * A test case for the User constructor.
	 * 
	 * @see User#User(String, String, String, String)
	 */
	@Test
	public void testSetEmail() {
		User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
		testUser.setEmail("newEmail@gmail.com");
		assertEquals("newEmail@gmail.com", testUser.getEmail(), "Ensure setEmail is properly setting new email.");
	}

	/**
	 * A test case for the User constructor.
	 * 
	 * @see User#User(String, String, String, String)
	 */
	@Test
	public void testUserEqualsMethod() {
		User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
		User newTest = new User("testUser", "password", "New User", "testUserEmail@email.com");
		User testThree = new User("", "", "", "");
		assertTrue(
				testUser.equals(newTest),
				"Ensure the User equals method is returning true when comparing two identical User objects");
		assertFalse(
				testUser.equals(testThree),
				"Ensure the User equals method is returning false when comparing two different User objects");
	}

	/**
	 * A test case for the User constructor.
	 * 
	 * @see User#User(String, String, String, String)
	 */
	@Test
	public void testUserToString() {
		User testUser = new User("testUser", "password", "New User", "testUserEmail@email.com");
		assertEquals(
				"testUser,password,New User,testUserEmail@email.com", testUser.toString(),
				"Ensure the User toString method is printing in the correct format");
	}
}
