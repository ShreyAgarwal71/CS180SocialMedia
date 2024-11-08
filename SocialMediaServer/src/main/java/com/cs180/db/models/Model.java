package com.cs180.db.models;

import java.io.Serializable;
import java.util.UUID;

public class Model implements Serializable {
    private UUID id;

    public Model() {
        this.id = generateId();
    }

    public UUID getId() {
        return id;
    }

    public static UUID generateId() {
        return UUID.randomUUID();
    }
}
