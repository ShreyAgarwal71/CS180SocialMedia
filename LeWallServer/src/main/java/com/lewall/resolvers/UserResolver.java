package com.lewall.resolvers;

import java.util.UUID;

import com.lewall.api.InternalServerError;
import com.lewall.api.Request;
import com.lewall.db.models.User;
import com.lewall.resolvers.ResolverTools.AuthGuard;
import com.lewall.resolvers.ResolverTools.BaseResolver;
import com.lewall.resolvers.ResolverTools.Endpoint;
import com.lewall.resolvers.ResolverTools.Resolver;
import com.lewall.services.UserService;

import com.lewall.dtos.DeleteUserDTO;
import com.lewall.dtos.BlockUserDTO;
import com.lewall.dtos.UnblockUserDTO;
import com.lewall.dtos.FollowUserDTO;
import com.lewall.dtos.PostsDTO;
import com.lewall.dtos.ProfileNameDTO;
import com.lewall.dtos.UnfollowUserDTO;
import com.lewall.dtos.UserDTO;
import com.lewall.dtos.UserPostsDTO;
import com.lewall.interfaces.IUserResolver;
import com.lewall.dtos.UserFollowingPostsDTO;
import com.lewall.dtos.FollowingPostsDTO;
import com.lewall.dtos.ClassPostsDTO;
import com.lewall.dtos.ClassFeedDTO;
import com.lewall.dtos.UserSearchDTO;
import com.lewall.dtos.UsersFoundDTO;

/**
 * A class to resolve User-related requests
 *
 * @author Shrey Agarwal
 * @version 14 November 2024
 */
@Resolver(basePath = "/user")
public class UserResolver implements BaseResolver, IUserResolver {
    /**
     * Get a user
     * 
     * @param request
     * @return {@link UserDTO}
     */
    @AuthGuard
    @Endpoint(endpoint = "/", method = Request.EMethod.GET, responseBodyType = UserDTO.class)
    public UserDTO getUser(Request<Void> request) {
        UUID userId = request.getUserId();

        User user = UserService.getUser(userId);
        if (user == null) {
            throw new InternalServerError("Failed to get User");
        }

        return new UserDTO(user);
    }

    /**
     * Delete a user
     * 
     * @param request
     *                {@link Request} with {@link DeleteUserDTO} body
     * @throws InternalServerError
     *                             if unable to delete user
     * @return void
     */
    @AuthGuard()
    @Endpoint(endpoint = "/delete", method = Request.EMethod.POST, requestBodyType = DeleteUserDTO.class)
    public void deleteUser(Request<DeleteUserDTO> request) {
        UUID userId = request.getUserId();

        if (!UserService.deleteUser(userId)) {
            throw new InternalServerError("Failed to Delete User");
        }
    }

    /**
     * Follow a user
     * 
     * @param request
     *                {@link Request} with {@link FollowUserDTO} body
     * @throws InternalServerError
     *                             if unable to follow user
     * @return void
     */
    @AuthGuard()
    @Endpoint(endpoint = "/follow", method = Request.EMethod.POST, requestBodyType = FollowUserDTO.class)
    public void followUser(Request<FollowUserDTO> request) {
        UUID followUserId = request.getBody().getFollowUserId();
        UUID userId = request.getUserId();

        if (!UserService.follow(userId, followUserId)) {
            throw new InternalServerError("Failed to Add User to Follow List");
        }
    }

    /**
     * Unfollow a user
     * 
     * @param request
     *                {@link Request} with {@link UnfollowUserDTO} body
     * @throws InternalServerError
     *                             if unable to unfollow user
     * @return void
     */
    @AuthGuard()
    @Endpoint(endpoint = "/unfollow", method = Request.EMethod.POST, requestBodyType = UnfollowUserDTO.class)
    public void unfollowUser(Request<UnfollowUserDTO> request) {
        UUID followUserId = request.getBody().getFollowUserId();
        UUID userId = request.getUserId();

        if (!UserService.unfollow(userId, followUserId)) {
            throw new InternalServerError("Failed to Add User to Follow List");
        }
    }

    /**
     * Block a user
     * 
     * @param request
     *                {@link Request} with {@link BlockUserDTO} body
     * @throws InternalServerError
     *                             if unable to block user
     * @return void
     */
    @AuthGuard()
    @Endpoint(endpoint = "/block", method = Request.EMethod.POST, requestBodyType = BlockUserDTO.class)
    public void blockUser(Request<BlockUserDTO> request) {
        UUID blockedUserId = request.getBody().getBlockedUserId();
        UUID userId = request.getUserId();

        if (!UserService.block(userId, blockedUserId)) {
            throw new InternalServerError("Failed to Add User to Blocked List");
        }
    }

    /**
     * Unblock a user
     * 
     * @param request
     *                {@link Request} with {@link UnblockUserDTO} body
     * @throws InternalServerError
     *                             if unable to unblock user
     * @return void
     */
    @AuthGuard()
    @Endpoint(endpoint = "/unblock", method = Request.EMethod.POST, requestBodyType = UnblockUserDTO.class)
    public void unblockUser(Request<UnblockUserDTO> request) {
        UUID blockedUserId = request.getBody().getUnblockedUserId();
        UUID userId = request.getUserId();

        if (!UserService.unblock(userId, blockedUserId)) {
            throw new InternalServerError("Failed to Add User to Blocked List");
        }
    }

    /**
     * Get posts of a user
     * 
     * @param request
     *                {@link Request} with {@link UserPostsDTO} body
     * @return {@link PostsDTO}
     */
    @AuthGuard()
    @Endpoint(endpoint = "/getPosts", method = Request.EMethod.GET, requestBodyType = UserPostsDTO.class, responseBodyType = PostsDTO.class)
    public PostsDTO getPosts(Request<UserPostsDTO> request) {
        UUID userId = request.getUserId();

        PostsDTO posts = new PostsDTO(UserService.getPosts(userId));
        if (UserService.getPosts(userId) == null) {
            throw new InternalServerError("Failed to get Posts");
        }

        return posts;
    }

    /**
     * Update the profile name of a user
     * 
     * @param request
     *                {@link Request} with {@link ProfileNameDTO} body
     * @throws InternalServerError
     *                             if unable to update profile name
     * @return void
     */
    @AuthGuard()
    @Endpoint(endpoint = "/updateProfileName", method = Request.EMethod.POST, requestBodyType = ProfileNameDTO.class)
    public void updateProfileName(Request<ProfileNameDTO> request) {
        UUID userId = request.getUserId();
        String name = request.getBody().getName();

        if (!UserService.updateProfileName(userId, name)) {
            throw new InternalServerError("Failed to Update Profile Name");
        }
    }

    /**
     * Get posts of users that the user is following
     * 
     * @param request
     *                {@link Request} with {@link UserFollowingPostsDTO} body
     * @return {@link FollowingPostsDTO}
     * 
     */
    @AuthGuard()
    @Endpoint(endpoint = "/getFollowerPosts", method = Request.EMethod.GET, requestBodyType = UserFollowingPostsDTO.class, responseBodyType = FollowingPostsDTO.class)
    public FollowingPostsDTO getFollowerPosts(Request<UserFollowingPostsDTO> request) {
        UUID userId = request.getUserId();
        UUID classId = request.getBody().getClassId();

        FollowingPostsDTO posts = new FollowingPostsDTO(UserService.getFollowingPosts(userId, classId));
        if (UserService.getFollowingPosts(userId, classId) == null) {
            throw new InternalServerError("Failed to get Following Posts");
        }

        return posts;
    }

    /**
     * Get posts of a class
     * 
     * @param request
     *                {@link Request} with {@link ClassPostsDTO} body
     * @return {@link ClassFeedDTO}
     */
    @AuthGuard()
    @Endpoint(endpoint = "/getMainFeed", method = Request.EMethod.GET, requestBodyType = ClassPostsDTO.class, responseBodyType = ClassFeedDTO.class)
    public ClassFeedDTO getMainFeed(Request<ClassPostsDTO> request) {
        UUID userId = request.getUserId();
        UUID classId = request.getBody().getClassId();

        ClassFeedDTO posts = new ClassFeedDTO(UserService.getClassFeed(userId, classId));
        if (UserService.getClassFeed(userId, classId) == null) {
            throw new InternalServerError("Failed to get Posts");
        }

        return posts;
    }

    /**
     * Search for users
     * 
     * @param request
     *                {@link Request} with {@link UserSearchDTO} body
     * @return {@link UsersFoundDTO}
     */
    @AuthGuard()
    @Endpoint(endpoint = "/userSearches", method = Request.EMethod.GET, requestBodyType = UserSearchDTO.class, responseBodyType = UsersFoundDTO.class)
    public UsersFoundDTO userSearches(Request<UserSearchDTO> request) {
        UUID userId = request.getUserId();
        String search = request.getBody().getSearch();

        UsersFoundDTO users = new UsersFoundDTO(UserService.getUsersSearched(userId, search));
        if (UserService.getUsersSearched(userId, search) == null) {
            throw new InternalServerError("Failed to get Searches");
        }

        return users;
    }

}
