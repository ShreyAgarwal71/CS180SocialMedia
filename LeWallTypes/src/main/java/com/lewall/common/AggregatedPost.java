package com.lewall.common;

import java.util.List;

import com.lewall.db.models.Comment;
import com.lewall.db.models.Post;
import com.lewall.db.models.User;

public class AggregatedPost {
    private Post post;
    private List<AggregatedComment> comments;
    private User user;

    public AggregatedPost(Post post, List<AggregatedComment> comments, User user) {
        this.post = post;
        this.comments = comments;
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public List<AggregatedComment> getComments() {
        return comments;
    }

    public void setComments(List<AggregatedComment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }
}
