package com.lewall.dtos;

import com.lewall.db.models.Post;
import java.util.List;

public class PostsDTO {
    private List<Post> posts;

    public PostsDTO(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

}
