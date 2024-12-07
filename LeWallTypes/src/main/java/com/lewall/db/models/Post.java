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
 * @author Shrey Agarwal and Mahit Mehta
 * @version 2024-11-03
 * 
 */
public class Post extends Model {
    private UUID userId;
    private String messagePost;
    private String date;
    private int likes;
    private Set<String> usersLiked;
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
    public Post(UUID userId, String messagePost, String date, int likes, String imageURL, String classId) {
        this.userId = userId;
        this.messagePost = messagePost;
        this.date = date; // MM/DD/YYYY
        this.likes = likes;
        this.imageURL = imageURL;
        this.usersLiked = new HashSet<>();
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
     * Setter for likes
     * 
     * @param likes
     */
    public void setLikes(int likes) {
        this.likes = likes;
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
     * Add a like to the post
     * 
     * @param userId
     * @return boolean
     */
    public boolean addLike(String userId) {
        if (usersLiked.add(userId)) {
            this.likes++;
            return true;
        }
        return false;
    }

    /**
     * Remove a like from the post
     * 
     * @param userId
     * @return boolean
     */
    public boolean removeLike(String userId) {
        if (usersLiked.remove(userId)) {
            this.likes--;
            return true;
        }
        return false;
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
