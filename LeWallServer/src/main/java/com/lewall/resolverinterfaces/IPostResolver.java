package com.lewall.resolverinterfaces;

import com.lewall.api.Request;
import com.lewall.dtos.CreatePostDTO;
import com.lewall.dtos.DeletePostDTO;
import com.lewall.dtos.UnlikePostDTO;
import com.lewall.dtos.LikePostDTO;
import com.lewall.dtos.CommentsDTO;
import com.lewall.dtos.PostCommentsDTO;
import com.lewall.dtos.HidePostDTO;

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
    public void likePost(Request<LikePostDTO> request);

	/**
	 * Resolves an unlike post request
	 */
    public void unlikePost(Request<UnlikePostDTO> request);

	/**
	 * Resolves an hide post request
	 */
    public void hidePost(Request<HidePostDTO> request);

	/**
	 * Resolves a get posts request
	 */
    public CommentsDTO getPosts(Request<PostCommentsDTO> request);
}
