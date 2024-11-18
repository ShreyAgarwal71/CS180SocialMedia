package com.lewall.api;

import com.lewall.api.Response.EStatus;

/**
 * A class that represents a BadRequest server exception
 * 
 * @author Mahit Mehta
 * @version 17 November 2024
 */
public class BadRequest extends ServerException {
    public BadRequest(String message) {
        super(message);
    }

    @Override
    public EStatus getStatus() {
        return EStatus.BAD_REQUEST;
    }
}
