package com.cs180.db.models;

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
    private String[] followers;
    private String[] blockedUsers;
    private String[] following;

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
        this.followers = new String[0];
        this.blockedUsers = new String[0];
        this.following = new String[0];
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
    public String[] getFollowers() {
        return followers;
    }

    /**
     * Setter for followers
     * 
     * @param followers
     */
    public void setFollowers(String[] followers) {
        this.followers = followers;
    }

    /**
     * Getter for blockedUsers
     * 
     * @return blockedUsers
     */
    public String[] getBlockedUsers() {
        return blockedUsers;
    }

    /**
     * Setter for blockedUsers
     * 
     * @param blockedUsers
     */
    public void setBlockedUsers(String[] blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    /**
     * Getter for following
     * 
     * @return following
     */
    public String[] getFollowing() {
        return following;
    }

    /**
     * Setter for following
     * 
     * @param following
     */
    public void setFollowing(String[] following) {
        this.following = following;
    }

    /**
     * Follow a user
     * 
     * @param follower
     */
    public void followUser(String follower) {
        String[] newFollowing = new String[following.length + 1];
        for (int i = 0; i < following.length; i++) {
            newFollowing[i] = following[i];
        }
        newFollowing[following.length] = follower;
        following = newFollowing;
    }

    /**
     * Unfollow a user
     * 
     * @param followingUser
     */
    public void unfollowUser(String followingUser) {
        String[] newFollowing = new String[following.length - 1];
        int j = 0;
        for (int i = 0; i < following.length; i++) {
            if (!following[i].equals(followingUser)) {
                newFollowing[j] = following[i];
                j++;
            }
        }
        following = newFollowing;
    }

    /**
     * Add a follower to the user
     * 
     * @param follower
     */
    public void addFollower(String follower) {
        String[] newFollowers = new String[followers.length + 1];
        for (int i = 0; i < followers.length; i++) {
            newFollowers[i] = followers[i];
        }
        newFollowers[followers.length] = follower;
        followers = newFollowers;
    }

    /**
     * Remove a follower from the user
     * 
     * @param follower
     */
    public void removeFollower(String follower) {
        String[] newFollowers = new String[followers.length - 1];
        int j = 0;
        for (int i = 0; i < followers.length; i++) {
            if (!followers[i].equals(follower)) {
                newFollowers[j] = followers[i];
                j++;
            }
        }
        followers = newFollowers;
    }

    /**
     * Add a blocked user to the user
     * 
     * @param blockedUser
     */
    public void addBlockedUser(String blockedUser) {
        String[] newBlockedUsers = new String[blockedUsers.length + 1];
        for (int i = 0; i < blockedUsers.length; i++) {
            newBlockedUsers[i] = blockedUsers[i];
        }
        newBlockedUsers[blockedUsers.length] = blockedUser;
        blockedUsers = newBlockedUsers;
    }

    /**
     * Remove a blocked user from the user
     * 
     * @param blockedUser
     */
    public void removeBlockedUser(String blockedUser) {
        String[] newBlockedUsers = new String[blockedUsers.length - 1];
        int j = 0;
        for (int i = 0; i < blockedUsers.length; i++) {
            if (!blockedUsers[i].equals(blockedUser)) {
                newBlockedUsers[j] = blockedUsers[i];
                j++;
            }
        }
        blockedUsers = newBlockedUsers;
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
