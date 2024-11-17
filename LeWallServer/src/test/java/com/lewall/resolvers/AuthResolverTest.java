package com.lewall.resolvers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.lewall.api.Request;
import com.lewall.api.Request.EMethod;
import com.lewall.db.Database;
import com.lewall.dtos.AuthTokenDTO;
import com.lewall.dtos.CreateUserDTO;
import com.lewall.dtos.LoginDTO;

/*
 * A class to test the AuthResolver class
 *
 * @author Ates Isfendiyaroglu, L17
 * @version 16 November 2024
 */
public class AuthResolverTest {

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
		Database.cleanup();
	}

	/**
	 * Tests the signUp() method
	 */
	@Test
	public void signUpTest() {
		CreateUserDTO user = new CreateUserDTO(
												"Alice", "patsoburger19", 
												"alice_2", "bio", "foo@bar.com");
		Request<CreateUserDTO> req = new Request<CreateUserDTO>(
													EMethod.POST, "/auth/register", user, null);

		AuthResolver ar = new AuthResolver();
		AuthTokenDTO token = ar.signUp(req);

		assertNotNull(token, "Ensure this is a token");
	}

	/**
	 * Tests the signInWithEmailAndPassword() method
	 */
	@Test
	public void signInWithEmailAndPasswordTest() {
		LoginDTO body = new LoginDTO("foo@bar.com", "recep-ivedik-31");

		Request<LoginDTO> req = new Request<LoginDTO>(
													EMethod.POST, "/auth/login", body, null);

		AuthResolver ar = new AuthResolver();
		AuthTokenDTO token = ar.signInWithEmailAndPassword(req);
		assertNotNull(token, "Ensure this is a token");
	}
}
