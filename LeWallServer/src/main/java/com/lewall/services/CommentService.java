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

    public static Comment getComment(UUID commentId) {
        return comments.findOne(c -> c.getId().equals(commentId));
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
     * Toggle like
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

        comment.toggleLike(userId.toString());

        return comments.updateElement(comment.getId(), comment);
    }

    /**
     * Toggle dislike
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

        comment.toggleDislike(userId.toString());

        return comments.updateElement(comment.getId(), comment);
    }
}
