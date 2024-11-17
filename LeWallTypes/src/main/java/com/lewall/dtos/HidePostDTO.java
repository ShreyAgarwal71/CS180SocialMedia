package com.lewall.dtos;

import java.util.UUID;

public class HidePostDTO {
    private UUID postId;

    public HidePostDTO(UUID postId) {
        this.postId = postId;
    }

    public UUID getPostId() {
        return postId;
    }

}
