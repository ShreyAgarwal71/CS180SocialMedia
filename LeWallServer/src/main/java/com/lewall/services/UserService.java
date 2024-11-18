package com.lewall.services;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import com.lewall.db.collections.PostCollection;
import com.lewall.db.collections.UserCollection;
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

    public static boolean createUser(String username, String password, String displayName, String bio, String email) {
        User user = new User(username, password, displayName, bio, email);
        return users.addElement(user);
    }

    public static boolean deleteUser(UUID userId) {
        return users.removeElement(userId);
    }

    public static boolean follow(UUID userId, UUID followUserId) {
        User user = users.findOne(u -> u.getId().equals(userId));
        User userToFollow = users.findOne(u -> u.getId().equals(followUserId));
        if (userToFollow == null) {
            return false;
        }

        return userToFollow.addFollower(user.getId().toString()) && user.followUser(followUserId.toString())
                && users.updateElement(user.getId(), user);
    }

    public static boolean unfollow(UUID userId, UUID unfollowUserId) {
        User user = users.findOne(u -> u.getId().equals(userId));
        User userToUnfollow = users.findOne(u -> u.getId().equals(unfollowUserId));
        if (userToUnfollow == null) {
            return false;
        }

        return userToUnfollow.removeFollower(user.getId().toString()) && user.unfollowUser(unfollowUserId.toString())
                && users.updateElement(user.getId(), user);
    }

    public static boolean block(UUID userId, UUID blockUserID) {
        User user = users.findOne(u -> u.getId().equals(userId));
        User userToBlock = users.findOne(u -> u.getId().equals(blockUserID));
        if (userToBlock == null) {
            return false;
        }

        user.removeFollower(userToBlock.toString());
        user.unfollowUser(userToBlock.toString());
        userToBlock.unfollowUser(user.toString());
        userToBlock.removeFollower(user.toString());

        return user.addBlockedUser(blockUserID.toString()) && users.updateElement(user.getId(), user);
    }

    public static boolean unblock(UUID userId, UUID unblockUserID) {
        User user = users.findOne(u -> u.getId().equals(userId));
        User userToUnblock = users.findOne(u -> u.getId().equals(unblockUserID));
        if (userToUnblock == null) {
            return false;
        }

        return user.removeBlockedUser(unblockUserID.toString()) && users.updateElement(user.getId(), user);
    }

    public static boolean updateProfileName(UUID userId, String name) {
        User user = users.findOne(u -> u.getId().equals(userId));
        if (user == null) {
            return false;
        }

        user.setDisplayName(name);

        return users.updateElement(user.getId(), user);
    }

    public static boolean updateProfileBio(UUID userId, String bio) {
        User user = users.findOne(u -> u.getId().equals(userId));
        if (user == null) {
            return false;
        }

        user.setBio(bio);

        return users.updateElement(user.getId(), user);
    }

    public static List<Post> getPosts(UUID userId) {
        User user = users.findOne(u -> u.getId().equals(userId));
        if (user == null) {
            return null;
        }
        List<Post> posts1 = posts.findByUserId(userId);

        return posts1;
    }

    public static List<Post> getFollowingPosts(UUID userId, UUID classId) {
        User user = users.findOne(u -> u.getId().equals(userId));
        if (user == null) {
            return null;
        }
        List<UUID> following = new ArrayList<>();
        List<List<Post>> posts1 = new ArrayList<>();
        for (String followerId : user.getFollowing()) {
            following.add(UUID.fromString(followerId));
        }
        for (UUID follower : following) {
            posts1.add(posts.findByClassAndUserId(follower, classId));
        }

        List<Post> posts2 = new ArrayList<>();
        for (int i = 0; i < posts1.size(); i++) {
            for (int j = 0; j < posts1.get(i).size(); j++) {
                if (!(user.getBlockedUsers().contains(posts1.get(i).get(j).getUserId().toString()))) {
                    posts2.add(posts1.get(i).get(j));
                }
            }
        }

        PostSort.quickSort(posts2, 0, posts2.size() - 1);

        return posts2;
    }

    public static List<Post> getClassFeed(UUID userId, UUID classId) {
        User user = users.findOne(u -> u.getId().equals(userId));
        if (user == null) {
            return null;
        }

        List<Post> posts1 = posts.findByClassId(classId);

        return posts1;
    }

}
