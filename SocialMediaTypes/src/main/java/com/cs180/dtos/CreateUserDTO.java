package com.cs180.dtos;

public class CreateUserDTO {
    private String email;
    private String password;
    private String username;
    private String displayName;
    private String bio;

    public CreateUserDTO(String username, String password, String displayName, String bio, String email) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.displayName = displayName;
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getBio() {
        return bio;
    }
}
