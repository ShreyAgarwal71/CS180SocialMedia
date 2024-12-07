package com.lewall.services;

import java.util.List;
import java.util.UUID;

import com.lewall.db.collections.UserCollection;
import com.lewall.db.collections.PostCollection;
import com.lewall.db.models.Comment;
import com.lewall.db.models.Post;
import com.lewall.db.models.User;
import com.lewall.db.collections.CommentCollection;
import com.lewall.api.BadRequest;

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
            String classId) {
        Post post = new Post(userId, messagePost, date, likes, imageURL, classId);
        return posts.addElement(post);
    }

    /**
     * Delete a post
     * 
     * @param userId
     * @param postId
     * @return
     */
    public static boolean deletePost(UUID userId, UUID postId) {
        User user = users.findOne(u -> u.getId().equals(userId));
        if (user == null) {
            throw new BadRequest("User not found");
        }
        Post post = posts.findOne(p -> p.getId().equals(postId));
        if (post == null) {
            throw new BadRequest("Post not found");
        }
        if (!post.getUserId().equals(userId)) {
            throw new BadRequest("Not users post");
        }
        return posts.removeElement(postId);
    }

    /**
     * Like a post
     * 
     * @param userId
     * @param postId
     * @return
     */
    public static boolean likePost(UUID userId, UUID postId) {
        Post post = posts.findOne(p -> p.getId().equals(postId));
        if (post == null) {
            throw new BadRequest("Post not found");
        }

        if (!post.addLike(userId.toString())) {
            throw new BadRequest("Already liked post");
        }

        return posts.updateElement(post.getId(), post);
    }

    /**
     * Unlike a post
     * 
     * @param userId
     * @param postId
     * @return
     */
    public static boolean unlikePost(UUID userId, UUID postId) {
        Post post = posts.findOne(p -> p.getId().equals(postId));
        if (post == null) {
            throw new BadRequest("Post not found");
        }

        if (!post.removeLike(userId.toString())) {
            throw new BadRequest("Not liked post");
        }

        return posts.updateElement(post.getId(), post);
    }

    /**
     * Hide a post
     * 
     * @param userId
     * @param postId
     * @return
     */
    public static boolean hidePost(UUID userId, UUID postId) {
        Post post = posts.findOne(p -> p.getId().equals(postId));
        if (post == null) {
            throw new BadRequest("Post not found");
        }
        User user = users.findOne(u -> u.getId().equals(userId));
        if (user == null) {
            throw new BadRequest("User not found");
        }

        return user.hidePost(postId.toString()) && users.updateElement(user.getId(), user);
    }

    /**
     * Unhide a post, however shouldn't ever be able to unhide a post
     * 
     * @param userId
     * @param postId
     * @return
     */
    public static boolean unhidePost(UUID userId, UUID postId) {
        Post post = posts.findOne(p -> p.getId().equals(postId));
        if (post == null) {
            throw new BadRequest("Post not found");
        }
        User user = users.findOne(u -> u.getId().equals(userId));
        if (user == null) {
            throw new BadRequest("User not found");
        }

        return user.unhidePost(postId.toString()) && users.updateElement(user.getId(), user);
    }

    /**
     * Get all comments for a post
     * 
     * @param postId
     * @return
     */
    public static List<Comment> getComments(UUID postId) {
        Post post = posts.findOne(p -> p.getId().equals(postId));
        if (post == null) {
            throw new BadRequest("Post not found");
        }
        List<Comment> comments1 = comments.commentsByPostId(postId);

        return comments1;
    }

    /**
     * Make a post private
     * 
     * @param userId
     * @param postId
     * @param isPrivate
     * @return
     */
    public static boolean makePostPrivate(UUID userId, UUID postId, boolean isPrivate) {
        Post post = posts.findOne(p -> p.getId().equals(postId));
        if (post == null) {
            throw new BadRequest("Post not found");
        }
        User user = users.findOne(u -> u.getId().equals(userId));
        if (user == null) {
            throw new BadRequest("User not found");
        }

        if (!post.getUserId().equals(userId)) {
            throw new BadRequest("Not users post");
        }

        post.setIsPrivate(isPrivate);

        return posts.updateElement(post.getId(), post);
    }
}
