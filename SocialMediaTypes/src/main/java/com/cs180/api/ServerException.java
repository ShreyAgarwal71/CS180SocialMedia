package com.cs180.api;

import com.cs180.api.Response.EStatus;

public abstract class ServerException extends RuntimeException {
    public ServerException(String message) {
        super(message);
    }

    public abstract EStatus getStatus();
}
