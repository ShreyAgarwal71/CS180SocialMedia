package com.cs180.dtos;

import java.util.UUID;

public class UnfollowUserDTO {
    private UUID followUserId;

    public UnfollowUserDTO(UUID followUserId) {
        this.followUserId = followUserId;
    }

    public UUID getFollowUserId() {
        return followUserId;
    }
}
