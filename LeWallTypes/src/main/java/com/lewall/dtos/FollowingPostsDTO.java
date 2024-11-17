package com.lewall.dtos;

import com.lewall.db.models.Post;
import java.util.List;

public class FollowingPostsDTO {
    private List<Post> posts;

    public FollowingPostsDTO(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }

}