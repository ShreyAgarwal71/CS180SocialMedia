package com.cs180.db;

import java.io.Serializable;

/**
 * Comment
 * 
 * This class represents a comment object that can be added to a post or another
 * comment.
 * 
 * 
 * @author Shrey Agarwal
 * @author Mahit Mehta
 * @version 2024-11-03
 * 
 */
public class Comment implements Serializable {
    private String messageComment;
    private String username;
    private String date;
    private int commentId;
    private int postId;
    private int votes;
    private Comment[] comments;

    /**
     * Constructor for Comment
     * 
     * @param messageComment
     * @param username
     * @param date
     * @param commentId
     * @param postId
     * @param votes
     * @param comments
     */
    public Comment(String messageComment, String username, String date, int commentId, int postId, int votes,
            Comment[] comments) {
        this.messageComment = messageComment;
        this.username = username;
        this.date = date;
        this.postId = postId;
        this.commentId = commentId;
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
     * Getter for username
     * 
     * @return username
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
     * Getter for commentID
     * 
     * @return commentID
     */
    public int getCommentId() {
        return commentId;
    }

    /**
     * Getter for postId
     * 
     * @return postId
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
     * Setter for username
     * 
     * @param username
     */
    public void setUser(String username) {
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
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter for commentID
     * 
     * @param commentID
     */
    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    /**
     * Setter for postId
     * 
     * @param postId
     */
    public void setPostId(int postId) {
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

    /**
     * Equals method for Comment
     * 
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Comment))
            return false;
        Comment c = (Comment) obj;
        return c.messageComment.equals(messageComment) && c.username.equals(username) && c.date.equals(date)
                && c.commentId == commentId && c.postId == postId && c.votes == votes && c.comments.equals(comments);
    }

    /**
     * toString method for Comment
     * 
     * @return String
     */
    public String toString() {
        return messageComment + "," + username + "," + date + "," + commentId + "," + postId + "," + votes + ","
                + comments;
    }

}
