package com.lewall.api;

import com.lewall.api.Response.EStatus;

/**
 * A class that represents a server exception
 * 
 * @author Mahit Mehta
 * @version 17 November 2024
 */
public abstract class ServerException extends RuntimeException {
    public ServerException(String message) {
        super(message);
    }

    /**
     * Get the status of the exception
     * 
     * @return The status of the exception
     */
    public abstract EStatus getStatus();
}
