package com.lewall.interfaces;

import com.lewall.api.Request;

import com.lewall.dtos.AddCommentDTO;
import com.lewall.dtos.CommentDTO;
import com.lewall.dtos.DeleteCommentDTO;
import com.lewall.dtos.UnlikeCommentDTO;
import com.lewall.dtos.LikeCommentDTO;

/**
 * Interface implemented only by the PostResolver class
 *
 * @author Ates Isfendiyaroglu
 * @version 17 November 2024
 */
public interface ICommentResolver {
	/**
	 * Resolves an add comment request
	 */
	public void addComment(Request<AddCommentDTO> request);

	/**
	 * Resolves a delete comment request
	 */
	public void deleteComment(Request<DeleteCommentDTO> request);

	/**
	 * Resolves a toggle of a like comment request
	 */
	public CommentDTO likeComment(Request<LikeCommentDTO> request);
}
