package com.cs180.db;

import java.io.Serializable;

/**
 * Post
 * 
 * This class represents a post object that can be added to a feed.
 * 
 * 
 * @author Shrey Agarwal and Mahit Mehta
 * @version 2024-11-03
 * 
 */
public class Post implements Serializable {
    private String messagePost;
    private String username;
    private String date;
    private int postId;
    private int votes;
    private String imageURL;

    /**
     * Constructor for Post
     * 
     * @param messagePost
     * @param username
     * @param date
     * @param postId
     * @param votes
     */
    public Post(String messagePost, String username, String date, int postId, int votes, String imageURL) {
        this.messagePost = messagePost;
        this.username = username;
        this.date = date;
        this.postId = postId;
        this.votes = votes;
        this.imageURL = imageURL;
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
    public int getPostId() {
        return postId;
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
    public void setPostId(int postID) {
        this.postId = postID;
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
                && p.postId == postId
                && p.votes == votes && p.imageURL.equals(imageURL);
    }

    /**
     * toString method for Post
     * 
     * @return String
     */
    public String toString() {
        return messagePost + "," + username + "," + date + "," + postId + "," + votes + "," + imageURL;
    }

}
