package com.lewall.dtos;

import java.util.List;
import com.lewall.db.models.Comment;

public class CommentsDTO {
    List<Comment> comments;

    public CommentsDTO(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
