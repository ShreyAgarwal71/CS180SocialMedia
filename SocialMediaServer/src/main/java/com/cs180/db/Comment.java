package com.cs180.db;

public class Comment {
    private String messageComment;
    private User user;
    private String date;
    private int commentID;
    private int votes;
    private Comment[] comments;

    public Comment(String messageComment, User user, String date, int commentID, int votes, Comment[] comments) {
        this.messageComment = messageComment;
        this.user = user;
        this.date = date;
        this.commentID = commentID;
        this.votes = votes;
        this.comments = comments;
    }

    public String getMessageComment() {
        return messageComment;
    }

    public User getUser() {
        return user;
    }

    public String getDate() {
        return date;
    }

    public int getCommentID() {
        return commentID;
    }

    public int getVotes() {
        return votes;
    }

    public Comment[] getComments() {
        return comments;
    }

    public void setMessageComment(String messageComment) {
        this.messageComment = messageComment;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public void setComments(Comment[] comments) {
        this.comments = comments;
    }

    public String toString() {
        return messageComment + "," + user + "," + date + "," + commentID + "," + votes + "," + comments;
    }

}
