package com.lewall.dtos;

import com.lewall.db.models.User;
import java.util.List;

public class UsersFoundDTO {
    private List<User> users;

    public UsersFoundDTO(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
