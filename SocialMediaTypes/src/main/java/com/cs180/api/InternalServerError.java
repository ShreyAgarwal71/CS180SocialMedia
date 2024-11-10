package com.cs180.api;

import com.cs180.api.Response.EStatus;

public class InternalServerError extends ServerException {
    public InternalServerError(String message) {
        super(message);
    }

    @Override
    public EStatus getStatus() {
        return EStatus.INTERNAL_SERVER_ERROR;
    }
}
