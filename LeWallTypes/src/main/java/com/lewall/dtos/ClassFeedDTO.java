package com.lewall.dtos;

import com.lewall.db.models.Post;
import java.util.List;

public class ClassFeedDTO {
    private List<Post> posts;

    public ClassFeedDTO(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }

}