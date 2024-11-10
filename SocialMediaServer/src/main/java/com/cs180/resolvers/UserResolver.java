package com.cs180.resolvers;

import java.util.UUID;

import com.cs180.api.InternalServerError;
import com.cs180.api.Request;
import com.cs180.resolvers.ResolverTools.AuthGuard;
import com.cs180.resolvers.ResolverTools.BaseResolver;
import com.cs180.resolvers.ResolverTools.Endpoint;
import com.cs180.resolvers.ResolverTools.Resolver;
import com.cs180.services.UserService;

import com.cs180.dtos.FollowUserDTO;

@Resolver(basePath = "/user")
public class UserResolver extends BaseResolver {
    public UserResolver() {
        new ResolverTools().super();
    }

    @AuthGuard()
    @Endpoint(endpoint = "/follow", method = Request.EMethod.POST, requestBodyType = FollowUserDTO.class)
    public void followUser(Request<FollowUserDTO> request) {
        UUID followUserId = UUID.fromString(request.getBody().getFollowUserId());
        UUID userId = UUID.randomUUID();

        if (!UserService.follow(userId, followUserId)) {
            throw new InternalServerError("Failed to Add User to Follow List");
        }
    }

    @AuthGuard()
    @Endpoint(endpoint = "/", method = Request.EMethod.POST, requestBodyType = FollowUserDTO.class)
    public void followUser2(Request<FollowUserDTO> request) {
        UUID followUserId = UUID.fromString(request.getBody().getFollowUserId());
        UUID userId = UUID.randomUUID();

        if (!UserService.follow(userId, followUserId)) {
            throw new InternalServerError("Failed to Add User to Follow List");
        }
    }
}
