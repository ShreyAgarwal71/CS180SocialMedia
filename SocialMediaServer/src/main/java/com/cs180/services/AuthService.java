package com.cs180.services;

import com.cs180.db.UserCollection;
import com.cs180.db.models.User;

public class AuthService implements Service {
    private static final UserCollection users = db.getUserCollection();

    public AuthService() {
    }

    public User signInWithEmailAndPassword(String email, String password) {
        String hashedPassword = getHashedPassword(password);

        return users.findOne(user -> user.getEmail().equals(email));
    }

    // TODO: Implement this method
    private String getHashedPassword(String password) {
        return password;
    }
}
