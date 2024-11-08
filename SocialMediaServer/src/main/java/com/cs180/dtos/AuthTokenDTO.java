package com.cs180.dtos;

public class AuthTokenDTO {
    private final String token;

    public AuthTokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
