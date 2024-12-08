package com.lewall.dtos;

import java.util.List;

import com.lewall.common.AggregatedComment;

public class AggregatedCommentsDTO {
    private List<AggregatedComment> comments;

    public AggregatedCommentsDTO(List<AggregatedComment> comments) {
        this.comments = comments;
    }

    public List<AggregatedComment> getComments() {
        return comments;
    }
}
