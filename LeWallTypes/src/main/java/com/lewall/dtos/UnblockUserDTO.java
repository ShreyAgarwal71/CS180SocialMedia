package com.lewall.dtos;

import java.util.UUID;

public class UnblockUserDTO {
    private UUID unblockUserId;

    public UnblockUserDTO(UUID unblockUserId) {
        this.unblockUserId = unblockUserId;
    }

    public UUID getUnblockedUserId() {
        return unblockUserId;
    }
}
