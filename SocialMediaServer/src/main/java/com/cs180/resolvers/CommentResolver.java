package com.cs180.resolvers;

import java.util.UUID;

import com.cs180.api.InternalServerError;
import com.cs180.api.Request;
import com.cs180.resolvers.ResolverTools.AuthGuard;
import com.cs180.resolvers.ResolverTools.BaseResolver;
import com.cs180.resolvers.ResolverTools.Endpoint;
import com.cs180.resolvers.ResolverTools.Resolver;
import com.cs180.services.CommentService;

import com.cs180.dtos.LikeCommentDTO;

@Resolver(basePath = "/comment")
public class CommentResolver extends BaseResolver {
    public CommentResolver() {
        new ResolverTools().super();
    }

    @AuthGuard()
    @Endpoint(endpoint = "/add", method = Request.EMethod.POST, requestBodyType = LikeCommentDTO.class)
    public void addComment(Request<LikeCommentDTO> request) {
        UUID userId = UUID.randomUUID();
        UUID postId = UUID.fromString(request.getBody().getPostId());
        String messageComment = request.getBody().getMessageComment();
        String date = request.getBody().getDate();

        if (!CommentService.addComment(userId, postId, messageComment, date)) {
            throw new InternalServerError("Failed to Add Comment");
        }
    }

    AuthGuard()

    @Endpoint(endpoint = "/delete", method = Request.EMethod.POST, requestBodyType = LikeCommentDTO.class)
    public void deleteComment(Request<LikeCommentDTO> request) {
        UUID commentId = UUID.fromString(request.getBody().getCommentId());

        if (!CommentService.deleteComment(commentId)) {
            throw new InternalServerError("Failed to Delete Comment");
        }
    }

    @AuthGuard()
    @Endpoint(endpoint = "/like", method = Request.EMethod.POST, requestBodyType = LikeCommentDTO.class)
    public void likeComment(Request<LikeCommentDTO> request) {
        UUID commentId = UUID.fromString(request.getBody().getCommentId());
        UUID userId = UUID.randomUUID();

        if (!CommentService.likeComment(userId, commentId)) {
            throw new InternalServerError("Failed to Like Comment");
        }
    }

    @AuthGuard()
    @Endpoint(endpoint = "/unlike", method = Request.EMethod.POST, requestBodyType = LikeCommentDTO.class)
    public void unlikeComment(Request<LikeCommentDTO> request) {
        UUID commentId = UUID.fromString(request.getBody().getCommentId());
        UUID userId = UUID.randomUUID();

        if (!CommentService.unlikeComment(userId, commentId)) {
            throw new InternalServerError("Failed to Unlike Comment");
        }
    }
}
