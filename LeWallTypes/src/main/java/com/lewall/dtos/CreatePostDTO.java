package com.lewall.dtos;

import java.util.UUID;

public class CreatePostDTO {
    private String messagePost;
    private String date;
    private String imageURL;
    private UUID classId;

    public CreatePostDTO(String messagePost, String date, String imageURL, UUID classId) {
        this.messagePost = messagePost;
        this.date = date;
        this.imageURL = imageURL;
        this.classId = classId;
    }

    public String getMessagePost() {
        return messagePost;
    }

    public String getDate() {
        return date;
    }

    public String getImageURL() {
        return imageURL;
    }

    public UUID getClassId() {
        return classId;
    }

}
