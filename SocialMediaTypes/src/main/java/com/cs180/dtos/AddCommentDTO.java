package com.cs180.dtos;

import java.util.UUID;

public class AddCommentDTO {
    private UUID postId;
    private String messageComment;
    private String date;

    public AddCommentDTO(UUID postId, String messageComment, String date) {
        this.postId = postId;
        this.messageComment = messageComment;
        this.date = date;
    }

    public UUID getPostId() {
        return postId;
    }

    public String getMessageComment() {
        return messageComment;
    }

    public String getDate() {
        return date;
    }
}
