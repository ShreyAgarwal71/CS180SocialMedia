package com.lewall.dtos;

import java.util.Set;
import java.util.UUID;

public class PostRefetchDTO {
    private Set<UUID> postIds;

    public PostRefetchDTO(Set<UUID> postIds) {
        this.postIds = postIds;
    }

    public Set<UUID> getPostIds() {
        return postIds;
    }
}
