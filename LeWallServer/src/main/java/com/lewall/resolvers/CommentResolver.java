package com.lewall.resolvers;

import java.util.UUID;

import com.lewall.api.InternalServerError;
import com.lewall.api.Request;
import com.lewall.resolvers.ResolverTools.AuthGuard;
import com.lewall.resolvers.ResolverTools.BaseResolver;
import com.lewall.resolvers.ResolverTools.Endpoint;
import com.lewall.resolvers.ResolverTools.Resolver;
import com.lewall.services.CommentService;

import com.lewall.dtos.AddCommentDTO;
import com.lewall.dtos.CommentDTO;
import com.lewall.dtos.DeleteCommentDTO;
import com.lewall.dtos.UnlikeCommentDTO;
import com.lewall.interfaces.ICommentResolver;
import com.lewall.dtos.LikeCommentDTO;

/**
 * A class to resolve Comment-related requests
 *
 * @author Shrey Agarwal, Ates Isfendiyaroglu
 * @version 8 December 2024
 */
@Resolver(basePath = "/comment")
public class CommentResolver implements BaseResolver, ICommentResolver {

    /**
     * Add a comment to a post
     * 
     * @param request
     *                {@link Request} with {@link AddCommentDTO} body
     * @throws InternalServerError
     *                             if unable to add comment
     * @return void
     */
    @AuthGuard()
    @Endpoint(endpoint = "/add", method = Request.EMethod.POST, requestBodyType = AddCommentDTO.class)
    public void addComment(Request<AddCommentDTO> request) {
        UUID userId = request.getUserId();

        UUID postId = request.getBody().getPostId();
        String messageComment = request.getBody().getMessageComment();
        String date = request.getBody().getDate();

        if (!CommentService.addComment(userId, postId, messageComment, date)) {
            throw new InternalServerError("Failed to Add Comment");
        }
    }

    /**
     * Delete a comment
     * 
     * @param request
     *                {@link Request} with {@link DeleteCommentDTO} body
     * @throws InternalServerError
     *                             if unable to delete comment
     * @return void
     */
    @AuthGuard()
    @Endpoint(endpoint = "/delete", method = Request.EMethod.POST, requestBodyType = DeleteCommentDTO.class)
    public void deleteComment(Request<DeleteCommentDTO> request) {
        UUID commentId = request.getBody().getCommentId();
        UUID userId = request.getUserId();

        if (!CommentService.deleteComment(userId, commentId)) {
            throw new InternalServerError("Failed to Delete Comment");
        }
    }

    /**
     * Like a comment
     * 
     * @param request
     *                {@link Request} with {@link LikeCommentDTO} body
     * @throws InternalServerError
     *                             if unable to like comment
     * @return void
     */
    @AuthGuard()
    @Endpoint(endpoint = "/like", method = Request.EMethod.POST, requestBodyType = LikeCommentDTO.class, responseBodyType = CommentDTO.class)
    public CommentDTO likeComment(Request<LikeCommentDTO> request) {
        UUID commentId = request.getBody().getCommentId();
        UUID userId = request.getUserId();

        if (!CommentService.likeComment(userId, commentId)) {
            throw new InternalServerError("Failed to Like Comment");
        }

        return new CommentDTO(CommentService.getComment(commentId));
    }

    /**
     * Dislike a comment
     * 
     * @param request
     *                {@link Request} with {@link LikeCommentDTO} body
     * @throws InternalServerError
     *                             if unable to like comment
     * @return void
     */
    @AuthGuard()
    @Endpoint(endpoint = "/dislike", method = Request.EMethod.POST, requestBodyType = LikeCommentDTO.class, responseBodyType = CommentDTO.class)
    public CommentDTO dislikeComment(Request<LikeCommentDTO> request) {
        UUID commentId = request.getBody().getCommentId();
        UUID userId = request.getUserId();

        if (!CommentService.dislikeComment(userId, commentId)) {
            throw new InternalServerError("Failed to dislike Comment");
        }

        return new CommentDTO(CommentService.getComment(commentId));
    }

    /**
     * Unlike a comment
     * 
     * @param request
     *                {@link Request} with {@link UnlikeCommentDTO} body
     * @throws InternalServerError
     *                             if unable to unlike comment
     * @return void
     */
    @AuthGuard()
    @Endpoint(endpoint = "/unlike", method = Request.EMethod.POST, requestBodyType = UnlikeCommentDTO.class, responseBodyType = CommentDTO.class)
    public CommentDTO unlikeComment(Request<UnlikeCommentDTO> request) {
        UUID commentId = request.getBody().getCommentId();
        UUID userId = request.getUserId();

        if (!CommentService.unlikeComment(userId, commentId)) {
            throw new InternalServerError("Failed to Unlike Comment");
        }

        return new CommentDTO(CommentService.getComment(commentId));
    }

    /**
     * Un-dislike a comment
     * 
     * @param request
     *                {@link Request} with {@link UnlikeCommentDTO} body
     * @throws InternalServerError
     *                             if unable to unlike comment
     * @return void
     */
    @AuthGuard()
    @Endpoint(endpoint = "/unDislike", method = Request.EMethod.POST, requestBodyType = UnlikeCommentDTO.class, responseBodyType = CommentDTO.class)
    public CommentDTO unDislikeComment(Request<UnlikeCommentDTO> request) {
        UUID commentId = request.getBody().getCommentId();
        UUID userId = request.getUserId();

        if (!CommentService.unDislikeComment(userId, commentId)) {
            throw new InternalServerError("Failed to Unlike Comment");
        }

        return new CommentDTO(CommentService.getComment(commentId));
    }
}
