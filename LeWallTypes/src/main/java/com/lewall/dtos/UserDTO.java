package com.lewall.dtos;

import com.lewall.db.models.User;

public class UserDTO {
    private final User user;

    public UserDTO(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
