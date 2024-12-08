package com.lewall.common;

import com.lewall.db.models.Comment;
import com.lewall.db.models.User;

public class AggregatedComment {
    private Comment comment;
    private User user;

    public AggregatedComment(Comment comment, User user) {
        this.comment = comment;
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public User getUser() {
        return user;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
