package com.lewall.dtos;

import com.lewall.db.models.Post;

public class PostDTO {
    private Post post;

    public PostDTO(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return post;
    }
}