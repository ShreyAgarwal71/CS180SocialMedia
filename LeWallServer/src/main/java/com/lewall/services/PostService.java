package com.lewall.services;

import java.util.List;
import java.util.UUID;

import com.lewall.db.collections.PostCollection;
import com.lewall.db.models.Post;
import com.lewall.db.models.Comment;
import com.lewall.db.collections.CommentCollection;

public class PostService implements Service {
    private static final PostCollection posts = db.getPostCollection();
    private static final CommentCollection comments = db.getCommentCollection();

    public static boolean createPost(UUID userId, String messagePost, String date, int likes, String imageURL) {
        Post post = new Post(userId, messagePost, date, likes, imageURL);
        return posts.addElement(post);
    }

    public static boolean deletePost(UUID postId) {
        return posts.removeElement(postId);
    }

    public static boolean likePost(UUID userId, UUID postId) {
        Post post = posts.findOne(p -> p.getId().equals(postId));
        if (post == null) {
            return false;
        }

        post.addLike(userId.toString());

        return posts.updateElement(post.getId(), post);
    }

    public static boolean unlikePost(UUID userId, UUID postId) {
        Post post = posts.findOne(p -> p.getId().equals(postId));
        if (post == null) {
            return false;
        }

        post.removeLike(userId.toString());

        return posts.updateElement(post.getId(), post);
    }

    public static List<Comment> getComments(UUID postId) {
        Post post = posts.findOne(p -> p.getId().equals(postId));
        if (post == null) {
            return null;
        }
        List<Comment> comments1 = comments.commentsByPostId(postId);

        return comments1;
    }

}
