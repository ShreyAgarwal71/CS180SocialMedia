package com.cs180.resolvers;

import com.cs180.api.InternalServerError;
import com.cs180.api.Request;
import com.cs180.api.Request.EMethod;
import com.cs180.db.models.User;
import com.cs180.dtos.LoginDTO;
import com.cs180.dtos.CreateUserDTO;
import com.cs180.dtos.AuthTokenDTO;
import com.cs180.resolvers.ResolverTools.BaseResolver;
import com.cs180.resolvers.ResolverTools.Endpoint;
import com.cs180.resolvers.ResolverTools.Resolver;
import com.cs180.services.AuthService;

@Resolver(basePath = "/auth")
public class AuthResolver implements BaseResolver {

    @Endpoint(endpoint = "/register", method = EMethod.POST, requestBodyType = CreateUserDTO.class, responseBodyType = AuthTokenDTO.class)
    public AuthTokenDTO signUp(Request<CreateUserDTO> request) {
        String email = request.getBody().getEmail();
        String password = request.getBody().getPassword();
        String username = request.getBody().getUsername();
        String displayName = request.getBody().getDisplayName();
        String bio = request.getBody().getBio();

        // TODO: Change db API to expose duplicate record (User) error
        User user = AuthService.signUp(username, password, displayName, bio, email);
        if (user == null) {
            return new AuthTokenDTO(null);
        }

        String token = AuthService.generateAccessToken(user.getId().toString());
        return new AuthTokenDTO(token);
    }

    @Endpoint(endpoint = "/login", method = EMethod.POST, requestBodyType = LoginDTO.class, responseBodyType = AuthTokenDTO.class)
    public AuthTokenDTO signInWithEmailAndPassword(Request<LoginDTO> request) {
        String email = request.getBody().getEmail();
        String password = request.getBody().getPassword();

        User user = AuthService.signInWithEmailAndPassword(email, password);
        if (user == null) {
            return new AuthTokenDTO(null);
        }

        String token = AuthService.generateAccessToken(user.getId().toString());
        return new AuthTokenDTO(token);
    }
}