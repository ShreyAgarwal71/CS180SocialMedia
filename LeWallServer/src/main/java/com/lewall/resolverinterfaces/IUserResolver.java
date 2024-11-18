package com.lewall.resolverinterfaces;

import com.lewall.api.Request;

import com.lewall.dtos.CreateUserDTO;
import com.lewall.dtos.DeleteUserDTO;
import com.lewall.dtos.BlockUserDTO;
import com.lewall.dtos.UnblockUserDTO;
import com.lewall.dtos.FollowUserDTO;
import com.lewall.dtos.PostsDTO;
import com.lewall.dtos.ProfileNameDTO;
import com.lewall.dtos.UnfollowUserDTO;
import com.lewall.dtos.UserPostsDTO;
import com.lewall.dtos.UserFollowingPostsDTO;
import com.lewall.dtos.FollowingPostsDTO;
import com.lewall.dtos.ClassPostsDTO;
import com.lewall.dtos.ClassFeedDTO;

/**
 * Interface implemented only by the UserResolver class
 *
 * @author Ates Isfendiyaroglu
 * @version 17 November 2024
 */
public interface IUserResolver {

	/**
	 * Resolves a create new user request;
	 *
	 * @param request
	 */
    public void createUser(Request<CreateUserDTO> request);

	/**
	 * Resolves a delete user request;
	 *
	 * @param request
	 */
    public void deleteUser(Request<DeleteUserDTO> request);

	/**
	 * Resolves a follow user request;
	 *
	 * @param request
	 */
    public void followUser(Request<FollowUserDTO> request);

	/**
	 * Resolves an unfollow user request;
	 *
	 * @param request
	 */
    public void unfollowUser(Request<UnfollowUserDTO> request);

	/**
	 * Resolves a block user request;
	 *
	 * @param request
	 */
    public void blockUser(Request<BlockUserDTO> request);

	/**
	 * Resolves an unblock user request;
	 *
	 * @param request
	 */
    public void unblockUser(Request<UnblockUserDTO> request);

	/**
	 * Resolves a getPosts request;
	 *
	 * @param request
	 */
    public PostsDTO getPosts(Request<UserPostsDTO> request);

	/**
	 * Resolves a update profile name request;
	 *
	 * @param request
	 */
    public void updateProfileName(Request<ProfileNameDTO> request);

	/**
	 * Resolves a get follower posts request;
	 *
	 * @param request
	 */
    public FollowingPostsDTO getFollowerPosts(Request<UserFollowingPostsDTO> request);

	/**
	 * Resolves a get main feed request;
	 *
	 * @param request
	 */
    public ClassFeedDTO getMainFeed(Request<ClassPostsDTO> request);
}
