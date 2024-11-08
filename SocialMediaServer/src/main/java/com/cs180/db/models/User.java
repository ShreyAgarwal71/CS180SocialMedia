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
    private String email;
    private String[] followers;
    private String[] blockedUsers;

    /**
     * Constructor for User
     * 
     * @param username
     * @param password
     * @param displayName
     * @param email
     */
    public User(String username, String password, String displayName, String email) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.email = email;
        this.followers = new String[0];
        this.blockedUsers = new String[0];
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
     * Setter for email
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Equals method for User
     * 
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User))
            return false;
        User u = (User) obj;
        return u.username.equals(username) && u.password.equals(password) && u.displayName.equals(displayName)
                && u.email.equals(email);
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
