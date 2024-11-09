package com.cs180.resolvers;

import com.cs180.api.Request;
import com.cs180.api.Request.EMethod;
import com.cs180.db.models.User;
import com.cs180.dtos.LoginDTO;
import com.cs180.dtos.AuthTokenDTO;
import com.cs180.resolvers.ResolverTools.BaseResolver;
import com.cs180.resolvers.ResolverTools.Endpoint;
import com.cs180.resolvers.ResolverTools.Resolver;
import com.cs180.services.AuthService;

@Resolver(basePath = "/auth")
public class AuthResolver extends BaseResolver {
    private static final AuthService authService = new AuthService();

    public AuthResolver() {
        new ResolverTools().super();
    }

    @Endpoint(endpoint = "/login", method = EMethod.POST, requestBodyType = LoginDTO.class, responseBodyType = AuthTokenDTO.class)
    public AuthTokenDTO signInWithEmailAndPassword(Request<LoginDTO> request) {
        String email = request.getBody().getEmail();
        String password = request.getBody().getPassword();

        User user = authService.signInWithEmailAndPassword(email, password);
        if (user == null) {
            return new AuthTokenDTO("dummy_token_invalid");
        }

        return new AuthTokenDTO("dummy_token");
    }
}
