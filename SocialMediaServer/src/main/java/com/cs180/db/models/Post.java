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
    private int likes;
    private String[] usersLiked;
    private String imageURL;

    /**
     * Constructor for Post
     * 
     * @param userId
     * @param messagePost
     * @param date
     * @param postId
     * @param likes
     */
    public Post(UUID userId, String messagePost, String date, int likes, String imageURL) {
        this.userId = userId;
        this.messagePost = messagePost;
        this.date = date;
        this.likes = likes;
        this.imageURL = imageURL;
        this.usersLiked = new String[0];
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
     * Getter for likes
     * 
     * @return likes
     */
    public int getlikes() {
        return likes;
    }

    /**
     * getter for imageURL
     * 
     * @return imageURL
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * Getter for usersLiked
     * 
     * @return usersLiked
     */
    public String[] getUsersLiked() {
        return usersLiked;
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
     * Setter for likes
     * 
     * @param likes
     */
    public void setlikes(int likes) {
        this.likes = likes;
    }

    /**
     * Setter for usersLiked
     * 
     * @param usersLiked
     */
    public void setUsersLiked(String[] usersLiked) {
        this.usersLiked = usersLiked;
    }

    /**
     * Add a like to the post
     */
    public void addLike(String userId) {
        this.likes++;
        String[] newUsersLiked = new String[usersLiked.length + 1];
        for (int i = 0; i < usersLiked.length; i++) {
            newUsersLiked[i] = usersLiked[i];
        }
        newUsersLiked[usersLiked.length] = userId;
        usersLiked = newUsersLiked;
    }

    /**
     * Remove a like from the post
     */
    public void removeLike(String userId) {
        this.likes--;
        String[] newUsersLiked = new String[usersLiked.length - 1];
        int j = 0;
        for (int i = 0; i < usersLiked.length; i++) {
            if (!usersLiked[i].equals(userId)) {
                newUsersLiked[j] = usersLiked[i];
                j++;
            }
        }
        usersLiked = newUsersLiked;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
