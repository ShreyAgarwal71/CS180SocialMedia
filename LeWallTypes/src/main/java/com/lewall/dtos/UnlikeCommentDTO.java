package com.lewall.dtos;

import java.util.UUID;

public class UnlikeCommentDTO {
    private UUID commentId;

    public UnlikeCommentDTO(UUID commentId) {
        this.commentId = commentId;
    }

    public UUID getCommentId() {
        return commentId;
    }
}
