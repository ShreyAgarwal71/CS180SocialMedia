package com.cs180.services;

import java.util.UUID;

import com.cs180.db.UserCollection;
import com.cs180.db.models.User;

public class UserService implements Service {
    private static final UserCollection users = db.getUserCollection();

    public UserService() {
    }

    public boolean blockUserById(User user, String userId) {
        User userToBlock = users.findOne(u -> u.getId().equals(UUID.fromString(userId)));
        if (userToBlock == null) {
            return false;
        }

        // update the user's blocked list
        users.updateElement(user.getId(), user);

        return users.updateElement(user.getId(), user);
    }
}
