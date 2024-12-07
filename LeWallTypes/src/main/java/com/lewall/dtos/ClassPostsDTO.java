package com.lewall.dtos;

import java.util.UUID;

public class ClassPostsDTO {
    private UUID userId;
    private String classId;

    public ClassPostsDTO(UUID userId, String classId) {
        this.userId = userId;
        this.classId = classId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getClassId() {
        return classId;
    }
}