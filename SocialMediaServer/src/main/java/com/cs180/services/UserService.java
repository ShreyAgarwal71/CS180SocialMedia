package com.cs180.services;

import java.util.UUID;

import com.cs180.db.UserCollection;
import com.cs180.db.models.User;

public class UserService implements Service {
    private static final UserCollection users = db.getUserCollection();

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
}
