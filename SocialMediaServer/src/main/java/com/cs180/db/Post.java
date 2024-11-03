package com.cs180.db;

import java.io.Serializable;

/**
 * Post
 * 
 * This class represents a post object that can be added to a feed.
 * 
 * 
 * @author Shrey Agarwal and Mahit Mehta
 *
 * @version November 2nd, 2024
 * 
 */
public class Post implements Serializable {
    private String messagePost;
    private String username;
    private String date;
    private int postID;
    private int votes;
    private String imageURL;
    private Comment[] comments;

    /**
     * Constructor for Post
     * 
     * @param messagePost
     * @param username
     * @param date
     * @param postID
     * @param votes
     * @param comments
     */
    public Post(String messagePost, String username, String date, int postID, int votes, String imageURL,
            Comment[] comments) {
        this.messagePost = messagePost;
        this.username = username;
        this.date = date;
        this.postID = postID;
        this.votes = votes;
        this.imageURL = imageURL;
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
    public String getUsername() {
        return username;
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

    public String getImageURL() {
        return imageURL;
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
    public void setUsername(String username) {
        this.username = username;
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

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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
        return p.messagePost.equals(messagePost) && p.username.equals(username) && p.date.equals(date)
                && p.postID == postID
                && p.votes == votes && p.imageURL.equals(imageURL) && p.comments.equals(comments);
    }

    /**
     * toString method for Post
     * 
     * @return String
     */
    public String toString() {
        return messagePost + "," + username + "," + date + "," + postID + "," + votes + "," + imageURL + "," + comments;
    }

}
