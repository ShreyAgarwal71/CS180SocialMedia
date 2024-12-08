package com.lewall.services;

import java.util.UUID;

import com.lewall.db.collections.CommentCollection;
import com.lewall.db.models.Comment;
import com.lewall.api.BadRequest;
import com.lewall.db.collections.PostCollection;
import com.lewall.db.models.Post;

/**
 * A class that implements Comment-managing services
 * 
 * @author Shrey Agarwal, Ates Isfendiyaroglu
 * @version 8 December 2024
 */
public class CommentService implements IService {
    private static final CommentCollection comments = db.getCommentCollection();
    private static final PostCollection posts = db.getPostCollection();

    /**
     * Add a comment to a post
     * 
     * @param userId
     * @param postId
     * @param messageComment
     * @param date
     * @return
     */
    public static boolean addComment(UUID userId, UUID postId, String messageComment, String date) {
        Comment comment = new Comment(userId, postId, messageComment, date, 0, 0);

        return comments.addElement(comment);
    }

    /**
     * Edit a comment
     * 
     * @param userId
     * @param commentId
     * @param messageComment
     * @return
     */
    public static boolean deleteComment(UUID userId, UUID commentId) {
        Comment comment = comments.findOne(c -> c.getId().equals(commentId));
        if (comment == null) {
            throw new BadRequest("Comment not found");
        }
        Post post = posts.findOne(p -> p.getId().equals(comment.getPostId()));
        if (!(comment.getUserId().equals(userId) || post.getUserId().equals(userId))) {
            throw new BadRequest("User does not have permission to delete this comment");
        }
        return comments.removeElement(commentId);
    }

    /**
     * Like a comment
     * 
     * @param userId
     * @param commentId
     * @return
     */
    public static boolean likeComment(UUID userId, UUID commentId) {
        Comment comment = comments.findOne(c -> c.getId().equals(commentId));
        if (comment == null) {
            throw new BadRequest("Comment not found");
        }

        if (!comment.addLike(userId.toString())) {
            throw new BadRequest("User has already liked this comment");
        }

        return comments.updateElement(comment.getId(), comment);
    }

    /**
     * Unlike a comment
     * 
     * @param userId
     * @param commentId
     * @return
     */
    public static boolean unlikeComment(UUID userId, UUID commentId) {
        Comment comment = comments.findOne(c -> c.getId().equals(commentId));
        if (comment == null) {
            throw new BadRequest("Comment not found");
        }

        if (!comment.removeLike(userId.toString())) {
            throw new BadRequest("User has not liked this comment");
        }

        return comments.updateElement(comment.getId(), comment);
    }

    /**
     * Dislike a comment
     * 
     * @param userId
     * @param commentId
     * @return
     */
    public static boolean dislikeComment(UUID userId, UUID commentId) {
        Comment comment = comments.findOne(c -> c.getId().equals(commentId));
        if (comment == null) {
            throw new BadRequest("Comment not found");
        }

        if (!comment.addDislike(userId.toString())) {
            throw new BadRequest("User has already disliked this comment");
        }

        return comments.updateElement(comment.getId(), comment);
    }

    /**
     * Un-dislike a comment
     * 
     * @param userId
     * @param commentId
     * @return exitCode
     */
    public static boolean unDislikeComment(UUID userId, UUID commentId) {
        Comment comment = comments.findOne(c -> c.getId().equals(commentId));
        if (comment == null) {
            throw new BadRequest("Comment not found");
        }

        if (!comment.removeDislike(userId.toString())) {
            throw new BadRequest("User has not disliked this comment");
        }

        return comments.updateElement(comment.getId(), comment);
    }
}
