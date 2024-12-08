package com.lewall.dtos;

import com.lewall.db.models.Comment;

public class CommentDTO {
    private Comment comment;

    public CommentDTO(Comment comment) {
        this.comment = comment;
    }

    public Comment getComment() {
        return comment;
    }
}
