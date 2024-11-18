package com.lewall.db.models;

import java.util.HashSet;
import java.util.Set;

/**
 * User
 * 
 * This class represents a user object that can be added to a user collection. A
 * user contains a username, password, display name, and email.
 * 
 * 
 * @author Mahit Mehta and Shrey Agarwal
 * @version 2024-11-03
 * 
 */
public class User extends Model {
    private String username;
    private String password;
    private String displayName;
    private String bio;
    private String email;
    private Set<String> followers;
    private Set<String> blockedUsers;
    private Set<String> following;
    private Set<String> hiddenPosts;

    /**
     * Constructor for User
     * 
     * @param username
     * @param password
     * @param displayName
     * @param email
     */
    public User(String username, String password, String displayName, String bio, String email) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.bio = bio;
        this.email = email;
        this.followers = new HashSet<>();
        this.blockedUsers = new HashSet<>();
        this.following = new HashSet<>();
        this.hiddenPosts = new HashSet<>();
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
     * Getter for password
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter for displayName
     * 
     * @return displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Getter for bio
     * 
     * @return bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * Getter for email
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for username
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter for password
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Setter for displayName
     * 
     * @param displayName
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Setter for bio
     * 
     * @param bio
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Setter for email
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for followers
     * 
     * @return followers
     */
    public Set<String> getFollowers() {
        return followers;
    }

    /**
     * Setter for followers
     * 
     * @param followers
     */
    public void setFollowers(Set<String> followers) {
        this.followers = followers;
    }

    /**
     * Getter for blockedUsers
     * 
     * @return blockedUsers
     */
    public Set<String> getBlockedUsers() {
        return blockedUsers;
    }

    /**
     * Setter for blockedUsers
     * 
     * @param blockedUsers
     */
    public void setBlockedUsers(Set<String> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    /**
     * Getter for following
     * 
     * @return following
     */
    public Set<String> getFollowing() {
        return following;
    }

    /**
     * Setter for following
     * 
     * @param following
     */
    public void setFollowing(Set<String> following) {
        this.following = following;
    }

    /**
     * Getter for hiddenPosts
     * 
     * @return hiddenPosts
     */
    public Set<String> getHiddenPosts() {
        return hiddenPosts;
    }

    /**
     * Setter for hiddenPosts
     * 
     * @param hiddenPosts
     */
    public void setHiddenPosts(Set<String> hiddenPosts) {
        this.hiddenPosts = hiddenPosts;
    }

    /**
     * Follow a user
     * 
     * @param follower
     * @return boolean
     */
    public boolean followUser(String follower) {
        if (following.contains(follower)) {
            return false;
        }
        following.add(follower);
        return true;
    }

    /**
     * Unfollow a user
     * 
     * @param followingUser
     * @return boolean
     */
    public boolean unfollowUser(String followingUser) {
        if (!following.contains(followingUser)) {
            return false;
        }
        following.remove(followingUser);
        return true;
    }

    /**
     * Add a follower to the user
     * 
     * @param follower
     * @return boolean
     */
    public boolean addFollower(String follower) {
        if (followers.contains(follower)) {
            return false;
        }
        followers.add(follower);
        return true;
    }

    /**
     * Remove a follower from the user
     * 
     * @param follower
     */
    public boolean removeFollower(String follower) {
        if (!followers.contains(follower)) {
            return false;
        }
        followers.remove(follower);
        return true;
    }

    /**
     * Add a blocked user to the user
     * 
     * @param blockedUser
     */
    public boolean addBlockedUser(String blockedUser) {
        if (blockedUsers.contains(blockedUser)) {
            return false;
        }
        blockedUsers.add(blockedUser);
        return true;
    }

    /**
     * Remove a blocked user from the user
     * 
     * @param blockedUser
     */
    public boolean removeBlockedUser(String blockedUser) {
        if (!blockedUsers.contains(blockedUser)) {
            return false;
        }
        blockedUsers.remove(blockedUser);
        return true;
    }

    /**
     * Hide a post
     * 
     * @param postId
     */
    public boolean hidePost(String postId) {
        if (hiddenPosts.contains(postId)) {
            return false;
        }
        hiddenPosts.add(postId);
        return true;
    }

    /**
     * Unhide a post
     * 
     * @param postId
     */
    public boolean unhidePost(String postId) {
        if (!hiddenPosts.contains(postId)) {
            return false;
        }
        hiddenPosts.remove(postId);
        return true;
    }

    /**
     * equals method for User
     * 
     * @param obj
     * @return boolean
     */
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User user = (User) obj;
            return user.getUsername().equals(username) && user.getPassword().equals(password)
                    && user.getDisplayName().equals(displayName) && user.getEmail().equals(email);
        }
        return false;
    }

    /**
     * toString method for User
     * 
     * @return String
     */
    public String toString() {
        return username + "," + password + "," + displayName + "," + email;
    }

}
