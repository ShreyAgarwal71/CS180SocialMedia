package com.cs180.dtos;

import java.util.UUID;

public class LikePostDTO {
    private UUID postId;

    public LikePostDTO(UUID postId) {
        this.postId = postId;
    }

    public UUID getPostId() {
        return postId;
    }
}
