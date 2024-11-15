package com.lewall.dtos;

import com.lewall.db.models.Post;
import java.util.List;

public class FollowingPostsDTO {
    private List<List<Post>> posts;

    public FollowingPostsDTO(List<List<Post>> posts) {
        this.posts = posts;
    }

    public List<List<Post>> getPosts() {
        return posts;
    }

}