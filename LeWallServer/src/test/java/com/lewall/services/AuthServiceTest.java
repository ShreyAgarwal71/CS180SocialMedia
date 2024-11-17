package com.lewall.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.lewall.api.BadRequest;

import com.lewall.db.Database;
import com.lewall.db.models.User;

/**
 * A class to test the AuthService class
 *
 * @author Ates Isfendiyaroglu, L17
 * @version 16 November 2024
 */
public class AuthServiceTest {
	/**
	 * Initializes a temporary database to not mess up the production database files
	 */
	@BeforeAll
	public static void createTestDB() {
		Database.init("temp-users.txt", "temp-posts.txt", "temp-comments.txt");
	}

	/**
	 * Cleans up the test database 
	 */
	@AfterAll
	public static void cleanupTestDB() {
		Database.cleanup("temp-users.txt", "temp-posts.txt", "temp-comments.txt");
	}

	/**
	 * Tests the signUp() method
	 */
	@Test
	public void signUpTest() {
		String username = "alice";
		String password = "qwe123asd";
		String displayName = "cimbombom1905";
		String bio = "hello";
		String email = "foo@bar.com";

		User u1 = AuthService.signUp(username, password, displayName, bio, email);
		User u2 = new User(username, password, displayName, bio, email);

		// Password's hashed thus not equal
		assertTrue(u1.getUsername().equals(u2.getUsername()));
		assertTrue(u1.getDisplayName().equals(u2.getDisplayName()));
		assertTrue(u1.getBio().equals(u2.getBio()));
		assertTrue(u1.getEmail().equals(u2.getEmail()));

		// Test adding user with the same username
		Executable e = new Executable() {
			@Override
			public void execute() throws Throwable {
				AuthService.signUp("alice", "qwe123asd", "messi", "bio", "lio@messi.edu");
			}
		};
		assertThrows(BadRequest.class, e);
		// Test adding user with the same email
		e = new Executable() {
			@Override
			public void execute() throws Throwable {
				AuthService.signUp("lebronita", "qwe123asd", "james", "bio", "foo@bar.com");
			}
		};
		assertThrows(BadRequest.class, e);
	}

	/**
	 * Tests the signInWithEmailAndPassword() method
	 */
	@Test
	public void signInWithEmailAndPasswordTest() {
		Database db = new Database();	
		String username = "alice";
		String password = "qwe123asd";
		String displayName = "cimbombom1905";
		String bio = "hello";
		String email = "foo@bar.com";
		
		User u1 = new User(username, password, displayName, bio, email);
		db.getUserCollection().addUser(u1);
		User u2 = AuthService.signInWithEmailAndPassword(email, password);

		// Password's hashed thus not equal
		assertTrue(u1.getUsername().equals(u2.getUsername()));
		assertTrue(u1.getDisplayName().equals(u2.getDisplayName()));
		assertTrue(u1.getBio().equals(u2.getBio()));
		assertTrue(u1.getEmail().equals(u2.getEmail()));
		
		// No user exists
		Executable e = new Executable() {
			@Override
			public void execute() throws Throwable {
				AuthService.signInWithEmailAndPassword("uglawbluw@ogur.edu", "ardfef1071");
			}
		};
		assertThrows(BadRequest.class, e);
	}

	/**
	 * Tests the generateAccessToken() method
	 */
	@Test
	public void generateAccessTokenTest() {
		assertNotNull(AuthService.generateAccessToken(UUID.randomUUID().toString()));
	}

	/**
	 * Tests the validateAccessToken() method
	 */
	@Test
	public void validateAccessTokenTest() {
		String token = AuthService.generateAccessToken(UUID.randomUUID().toString());
		assertNotNull(AuthService.validateAccessToken(token));
	}
}
