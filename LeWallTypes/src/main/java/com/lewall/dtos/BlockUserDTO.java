package com.lewall.dtos;

import java.util.UUID;

public class BlockUserDTO {
    private UUID blockUserId;

    public BlockUserDTO(UUID blockUserId) {
        this.blockUserId = blockUserId;
    }

    public UUID getBlockedUserId() {
        return blockUserId;
    }

}
