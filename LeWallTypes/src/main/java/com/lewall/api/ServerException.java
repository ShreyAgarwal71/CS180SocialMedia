package com.lewall.api;

import com.lewall.api.Response.EStatus;

public abstract class ServerException extends RuntimeException {
    public ServerException(String message) {
        super(message);
    }

    public abstract EStatus getStatus();
}
