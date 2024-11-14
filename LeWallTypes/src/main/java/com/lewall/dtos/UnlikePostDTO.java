package com.lewall.dtos;

import java.util.UUID;

public class UnlikePostDTO {
    private UUID postId;

    public UnlikePostDTO(UUID postId) {
        this.postId = postId;
    }

    public UUID getPostId() {
        return postId;
    }

}
