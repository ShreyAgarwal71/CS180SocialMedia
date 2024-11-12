package com.cs180.dtos;

import java.util.UUID;

public class DeleteUserDTO {
    private UUID userId;

    public DeleteUserDTO(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }
}
