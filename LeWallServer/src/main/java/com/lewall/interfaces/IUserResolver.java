package com.lewall.interfaces;

import com.lewall.api.Request;

import com.lewall.dtos.DeleteUserDTO;
import com.lewall.dtos.AggregatedPostsDTO;
import com.lewall.dtos.BlockUserDTO;
import com.lewall.dtos.UnblockUserDTO;
import com.lewall.dtos.FollowUserDTO;
import com.lewall.dtos.ProfileNameDTO;
import com.lewall.dtos.UnfollowUserDTO;
import com.lewall.dtos.UserDTO;
import com.lewall.dtos.UserFollowingPostsDTO;
import com.lewall.dtos.UserIdDTO;
import com.lewall.dtos.FollowingPostsDTO;
import com.lewall.dtos.LimitDTO;
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
	public UserDTO followUser(Request<FollowUserDTO> request);

	/**
	 * Resolves an unfollow user request;
	 *
	 * @param request
	 */
	public UserDTO unfollowUser(Request<UnfollowUserDTO> request);

	/**
	 * Resolves a block user request;
	 *
	 * @param request
	 */
	public UserDTO blockUser(Request<BlockUserDTO> request);

	/**
	 * Resolves an unblock user request;
	 *
	 * @param request
	 */
	public UserDTO unblockUser(Request<UnblockUserDTO> request);

	/**
	 * Resolves a getPosts request;
	 *
	 * @param request
	 */
	public AggregatedPostsDTO getPosts(Request<UserIdDTO> request);

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
	public FollowingPostsDTO getFollowerPosts(Request<LimitDTO> request);

	/**
	 * Resolves a get main feed request;
	 *
	 * @param request
	 */
	public ClassFeedDTO getMainFeed(Request<ClassPostsDTO> request);
}
