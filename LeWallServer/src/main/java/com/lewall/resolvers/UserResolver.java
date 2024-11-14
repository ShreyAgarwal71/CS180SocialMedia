package com.lewall.resolvers;

import java.util.UUID;

import com.lewall.api.InternalServerError;
import com.lewall.api.Request;
import com.lewall.resolvers.ResolverTools.AuthGuard;
import com.lewall.resolvers.ResolverTools.BaseResolver;
import com.lewall.resolvers.ResolverTools.Endpoint;
import com.lewall.resolvers.ResolverTools.Resolver;
import com.lewall.services.UserService;

import com.lewall.dtos.CreateUserDTO;
import com.lewall.dtos.DeleteUserDTO;
import com.lewall.dtos.BlockUserDTO;
import com.lewall.dtos.UnblockUserDTO;
import com.lewall.dtos.FollowUserDTO;
import com.lewall.dtos.UnfollowUserDTO;

@Resolver(basePath = "/user")
public class UserResolver implements BaseResolver {

    @AuthGuard()
    @Endpoint(endpoint = "/create", method = Request.EMethod.POST, requestBodyType = CreateUserDTO.class)
    public void createUser(Request<CreateUserDTO> request) {
        String username = request.getBody().getUsername();
        String password = request.getBody().getPassword();
        String displayName = request.getBody().getDisplayName();
        String bio = request.getBody().getBio();
        String email = request.getBody().getEmail();

        if (!UserService.createUser(username, password, displayName, bio, email)) {
            throw new InternalServerError("Failed to Create User");
        }
    }

    @AuthGuard()
    @Endpoint(endpoint = "/delete", method = Request.EMethod.POST, requestBodyType = DeleteUserDTO.class)
    public void deleteUser(Request<DeleteUserDTO> request) {
        UUID userId = request.getUserId();

        if (!UserService.deleteUser(userId)) {
            throw new InternalServerError("Failed to Delete User");
        }
    }

    @AuthGuard()
    @Endpoint(endpoint = "/follow", method = Request.EMethod.POST, requestBodyType = FollowUserDTO.class)
    public void followUser(Request<FollowUserDTO> request) {
        UUID followUserId = request.getBody().getFollowUserId();
        UUID userId = request.getUserId();

        if (!UserService.follow(userId, followUserId)) {
            throw new InternalServerError("Failed to Add User to Follow List");
        }
    }

    @AuthGuard()
    @Endpoint(endpoint = "/unfollow", method = Request.EMethod.POST, requestBodyType = UnfollowUserDTO.class)
    public void unfollowUser(Request<UnfollowUserDTO> request) {
        UUID followUserId = request.getBody().getFollowUserId();
        UUID userId = request.getUserId();

        if (!UserService.unfollow(userId, followUserId)) {
            throw new InternalServerError("Failed to Add User to Follow List");
        }
    }

    @AuthGuard()
    @Endpoint(endpoint = "/block", method = Request.EMethod.POST, requestBodyType = BlockUserDTO.class)
    public void blockUser(Request<BlockUserDTO> request) {
        UUID blockedUserId = request.getBody().getBlockedUserId();
        UUID userId = request.getUserId();

        if (!UserService.block(userId, blockedUserId)) {
            throw new InternalServerError("Failed to Add User to Blocked List");
        }
    }

    @AuthGuard()
    @Endpoint(endpoint = "/unblock", method = Request.EMethod.POST, requestBodyType = UnblockUserDTO.class)
    public void unblockUser(Request<UnblockUserDTO> request) {
        UUID blockedUserId = request.getBody().getUnblockedUserId();
        UUID userId = request.getUserId();

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
