package com.lewall.services;

import java.util.List;
import java.util.UUID;

import com.lewall.db.collections.UserCollection;
import com.lewall.db.collections.PostCollection;
import com.lewall.db.models.Comment;
import com.lewall.db.models.Post;
import com.lewall.db.models.User;
import com.lewall.db.collections.CommentCollection;

/**
 * A class that implements Post-managing services
 * 
 * @author Shrey Agarwal
 * @version 14 November 2024
 */
public class PostService implements IService {
    private static final UserCollection users = db.getUserCollection();
    private static final PostCollection posts = db.getPostCollection();
    private static final CommentCollection comments = db.getCommentCollection();

    public static boolean createPost(UUID userId, String messagePost, String date, int likes, String imageURL,
            UUID classId) {
        Post post = new Post(userId, messagePost, date, likes, imageURL, classId);
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

    public static boolean hidePost(UUID userId, UUID postId) {
        Post post = posts.findOne(p -> p.getId().equals(postId));
        if (post == null) {
            return false;
        }
        User user = users.findOne(u -> u.getId().equals(userId));
        if (user == null) {
            return false;
        }

        return user.hidePost(postId.toString()) && users.updateElement(user.getId(), user);
    }

    // Shouldn't ever be able to unhide a post
    public static boolean unhidePost(UUID userId, UUID postId) {
        Post post = posts.findOne(p -> p.getId().equals(postId));
        if (post == null) {
            return false;
        }
        User user = users.findOne(u -> u.getId().equals(userId));
        if (user == null) {
            return false;
        }

        return user.unhidePost(postId.toString()) && users.updateElement(user.getId(), user);
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
