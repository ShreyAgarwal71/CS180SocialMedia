package com.lewall.dtos;

import java.util.UUID;

public class PublicPrivateDTO {
    public UUID userId;
    public UUID postId;
    public boolean isPrivate;

    public PublicPrivateDTO(UUID userId, UUID postId, boolean isPrivate) {
        this.userId = userId;
        this.postId = postId;
        this.isPrivate = isPrivate;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getPostId() {
        return postId;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }
}
