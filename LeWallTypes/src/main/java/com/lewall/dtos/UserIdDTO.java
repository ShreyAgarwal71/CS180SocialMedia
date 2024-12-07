package com.lewall.dtos;

import java.util.UUID;

public class UserIdDTO {
    private UUID userId;

    public UserIdDTO(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }
}
