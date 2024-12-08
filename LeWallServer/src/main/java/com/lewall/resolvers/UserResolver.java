package com.lewall.resolvers;

import java.util.UUID;

import com.lewall.api.InternalServerError;
import com.lewall.api.Request;
import com.lewall.common.AggregatedPost;
import com.lewall.db.models.Post;
import com.lewall.db.models.User;
import com.lewall.resolvers.ResolverTools.AuthGuard;
import com.lewall.resolvers.ResolverTools.BaseResolver;
import com.lewall.resolvers.ResolverTools.Endpoint;
import com.lewall.resolvers.ResolverTools.Resolver;
import com.lewall.services.UserService;

import com.lewall.dtos.DeleteUserDTO;
import com.lewall.dtos.AggregatedPostsDTO;
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
import com.lewall.dtos.UserIdDTO;
import com.lewall.dtos.FollowingPostsDTO;
import com.lewall.dtos.ClassPostsDTO;
import com.lewall.dtos.ClassFeedDTO;
import com.lewall.dtos.UserSearchDTO;
import com.lewall.dtos.UsersFoundDTO;

import java.util.List;

/**
 * A class to resolve User-related requests
 *
 * @author Shrey Agarwal
 * @version 14 November 2024
 */
@Resolver(basePath = "/user")
public class UserResolver implements BaseResolver, IUserResolver {
    /**
     * Get the current authenticated user
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

    @AuthGuard
    @Endpoint(endpoint = "/get", method = Request.EMethod.POST, requestBodyType = UserIdDTO.class, responseBodyType = UserDTO.class)
    public UserDTO getUserById(Request<UserIdDTO> request) {
        UUID userId = request.getBody().getUserId();

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
     *            {@link Request} with {@link DeleteUserDTO} body
     * @throws InternalServerError
     *             if unable to delete user
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
     *            {@link Request} with {@link FollowUserDTO} body
     * @throws InternalServerError
     *             if unable to follow user
     * @return void
     */
    @AuthGuard()
    @Endpoint(endpoint = "/follow", method = Request.EMethod.POST, requestBodyType = FollowUserDTO.class, responseBodyType = UserDTO.class)
    public UserDTO followUser(Request<FollowUserDTO> request) {
        UUID followUserId = request.getBody().getFollowUserId();
        UUID userId = request.getUserId();

        User updatedUser = UserService.follow(userId, followUserId);
        if (updatedUser == null) {
            throw new InternalServerError("Failed to Add User to Follow List");
        }

        return new UserDTO(updatedUser);
    }

    /**
     * Unfollow a user
     * 
     * @param request
     *            {@link Request} with {@link UnfollowUserDTO} body
     * @throws InternalServerError
     *             if unable to unfollow user
     * @return UserDTO
     */
    @AuthGuard()
    @Endpoint(endpoint = "/unfollow", method = Request.EMethod.POST, requestBodyType = UnfollowUserDTO.class, responseBodyType = UserDTO.class)
    public UserDTO unfollowUser(Request<UnfollowUserDTO> request) {
        UUID followUserId = request.getBody().getFollowUserId();
        UUID userId = request.getUserId();

        User updatedUser = UserService.unfollow(userId, followUserId);
        if (updatedUser == null) {
            throw new InternalServerError("Failed to Add User to Follow List");
        }

        return new UserDTO(updatedUser);
    }

    /**
     * Block a user
     * 
     * @param request
     *            {@link Request} with {@link BlockUserDTO} body
     * @throws InternalServerError
     *             if unable to block user
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
     *            {@link Request} with {@link UnblockUserDTO} body
     * @throws InternalServerError
     *             if unable to unblock user
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
     *            {@link Request} with {@link UserPostsDTO} body
     * @return {@link PostsDTO}
     */
    @AuthGuard()
    @Endpoint(endpoint = "/getPosts", method = Request.EMethod.GET, responseBodyType = AggregatedPostsDTO.class)
    public AggregatedPostsDTO getPosts(Request<Void> request) {
        UUID userId = request.getUserId();

        try {
            List<AggregatedPost> posts = UserService.getAggregatedPosts(UserService.getPosts(userId));
            if (posts == null) {
                throw new InternalServerError("Failed to get Posts");
            }
            return new AggregatedPostsDTO(posts);
        } catch (Exception e) {
            System.out.println(e);
            throw new InternalServerError("Failed to get Posts");
        }
    }

    /**
     * Update the profile name of a user
     * 
     * @param request
     *            {@link Request} with {@link ProfileNameDTO} body
     * @throws InternalServerError
     *             if unable to update profile name
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
     * Get posts of users that the user is following and the user
     * 
     * @param request
     *            {@link Request} with {@link UserFollowingPostsDTO} body
     * @return {@link FollowingPostsDTO}
     * 
     */
    @AuthGuard()
    @Endpoint(endpoint = "/getFollowerPosts", method = Request.EMethod.GET, requestBodyType = UserFollowingPostsDTO.class, responseBodyType = FollowingPostsDTO.class)
    public FollowingPostsDTO getFollowerPosts(Request<UserFollowingPostsDTO> request) {
        UUID userId = request.getUserId();

        List<Post> posts = UserService.getFollowingPosts(userId);

        if (posts == null) {
            throw new InternalServerError("Failed to get Following Posts");
        }

        List<AggregatedPost> postRecords = UserService.getAggregatedPosts(posts);
        return new FollowingPostsDTO(postRecords);
    }

    /**
     * Get posts of a class
     * 
     * @param request
     *            {@link Request} with {@link ClassPostsDTO} body
     * @return {@link ClassFeedDTO}
     */
    @AuthGuard()
    @Endpoint(endpoint = "/getMainFeed", method = Request.EMethod.POST, requestBodyType = ClassPostsDTO.class, responseBodyType = ClassFeedDTO.class)
    public ClassFeedDTO getMainFeed(Request<ClassPostsDTO> request) {
        UUID userId = request.getUserId();
        String classId = request.getBody().getClassId();

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
     *            {@link Request} with {@link UserSearchDTO} body
     * @return {@link UsersFoundDTO}
     */
    @AuthGuard()
    @Endpoint(endpoint = "/userSearches", method = Request.EMethod.POST, requestBodyType = UserSearchDTO.class, responseBodyType = UsersFoundDTO.class)
    public UsersFoundDTO userSearches(Request<UserSearchDTO> request) {
        UUID userId = request.getUserId();
        String search = request.getBody().getSearch();

        UsersFoundDTO users = new UsersFoundDTO(UserService.getUsersSearched(userId, search));
        if (UserService.getUsersSearched(userId, search) == null) {
            throw new InternalServerError("Failed to get Searches");
        }

        /*
         * ClassesDTO classes = new ClassesDTO();
         * List<String> classesSearched = classes.getClasses();
         * for (int i = 0; i < classesSearched.size(); i++) {
         * if (classesSearched.get(i).toLowerCase().contains(search.toLowerCase())) {
         * users.addUsers(new User(classesSearched.get(i), "", "Class", "", ""));
         * }
         * }
         */

        return users;
    }

}
