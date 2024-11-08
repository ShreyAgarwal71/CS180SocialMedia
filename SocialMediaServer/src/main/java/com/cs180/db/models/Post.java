package com.cs180.db.models;

import java.util.UUID;

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
public class Post extends Model {
    private UUID userId;
    private String messagePost;
    private String date;
    private int votes;
    private String imageURL;

    /**
     * Constructor for Post
     * 
     * @param userId
     * @param messagePost
     * @param date
     * @param postId
     * @param votes
     */
    public Post(UUID userId, String messagePost, String date, int votes, String imageURL) {
        this.userId = userId;
        this.messagePost = messagePost;
        this.date = date;
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
     * Getter for userId
     * 
     * @return userId
     */
    public UUID getUserId() {
        return userId;
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
     * Setter for userId
     * 
     * @param userId
     */
    public void setUserId(UUID userId) {
        this.userId = userId;
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
}
