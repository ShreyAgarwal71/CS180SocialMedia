package com.lewall.api;

import com.lewall.api.Response.EStatus;

public class BadRequest extends ServerException {
    public BadRequest(String message) {
        super(message);
    }

    @Override
    public EStatus getStatus() {
        return EStatus.BAD_REQUEST;
    }
}
