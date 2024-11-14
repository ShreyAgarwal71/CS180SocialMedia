package com.lewall.dtos;

import java.util.UUID;

public class FollowUserDTO {
    private UUID followUserId;

    public FollowUserDTO(UUID followUserId) {
        this.followUserId = followUserId;
    }

    public UUID getFollowUserId() {
        return followUserId;
    }
}