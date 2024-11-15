package com.lewall.dtos;

import java.util.UUID;

public class PostCommentsDTO {
    private UUID postId;

    public PostCommentsDTO(UUID postId) {
        this.postId = postId;
    }

    public UUID getPostId() {
        return postId;
    }

}
