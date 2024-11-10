package com.cs180.dtos;

public class FollowUserDTO {
    private String followUserId;

    public FollowUserDTO(String followUserId) {
        this.followUserId = followUserId;
    }

    public String getFollowUserId() {
        return followUserId;
    }
}