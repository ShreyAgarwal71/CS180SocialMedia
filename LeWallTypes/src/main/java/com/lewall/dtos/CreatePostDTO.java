package com.lewall.dtos;

public class CreatePostDTO {
    private String messagePost;
    private String date;
    private String imageURL;

    public CreatePostDTO(String messagePost, String date, String imageURL) {
        this.messagePost = messagePost;
        this.date = date;
        this.imageURL = imageURL;
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
}
