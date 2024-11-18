package com.lewall.resolvers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.lewall.api.Request;
import com.lewall.api.BadRequest;
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
		Database.init("temp_users.txt", "temp_posts.txt", "temp_comments.txt");
	}

	/**
	 * Cleans up the test database 
	 */
	@AfterAll
	public static void cleanupTestDB() {
		Database.cleanup("temp_users.txt", "temp_posts.txt", "temp_comments.txt");
	}

	/**
	 * Tests the signUp() method
	 */
	@Test
	public void signUpTest() {
		// Valid user
		CreateUserDTO user = new CreateUserDTO(
												"Alice", "patsoburger19", 
												"alice_2", "bio", "alice@foo.bar");
		Request<CreateUserDTO> req = new Request<CreateUserDTO>(
													EMethod.POST, "/auth/register", user, null);

		AuthResolver ar = new AuthResolver();
		AuthTokenDTO token = ar.signUp(req);

		assertNotNull(token, "Ensure this is a token");

		// Null fields
		Executable e = new Executable() {
			@Override
			public void execute() throws Throwable {
				ar.signUp(new Request<CreateUserDTO>(
													EMethod.POST, "/auth/register", 
													new CreateUserDTO(null, null, 
																		null, null, null),
													 null));
			}
		};
		assertThrows(BadRequest.class, e);

		// Empty fields
		e = new Executable() {
			@Override
			public void execute() throws Throwable {
				ar.signUp(new Request<CreateUserDTO>(
													EMethod.POST, "/auth/register", 
													new CreateUserDTO("", "", 
																		"", "", ""),
													null));
			}
		};
		assertThrows(BadRequest.class, e);
	}

	/**
	 * Tests the signInWithEmailAndPassword() method
	 */
	@Test
	public void signInWithEmailAndPasswordTest() {
		// Valid sign in
		AuthResolver ar = new AuthResolver();
		CreateUserDTO user = new CreateUserDTO(
												"recep-ivedik", "patsoburger19", 
												"bohohohoyt", "bio", "recep@foo.com");
		Request<CreateUserDTO> req2 = new Request<CreateUserDTO>(
													EMethod.POST, "/auth/register", user, null);

		ar.signUp(req2);

		LoginDTO body = new LoginDTO(user.getEmail(), user.getPassword());

		Request<LoginDTO> req = new Request<LoginDTO>(
													EMethod.POST, "/auth/login", body, null);


		AuthTokenDTO token = ar.signInWithEmailAndPassword(req);
		assertNotNull(token, "Ensure this is a token");
		
		// Null fields
		Executable e = new Executable() {
			@Override
			public void execute() throws Throwable {
				ar.signInWithEmailAndPassword(new Request<LoginDTO>(
													EMethod.POST, "/auth/login", 
													new LoginDTO(null, null), null));
			}
		};
		assertThrows(BadRequest.class, e);
		// Empty fields
		e = new Executable() {
			@Override
			public void execute() throws Throwable {
				ar.signInWithEmailAndPassword(new Request<LoginDTO>(
													EMethod.POST, "/auth/login", 
													new LoginDTO("", ""), null));
			}
		};
		assertThrows(BadRequest.class, e);
	}
}
