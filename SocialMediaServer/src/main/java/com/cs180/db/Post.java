package com.cs180.db;

/**
 * Post
 * 
 * This class represents a post object that can be added to a feed.
 * 
 * 
 * @author Shrey Agarwal
 *
 * @version October 29, 2024
 * 
 */
public class Post {
    private String messagePost;
    private User user;
    private String date;
    private int postID;
    private int votes;
    private Comment[] comments;

    /**
     * Constructor for Post
     * 
     * @param messagePost
     * @param user
     * @param date
     * @param postID
     * @param votes
     * @param comments
     */
    public Post(String messagePost, User user, String date, int postID, int votes, Comment[] comments) {
        this.messagePost = messagePost;
        this.user = user;
        this.date = date;
        this.postID = postID;
        this.votes = votes;
        this.comments = comments;
    }

    /**
     * Getter for messagePost
     * 
     * @return messagePost
     */
    public String getMessagePost() {
        return messagePost;
    }

    /**
     * Getter for user
     * 
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * Getter for date
     * 
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * Getter for postID
     * 
     * @return postID
     */
    public int getPostID() {
        return postID;
    }

    /**
     * Getter for votes
     * 
     * @return votes
     */
    public int getVotes() {
        return votes;
    }

    /**
     * Getter for comments
     * 
     * @return comments
     */
    public Comment[] getComments() {
        return comments;
    }

    /**
     * Setter for messagePost
     * 
     * @param messagePost
     */
    public void setMessagePost(String messagePost) {
        this.messagePost = messagePost;
    }

    /**
     * Setter for user
     * 
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter for date
     * 
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Setter for postID
     * 
     * @param postID
     */
    public void setPostID(int postID) {
        this.postID = postID;
    }

    /**
     * Setter for votes
     * 
     * @param votes
     */
    public void setVotes(int votes) {
        this.votes = votes;
    }

    /**
     * Setter for comments
     * 
     * @param comments
     */
    public void setComments(Comment[] comments) {
        this.comments = comments;
    }

    /**
     * Equals method for Post
     * 
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Post))
            return false;
        Post p = (Post) obj;
        return p.messagePost.equals(messagePost) && p.user.equals(user) && p.date.equals(date) && p.postID == postID
                && p.votes == votes && p.comments.equals(comments);
    }

    /**
     * toString method for Post
     * 
     * @return String
     */
    public String toString() {
        return messagePost + "," + user + "," + date + "," + postID + "," + votes + "," + comments;
    }

}
