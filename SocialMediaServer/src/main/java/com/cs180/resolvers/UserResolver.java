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
    @Endpoint(endpoint = "/unfollow", method = Request.EMethod.POST, requestBodyType = UnfollowUserDTO.class)
    public void followUser(Request<UnFollowUserDTO> request) {
        UUID followUserId = UUID.fromString(request.getBody().getFollowUserId());
        UUID userId = UUID.randomUUID();

        if (!UserService.unfollow(userId, followUserId)) {
            throw new InternalServerError("Failed to Add User to Follow List");
        }
    }

    @AuthGuard()
    @Endpoint(endpoint = "/block", method = Request.EMethod.POST, requestBodyType = BlockUserDTO.class)
    public void followUser(Request<BlockUserDTO> request) {
        UUID blockedUserId = UUID.fromString(request.getBody().getBlockedUserId());
        UUID userId = UUID.randomUUID();

        if (!UserService.block(userId, blockedUserId)) {
            throw new InternalServerError("Failed to Add User to Blocked List");
        }
    }

    @AuthGuard()
    @Endpoint(endpoint = "/unblock", method = Request.EMethod.POST, requestBodyType = UnblockUserDTO.class)
    public void unblock(Request<UnblockUserDTO> request) {
        UUID blockedUserId = UUID.fromString(request.getBody().getBlockedUserId());
        UUID userId = UUID.randomUUID();

        if (!UserService.unblock(userId, blockedUserId)) {
            throw new InternalServerError("Failed to Add User to Blocked List");
        }
    }

    /*
     * @AuthGuard()
     * 
     * @Endpoint(endpoint = "/changedisplayname", method = Request.EMethod.POST,
     * requestBodyType = nameUserDTO.class)
     * public void changeName(Request<nameUserDTO> request) {
     * UUID userId = UUID.randomUUID();
     * //String newName = request.getBody().getFollowUserId();
     * 
     * if (!UserService.changeDisplayName(userId, newName)) {
     * throw new InternalServerError("Failed to Change Display Name");
     * }
     * }
     */

}
