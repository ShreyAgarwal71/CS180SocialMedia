package com.lewall.dtos;

import java.util.UUID;

public class ClassPostsDTO {
    private UUID userId;
    private UUID classId;

    public ClassPostsDTO(UUID userId, UUID classId) {
        this.userId = userId;
        this.classId = classId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getClassId() {
        return classId;
    }
}