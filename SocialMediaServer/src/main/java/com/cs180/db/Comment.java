package com.cs180.db;

import java.io.Serializable;

/**
 * Comment
 * 
 * This class represents a comment object that can be added to a post or another
 * comment.
 * 
 * 
 * @author Shrey Agarwal and Mahit Mehta, Lab 29927-L17
 *
 * @version November 2nd, 2024
 * 
 */
public class Comment implements Serializable {
    private String messageComment;
    private User user;
    private String date;
    private int commentID;
    private int votes;
    private Comment[] comments;

    /**
     * Constructor for Comment
     * 
     * @param messageComment
     * @param user
     * @param date
     * @param commentID
     * @param votes
     * @param comments
     */
    public Comment(String messageComment, User user, String date, int commentID, int votes, Comment[] comments) {
        this.messageComment = messageComment;
        this.user = user;
        this.date = date;
        this.commentID = commentID;
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
     * Getter for commentID
     * 
     * @return commentID
     */
    public int getCommentID() {
        return commentID;
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
     * Setter for messageComment
     * 
     * @param messageComment
     */
    public void setMessageComment(String messageComment) {
        this.messageComment = messageComment;
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
     * Setter for commentID
     * 
     * @param commentID
     */
    public void setCommentID(int commentID) {
        this.commentID = commentID;
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
        return c.messageComment.equals(messageComment) && c.user.equals(user) && c.date.equals(date)
                && c.commentID == commentID && c.votes == votes && c.comments.equals(comments);
    }

    /**
     * toString method for Comment
     * 
     * @return String
     */
    public String toString() {
        return messageComment + "," + user + "," + date + "," + commentID + "," + votes + "," + comments;
    }

}
