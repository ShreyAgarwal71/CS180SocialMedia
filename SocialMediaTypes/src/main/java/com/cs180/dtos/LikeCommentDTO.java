package com.cs180.dtos;

import java.util.UUID;

public class LikeCommentDTO {
    private UUID commentId;

    public LikeCommentDTO(UUID commentId) {
        this.commentId = commentId;
    }

    public UUID getCommentId() {
        return commentId;
    }
}
