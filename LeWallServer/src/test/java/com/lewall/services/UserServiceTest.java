package com.lewall.services;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import com.lewall.db.Database;

/**
 * A class to test the UserService class
 */
public class UserServiceTest {
	/**
	 * Initializes a temporary database to not mess up the production database files
	 */
	@BeforeAll
	public static void createTestDB() {
		Database.init("tmp_users.txt", "tmp_posts.txt", "tmp_comments.txt");
	}

	/**
	 * Cleans up the test database 
	 */
	@AfterAll
	public static void cleanupTestDB() {
		Database.cleanup();
	}
}
