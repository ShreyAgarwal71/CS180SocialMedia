package com.lewall.services;

import java.util.UUID;

import com.lewall.db.collections.CommentCollection;
import com.lewall.db.models.Comment;

/**
 * A class that implements Comment-managing services
 * 
 * @author Shrey Agarwal
 * @version 14 November 2024
 */
public class CommentService implements IService {
    private static final CommentCollection comments = db.getCommentCollection();

    public static boolean addComment(UUID userId, UUID postId, String messageComment, String date) {
        Comment comment = new Comment(userId, postId, messageComment, date, 0);

        return comments.addElement(comment);
    }

    public static boolean deleteComment(UUID commentId) {
        return comments.removeElement(commentId);
    }

    public static boolean likeComment(UUID userId, UUID commentId) {
        Comment comment = comments.findOne(c -> c.getId().equals(commentId));
        if (comment == null) {
            return false;
        }

        comment.addLike(userId.toString());

        return comments.updateElement(comment.getId(), comment);
    }

    public static boolean unlikeComment(UUID userId, UUID commentId) {
        Comment comment = comments.findOne(c -> c.getId().equals(commentId));
        if (comment == null) {
            return false;
        }

        comment.removeLike(userId.toString());

        return comments.updateElement(comment.getId(), comment);
    }

}
