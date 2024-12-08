package com.lewall.services;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import com.lewall.api.BadRequest;
import com.lewall.common.AggregatedPost;
import com.lewall.db.collections.CommentCollection;
import com.lewall.db.collections.PostCollection;
import com.lewall.db.collections.UserCollection;
import com.lewall.db.models.Comment;
import com.lewall.db.models.Post;
import com.lewall.db.models.User;
import com.lewall.helpers.PostSort;

/**
 * A class that implements User-managing services
 * 
 * @author Shrey Agarwal
 * @version 14 November 2024
 */
public class UserService implements IService {
    private static final UserCollection users = db.getUserCollection();
    private static final PostCollection posts = db.getPostCollection();
    private static final CommentCollection comments = db.getCommentCollection();

    public static List<AggregatedPost> getAggregatedPosts(List<Post> posts) {
        List<AggregatedPost> aggregatedPosts = new ArrayList<>();
        for (Post post : posts) {
            aggregatedPosts.add(getAggregatedPost(post));
        }
        return aggregatedPosts;
    }

    public static AggregatedPost getAggregatedPost(Post post) {
        User user = users.findById(post.getUserId());
        List<Comment> postComments = comments.findAll(c -> c.getPostId().equals(post.getId()));
        AggregatedPost aggregatedPost = new AggregatedPost(post, postComments, user);
        return aggregatedPost;
    }

    /**
     * Get a user
     * 
     * @param userId
     * @return {@link User}
     */
    public static User getUser(UUID userId) {
        return users.findById(userId);
    }

    /**
     * Delete a user
     * 
     * @param userId
     * @return boolean
     */
    public static boolean deleteUser(UUID userId) {
        User user = users.findOne(u -> u.getId().equals(userId));
        if (user == null) {
            throw new BadRequest("User not found");
        }
        return users.removeElement(userId);
    }

    /**
     * Follow a user
     * 
     * @param userId
     * @param followUserId
     * @return user
     */
    public static User follow(UUID userId, UUID followUserId) {
        User user = users.findOne(u -> u.getId().equals(userId));
        User userToFollow = users.findOne(u -> u.getId().equals(followUserId));
        if (userToFollow == null) {
            throw new BadRequest("User not found");
        }

        boolean followStatus = userToFollow.addFollower(user.getId().toString())
                && user.followUser(followUserId.toString())
                && users.updateElement(user.getId(), user);

        if (!followStatus)
            return null;

        return users.findById(followUserId);
    }

    /**
     * Unfollow a user
     * 
     * @param userId
     * @param unfollowUserId
     * @return User
     */
    public static User unfollow(UUID userId, UUID unfollowUserId) {
        User user = users.findOne(u -> u.getId().equals(userId));
        User userToUnfollow = users.findOne(u -> u.getId().equals(unfollowUserId));
        if (userToUnfollow == null) {
            throw new BadRequest("User not found");
        }

        boolean unfollowStatus = userToUnfollow.removeFollower(user.getId().toString())
                && user.unfollowUser(unfollowUserId.toString())
                && users.updateElement(user.getId(), user);

        if (!unfollowStatus)
            return null;

        return users.findById(unfollowUserId);
    }

    /**
     * Block a user
     * 
     * @param userId
     * @param blockUserID
     * @return boolean
     */
    public static boolean block(UUID userId, UUID blockUserID) {
        User user = users.findOne(u -> u.getId().equals(userId));
        User userToBlock = users.findOne(u -> u.getId().equals(blockUserID));
        if (userToBlock == null) {
            throw new BadRequest("User not found");
        }

        user.removeFollower(userToBlock.toString());
        user.unfollowUser(userToBlock.toString());
        userToBlock.unfollowUser(user.toString());
        userToBlock.removeFollower(user.toString());

        return user.addBlockedUser(blockUserID.toString()) && users.updateElement(user.getId(), user);
    }

    /**
     * Unblock a user
     * 
     * @param userId
     * @param unblockUserID
     * @return boolean
     */
    public static boolean unblock(UUID userId, UUID unblockUserID) {
        User user = users.findOne(u -> u.getId().equals(userId));
        User userToUnblock = users.findOne(u -> u.getId().equals(unblockUserID));
        if (userToUnblock == null) {
            throw new BadRequest("User not found");
        }

        return user.removeBlockedUser(unblockUserID.toString()) && users.updateElement(user.getId(), user);
    }

    /**
     * Update a user's profile name
     * 
     * @param userId
     * @param name
     * @return boolean
     */
    public static boolean updateProfileName(UUID userId, String name) {
        User user = users.findOne(u -> u.getId().equals(userId));
        if (user == null) {
            throw new BadRequest("User not found");
        }

        user.setDisplayName(name);

        return users.updateElement(user.getId(), user);
    }

    /**
     * Update a user's profile bio
     * 
     * @param userId
     * @param bio
     * @return boolean
     */
    public static boolean updateProfileBio(UUID userId, String bio) {
        User user = users.findOne(u -> u.getId().equals(userId));
        if (user == null) {
            throw new BadRequest("User not found");
        }

        user.setBio(bio);

        return users.updateElement(user.getId(), user);
    }

    /**
     * Get a user's posts
     * 
     * @param userId
     * @return
     */
    public static List<Post> getPosts(UUID userId) {
        User user = users.findOne(u -> u.getId().equals(userId));
        if (user == null) {
            throw new BadRequest("User not found");
        }
        List<Post> posts1 = posts.findByUserId(userId);

        return posts1;
    }

    /**
     * Get all posts from users that the user is following
     * 
     * @param userId
     * @return
     */
    public static List<Post> getFollowingPosts(UUID userId) {
        User user = users.findOne(u -> u.getId().equals(userId));
        if (user == null) {
            throw new BadRequest("User not found");
        }
        List<UUID> following = new ArrayList<>();
        List<List<Post>> posts1 = new ArrayList<>();
        for (String followerId : user.getFollowing()) {
            following.add(UUID.fromString(followerId));
        }
        for (UUID follower : following) {
            posts1.add(posts.findByClassAndUserId(follower));
        }

        List<Post> posts2 = new ArrayList<>();
        for (int i = 0; i < posts1.size(); i++) {
            for (int j = 0; j < posts1.get(i).size(); j++) {
                if (!(user.getBlockedUsers().contains(posts1.get(i).get(j).getUserId().toString()))
                        && !(user.getHiddenPosts().contains(posts1.get(i).get(j).getId().toString()))
                        && !(posts1.get(i).get(j).getIsPrivate())) {
                    posts2.add(posts1.get(i).get(j));
                }
            }
        }

        List<Post> userPosts = UserService.getPosts(userId);
        posts2.addAll(userPosts);

        PostSort.quickSort(posts2, 0, posts2.size() - 1);

        return posts2;
    }

    /**
     * Get all posts from a class
     * 
     * @param userId
     * @param classId
     * @return
     */
    public static List<Post> getClassFeed(UUID userId, String classId) {
        User user = users.findOne(u -> u.getId().equals(userId));
        if (user == null) {
            throw new BadRequest("User not found");
        }

        List<Post> posts1 = posts.findByClassId(classId);
        List<Post> posts2 = new ArrayList<>();
        for (int i = 0; i < posts1.size(); i++) {
            if (!(user.getBlockedUsers().contains(posts1.get(i).getUserId().toString()))
                    && !(user.getHiddenPosts().contains(posts1.get(i).getId().toString()))
                    && !(posts1.get(i).getIsPrivate())) {
                posts2.add(posts1.get(i));
            }
        }

        return posts2;
    }

    public static List<User> getUsersSearched(UUID userId, String search) {
        User user = users.findOne(u -> u.getId().equals(userId));
        if (user == null) {
            throw new BadRequest("User not found");
        }

        // System.out.println(search);
        List<User> users1 = users.searchByUsername(search);
        // System.out.println(users1.size());

        List<User> users2 = new ArrayList<>();
        for (int i = 0; i < users1.size(); i++) {
            if (!(users1.get(i).getBlockedUsers().contains(user.getId().toString()))
                    && !(user.getHiddenPosts().contains(users1.get(i).getId().toString()))) {
                users2.add(users1.get(i));
            }
        }

        return users2;
    }

}
