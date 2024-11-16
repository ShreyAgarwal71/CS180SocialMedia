package com.lewall.dtos;

import java.util.UUID;

public class UserPostsDTO {
    private UUID userId;

    public UserPostsDTO(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }
}
