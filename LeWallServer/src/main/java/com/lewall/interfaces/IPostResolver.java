package com.lewall.interfaces;

import com.lewall.api.Request;
import com.lewall.dtos.CreatePostDTO;
import com.lewall.dtos.DeletePostDTO;
import com.lewall.dtos.UnlikePostDTO;
import com.lewall.dtos.UserIdDTO;
import com.lewall.dtos.LikePostDTO;
import com.lewall.dtos.AggregatedCommentsDTO;
import com.lewall.dtos.CommentsDTO;
import com.lewall.dtos.PostCommentsDTO;
import com.lewall.dtos.PostsDTO;
import com.lewall.dtos.HidePostDTO;
import com.lewall.dtos.PostDTO;

/**
 * Interface implemented only by the PostResolver class
 *
 * @author Ates Isfendiyaroglu
 * @version 17 November 2024
 */
public interface IPostResolver {
	/**
	 * Resolves a create post request
	 */
	public void createPost(Request<CreatePostDTO> request);

	/**
	 * Resolves a delete post request
	 */
	public void deletePost(Request<DeletePostDTO> request);

	/**
	 * Resolves a like post request
	 */
	public PostDTO likePost(Request<LikePostDTO> request);

	/**
	 * Resolves an unlike post request
	 */
	public PostDTO unlikePost(Request<UnlikePostDTO> request);

	/**
	 * Resolves an hide post request
	 */
	public void hidePost(Request<HidePostDTO> request);

	/**
	 * Resolves a get commments of a post request
	 */
	public AggregatedCommentsDTO getComments(Request<PostCommentsDTO> request);

	/**
	 * Resolves all posts of a specific user
	 */
	public PostsDTO getPosts(Request<UserIdDTO> request);
}
