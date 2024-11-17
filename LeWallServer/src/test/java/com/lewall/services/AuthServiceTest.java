package com.lewall.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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

		assertTrue(u1.equals(u2));
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

		assertTrue(u1.equals(u2));
	}

	/**
	 * Tests the generateAccessToken() method
	 */
	@Test
	public void generateAccessTokenTest() {
		assertNotNull(AuthService.generateAccessToken("messi"));
	}

	/**
	 * Tests the validateAccessToken() method
	 */
	@Test
	public void validateAccessTokenTest() {
		String token = AuthService.generateAccessToken("messi");
		assertNotNull(AuthService.validateAccessToken(token));
	}
}
