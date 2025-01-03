package com.lewall.dtos;

import com.lewall.common.AggregatedPost;

import java.util.List;

public class FollowingPostsDTO {
    private List<AggregatedPost> posts;

    public FollowingPostsDTO(List<AggregatedPost> posts) {
        this.posts = posts;
    }

    public List<AggregatedPost> getAggregatedPosts() {
        return posts;
    }

}