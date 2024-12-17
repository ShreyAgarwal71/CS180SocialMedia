package com.lewall.dtos;

import java.util.Set;
import java.util.UUID;

public class LimitDTO {
    private int limit;
    private Set<UUID> requestedIds;
    private Set<UUID> excludedIds;

    public LimitDTO() {
        this.limit = -1; // no limit
        this.requestedIds = null;
    }

    public LimitDTO(int limit, Set<UUID> excludedIds) {
        this.limit = limit;
        this.excludedIds = excludedIds;
        this.requestedIds = null;
    }

    public LimitDTO(Set<UUID> requestedIds) {
        this.limit = -1; // no limit
        this.requestedIds = requestedIds;
    }

    public int getLimit() {
        return limit;
    }

    public Set<UUID> getRequestedIds() {
        return requestedIds;
    }

    public Set<UUID> getExcludedIds() {
        return excludedIds;
    }
}
