package com.lewall.db.models;

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

    private static UUID generateId() {
        return UUID.randomUUID();
    }
}
