package com.cs180.services;

import java.util.UUID;

import com.cs180.db.collections.UserCollection;
import com.cs180.db.models.User;

public class UserService implements Service {
    private static final UserCollection users = db.getUserCollection();

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

        userToFollow.addFollower(user.getId().toString());
        user.followUser(followUserId.toString());

        return users.updateElement(user.getId(), user);
    }

    public static boolean unfollow(UUID userId, UUID unfollowUserId) {
        User user = users.findOne(u -> u.getId().equals(userId));
        User userToUnfollow = users.findOne(u -> u.getId().equals(unfollowUserId));
        if (userToUnfollow == null) {
            return false;
        }

        userToUnfollow.removeFollower(user.getId().toString());
        user.unfollowUser(unfollowUserId.toString());

        return users.updateElement(user.getId(), user);
    }

    public static boolean block(UUID userId, UUID blockUserID) {
        User user = users.findOne(u -> u.getId().equals(userId));
        User userToBlock = users.findOne(u -> u.getId().equals(blockUserID));
        if (userToBlock == null) {
            return false;
        }

        user.addBlockedUser(blockUserID.toString());

        return users.updateElement(user.getId(), user);
    }

    public static boolean unblock(UUID userId, UUID unblockUserID) {
        User user = users.findOne(u -> u.getId().equals(userId));
        User userToUnblock = users.findOne(u -> u.getId().equals(unblockUserID));
        if (userToUnblock == null) {
            return false;
        }

        user.removeBlockedUser(unblockUserID.toString());

        return users.updateElement(user.getId(), user);
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

}
