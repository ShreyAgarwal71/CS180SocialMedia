package com.lewall.dtos;

import java.util.UUID;

public class UserFollowingPostsDTO {
    private UUID userId;

    public UserFollowingPostsDTO(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }
}
