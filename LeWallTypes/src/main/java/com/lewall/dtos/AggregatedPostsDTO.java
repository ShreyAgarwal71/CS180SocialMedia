package com.lewall.dtos;

import java.util.List;

import com.lewall.common.AggregatedPost;

public class AggregatedPostsDTO {
    private List<AggregatedPost> posts;

    public AggregatedPostsDTO(List<AggregatedPost> posts) {
        this.posts = posts;
    }

    public List<AggregatedPost> getAggregatedPosts() {
        return posts;
    }
}
