package com.lewall.dtos;

import java.util.UUID;

public class DeletePostDTO {
    private UUID postId;

    public DeletePostDTO(UUID postId) {
        this.postId = postId;
    }

    public UUID getPostId() {
        return postId;
    }
}
