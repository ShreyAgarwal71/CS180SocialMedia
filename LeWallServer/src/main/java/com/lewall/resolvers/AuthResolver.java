package com.lewall.resolvers;

import com.lewall.api.BadRequest;
import com.lewall.api.InternalServerError;
import com.lewall.api.Request;
import com.lewall.api.Request.EMethod;
import com.lewall.db.models.User;
import com.lewall.dtos.LoginDTO;
import com.lewall.dtos.CreateUserDTO;
import com.lewall.dtos.AuthTokenDTO;
import com.lewall.resolvers.ResolverTools.BaseResolver;
import com.lewall.resolvers.ResolverTools.Endpoint;
import com.lewall.resolvers.ResolverTools.Resolver;
import com.lewall.services.AuthService;

@Resolver(basePath = "/auth")
public class AuthResolver implements BaseResolver {

    /**
     * Sign up with email, password, username, display name, and bio
     * 
     * @throws BadRequest
     *             if email, password, or username is empty
     * @throws BadRequest
     *             if user with given email or username already exists
     * @throws InternalServerError
     *             if unable to register
     * 
     * @param request
     *            {@link Request} with {@link CreateUserDTO} body
     * @return {@link AuthTokenDTO}
     */
    @Endpoint(endpoint = "/register", method = EMethod.POST, requestBodyType = CreateUserDTO.class, responseBodyType = AuthTokenDTO.class)
    public AuthTokenDTO signUp(Request<CreateUserDTO> request) {
        String email = request.getBody().getEmail();
        String password = request.getBody().getPassword();
        String username = request.getBody().getUsername();
        String displayName = request.getBody().getDisplayName();
        String bio = request.getBody().getBio();

        if (email == null || password == null || username == null || email.isEmpty() || password.isEmpty()
                || username.isEmpty()) {
            throw new BadRequest("Invalid email, password, or username format.");
        }

        User user = AuthService.signUp(username, password, displayName, bio, email.toLowerCase());
        if (user == null) {
            throw new InternalServerError("Unable to Register Temporarily.");
        }

        String token = AuthService.generateAccessToken(user.getId().toString());
        return new AuthTokenDTO(token);
    }

    /**
     * Sign in with email and password
     * 
     * @throws BadRequest
     *             if email or password is empty
     * @throws BadRequest
     *             if no user is found with email and password combination
     * @throws InternalServerError
     *             if unable to login
     * 
     * @param request
     *            {@link Request} with {@link LoginDTO} body
     * @return {@link AuthTokenDTO}
     */
    @Endpoint(endpoint = "/login", method = EMethod.POST, requestBodyType = LoginDTO.class, responseBodyType = AuthTokenDTO.class)
    public AuthTokenDTO signInWithEmailAndPassword(Request<LoginDTO> request) {
        String email = request.getBody().getEmail();
        String password = request.getBody().getPassword();

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            throw new BadRequest("Invalid email or password format.");
        }

        User user = AuthService.signInWithEmailAndPassword(email.toLowerCase(), password);
        if (user == null) {
            throw new InternalServerError("Unable to Login Temporarily.");
        }

        String token = AuthService.generateAccessToken(user.getId().toString());
        return new AuthTokenDTO(token);
    }
}
