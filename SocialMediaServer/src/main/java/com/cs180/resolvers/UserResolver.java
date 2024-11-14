package com.cs180.resolvers;

import java.util.UUID;

import com.cs180.api.InternalServerError;
import com.cs180.api.Request;
import com.cs180.db.models.Post;
import com.cs180.resolvers.ResolverTools.AuthGuard;
import com.cs180.resolvers.ResolverTools.BaseResolver;
import com.cs180.resolvers.ResolverTools.Endpoint;
import com.cs180.resolvers.ResolverTools.Resolver;
import com.cs180.services.UserService;

import com.cs180.dtos.PostsDTO;
import com.cs180.dtos.ProfileNameDTO;
import com.cs180.dtos.UserPostsDTO;
import com.cs180.dtos.CreateUserDTO;
import com.cs180.dtos.DeleteUserDTO;
import com.cs180.dtos.BlockUserDTO;
import com.cs180.dtos.UnblockUserDTO;
import com.cs180.dtos.FollowUserDTO;
import com.cs180.dtos.UnfollowUserDTO;

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
        UUID userId = request.getBody().getUserId();

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

    @AuthGuard()
    @Endpoint(endpoint = "/getPosts", method = Request.EMethod.GET, requestBodyType = UserPostsDTO.class, responseBodyType = PostsDTO.class)
    public void getPosts(Request<UserPostsDTO> request) {
        UUID userId = request.getUserId();

        PostsDTO posts = new PostsDTO(UserService.getPosts(userId));
        if (UserService.getPosts(userId) == null) {
            throw new InternalServerError("Failed to Add User to Blocked List");
        }

    }

    @AuthGuard()
    @Endpoint(endpoint = "/updateProfileName", method = Request.EMethod.POST, requestBodyType = ProfileNameDTO.class)
    public void updateProfileName(Request<ProfileNameDTO> request) {
        UUID userId = request.getUserId();
        String name = request.getBody().getName();

        if (!UserService.updateProfileName(userId, name)) {
            throw new InternalServerError("Failed to Update Profile Name");
        }
    }

}
