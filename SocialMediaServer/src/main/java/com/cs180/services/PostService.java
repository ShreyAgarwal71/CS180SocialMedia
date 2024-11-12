package com.cs180.services;

import java.util.UUID;

import com.cs180.db.collections.PostCollection;
import com.cs180.db.models.Post;

public class PostService implements Service {
    private static final PostCollection posts = db.getPostCollection();

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

}
