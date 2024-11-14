package com.cs180.dtos;

import com.cs180.db.models.Post;
import java.util.List;

public class PostsDTO {
    private List<Post> posts;

    public PostsDTO(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }

}
