package com.lewall.services;

import java.util.UUID;

import com.lewall.db.collections.CommentCollection;
import com.lewall.db.models.Comment;
import com.lewall.api.BadRequest;
import com.lewall.db.collections.PostCollection;
import com.lewall.db.models.Post;

public class CommentService implements Service {
    private static final CommentCollection comments = db.getCommentCollection();
    private static final PostCollection posts = db.getPostCollection();

    public static boolean addComment(UUID userId, UUID postId, String messageComment, String date) {
        Comment comment = new Comment(userId, postId, messageComment, date, 0);

        return comments.addElement(comment);
    }

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

}
