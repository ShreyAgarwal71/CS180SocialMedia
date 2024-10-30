package com.cs180.db;

public class Post {
    private String messagePost;
    private User user;
    private String date;
    private int postID;
    private int votes;
    private Comment[] comments;

    public Post(String messagePost, User user, String date, int postID, int votes, Comment[] comments) {
        this.messagePost = messagePost;
        this.user = user;
        this.date = date;
        this.postID = postID;
        this.votes = votes;
        this.comments = comments;
    }

    public String getMessagePost() {
        return messagePost;
    }

    public User getUser() {
        return user;
    }

    public String getDate() {
        return date;
    }

    public int getPostID() {
        return postID;
    }

    public int getVotes() {
        return votes;
    }

    public Comment[] getComments() {
        return comments;
    }

    public void setMessagePost(String messagePost) {
        this.messagePost = messagePost;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public void setComments(Comment[] comments) {
        this.comments = comments;
    }

    public String toString() {
        return messagePost + "," + user + "," + date + "," + postID + "," + votes + "," + comments;
    }

}
