package com.cs180.db.models;

import java.io.Serializable;
import java.util.UUID;

/**
 * Comment
 * 
 * This class represents a comment object that can be added to a post or another
 * comment.
 * 
 * 
 * @author Shrey Agarwal and Mahit Mehta
 * @version 2024-11-03
 * 
 */
public class Comment extends Model {
    private UUID userId;
    private UUID postId;
    private String messageComment;
    private String date;
    private int votes;
    private Comment[] comments;

    /**
     * Constructor for Comment
     * 
     * @param postId
     * @param userId
     * @param messageComment
     * @param date
     * @param votes
     * @param comments
     */
    public Comment(UUID userId, UUID postId, String messageComment, String date, int votes,
            Comment[] comments) {
        super();

        this.postId = postId;
        this.userId = userId;
        this.messageComment = messageComment;
        this.date = date;
        this.votes = votes;
        this.comments = comments;
    }

    /**
     * Getter for messageComment
     * 
     * @return messageComment
     */
    public String getMessageComment() {
        return messageComment;
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
     * Getter for postId
     * 
     * @return postId
     */
    public UUID getPostId() {
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

    /**
     * Getter for comments
     * 
     * @return comments
     */
    public Comment[] getComments() {
        return comments;
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
     * Setter for messageComment
     * 
     * @param messageComment
     */
    public void setMessageComment(String messageComment) {
        this.messageComment = messageComment;
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
     * Setter for postId
     * 
     * @param postId
     */
    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    /**
     * Setter for votes
     * 
     * @param votes
     */
    public void setVotes(int votes) {
        this.votes = votes;
    }
}
