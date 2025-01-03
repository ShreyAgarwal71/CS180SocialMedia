package com.lewall.db.models;

import java.util.UUID;
import java.util.HashSet;
import java.util.Set;

/**
 * Post
 * 
 * This class represents a post object that can be added to a feed.
 * 
 * 
 * @author Shrey Agarwal, Mahit Mehta, Ates Isfendiyaroglu
 * @version 8 December 2024
 * 
 */
public class Post extends Model {
    private UUID userId;
    private String messagePost;
    private String date;
    private int likes;
    private Set<String> usersLiked;
    private int dislikes;
    private Set<String> usersDisliked;
    private String imageURL;
    private String classId;
    private boolean isPrivate;

    /**
     * Constructor for Post
     * 
     * @param userId
     * @param messagePost
     * @param date
     * @param postId
     * @param likes
     */
    public Post(UUID userId, String messagePost, String date, int likes, int dislikes, String imageURL,
            String classId) {
        this.userId = userId;
        this.messagePost = messagePost;
        this.date = date; // MM/DD/YYYY
        this.likes = likes;
        this.dislikes = dislikes;
        this.imageURL = imageURL;
        this.usersLiked = new HashSet<>();
        this.usersDisliked = new HashSet<>();
        this.classId = classId;
        this.isPrivate = false;
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
    public int getLikes() {
        return likes;
    }

    /**
     * Getter for dislikes
     * 
     * @return dislikes
     */
    public int getDislikes() {
        return dislikes;
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
    public Set<String> getUsersLiked() {
        return usersLiked;
    }

    /**
     * Getter for usersDisliked
     *
     * @return usersDisliked
     */
    public Set<String> getUsersDisliked() {
        return usersDisliked;
    }

    /**
     * Getter for classId
     * 
     * @return classId
     */
    public String getClassId() {
        return classId;
    }

    /**
     * Getter for isPrivate
     * 
     * @return isPrivate
     */
    public boolean getIsPrivate() {
        return isPrivate;
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
     * Setter for dislikes
     * 
     * @param likes
     */
    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    /**
     * Setter for likes
     * 
     * @param likes
     */
    public void setLikes(int likes) {
        this.likes = likes;
    }

    /**
     * Setter for usersDisliked
     * 
     * @param usersLiked
     */
    public void setUsersDisliked(Set<String> usersDisliked) {
        this.usersDisliked = usersDisliked;
    }

    /**
     * Setter for usersLiked
     * 
     * @param usersLiked
     */
    public void setUsersLiked(Set<String> usersLiked) {
        this.usersLiked = usersLiked;
    }

    /**
     * Setter for classId
     * 
     * @param classId
     */
    public void setClassId(String classId) {
        this.classId = classId;
    }

    /**
     * Setter for isPrivate
     * 
     * @param isPrivate
     */
    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    /**
     * Toggle like of a post
     * 
     * @param userId
     */
    public void toggleLike(String userId) {
        if (usersLiked.contains(userId)) {
            usersLiked.remove(userId);
            this.likes--;
            return;
        }

        if (usersDisliked.contains(userId)) {
            usersDisliked.remove(userId);
            this.dislikes--;
        }

        usersLiked.add(userId);
        this.likes++;
    }

    /**
     * Toggle dislike of a post
     * 
     * @param userId
     * @return boolean
     */
    public void toggleDislike(String userId) {
        if (usersDisliked.contains(userId)) {
            usersDisliked.remove(userId);
            this.dislikes--;
            return;
        }

        if (usersLiked.contains(userId)) {
            usersLiked.remove(userId);
            this.likes--;
        }

        usersDisliked.add(userId);
        this.dislikes++;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * equals method for Post
     * 
     * @param obj
     */
    public boolean equals(Object obj) {
        if (obj instanceof Post) {
            Post post = (Post) obj;
            return post.getId().equals(post.getId());
        }
        return false;
    }

    /**
     * compereTo method for Post
     * 
     * @param post
     */
    public int compareTo(Post post) {
        String[] date1 = this.date.split("/");
        String[] date2 = post.getDate().split("/");
        if (Integer.parseInt(date1[2]) > Integer.parseInt(date2[2])) {
            return 1;
        } else if (Integer.parseInt(date1[2]) < Integer.parseInt(date2[2])) {
            return -1;
        } else {
            if (Integer.parseInt(date1[0]) > Integer.parseInt(date2[0])) {
                return 1;
            } else if (Integer.parseInt(date1[0]) < Integer.parseInt(date2[0])) {
                return -1;
            } else {
                if (Integer.parseInt(date1[1]) > Integer.parseInt(date2[1])) {
                    return 1;
                } else if (Integer.parseInt(date1[1]) < Integer.parseInt(date2[1])) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }

    /**
     * toString method for Post
     * 
     * @return String
     */
    public String toString() {
        return "Post: " + messagePost + " by " + userId + " on " + date + " with " + likes + " likes";
    }
}
