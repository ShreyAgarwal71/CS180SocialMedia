package com.lewall.dtos;

public class CreatePostDTO {
    private String messagePost;
    private String date;
    private String imageURL;
    private String classId;

    public CreatePostDTO(String messagePost, String date, String imageURL, String classId) {
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

    public String getClassId() {
        return classId;
    }

}
