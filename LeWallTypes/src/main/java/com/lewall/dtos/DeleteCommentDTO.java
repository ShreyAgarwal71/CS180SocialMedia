package com.lewall.dtos;

import java.util.UUID;

public class DeleteCommentDTO {
    private UUID commentId;

    public DeleteCommentDTO(UUID commentId) {
        this.commentId = commentId;
    }

    public UUID getCommentId() {
        return commentId;
    }
}
