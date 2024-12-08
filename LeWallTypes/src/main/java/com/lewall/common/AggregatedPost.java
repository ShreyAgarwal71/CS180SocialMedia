package com.lewall.common;

import java.util.List;

import com.lewall.db.models.Comment;
import com.lewall.db.models.Post;
import com.lewall.db.models.User;

public class AggregatedPost {
    private Post post;
    private List<Comment> comments;
    private User user;

    public AggregatedPost(Post post, List<Comment> comments, User user) {
        this.post = post;
        this.comments = comments;
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }
}
