package com.lewall.api;

import java.util.UUID;

/**
 * A class that represents a response from the server
 * 
 * @param <Body>
 *            The type of the body of the response
 * @version 17 November 2024
 */
public class Response<Body> {
    public enum EStatus {
        OK, INTERNAL_SERVER_ERROR, ENDPOINT_NOT_FOUND, BAD_REQUEST, UNKNOWN_ENDPOINT, SERVER_ERROR, UNAUTHORIZED, ACCESS_EXPIRED
    }

    private final Request.EMethod method;
    private final String endpoint;
    private final Body body;
    private final EStatus status;
    private final String bodyType;
    private final UUID requestId;

    /**
     * Create a new response
     * 
     * @param method
     *            The method of the request
     * @param endpoint
     *            The endpoint of the request
     * @param body
     *            The body of the response
     * @param status
     *            The status of the response
     * @param requestId
     *            The id of the request
     */
    public Response(Request.EMethod method, String endpoint, Body body, EStatus status, UUID requestId) {
        this.method = method;
        this.endpoint = endpoint;
        this.body = body;
        this.status = status;
        this.bodyType = body.getClass().getName();
        this.requestId = requestId;
    }

    /**
     * Get the id of the request
     * 
     * @return The id of the request
     */
    public UUID getRequestId() {
        return requestId;
    }

    /**
     * Get the method of the request
     * 
     * @return The method of the request
     */
    public Request.EMethod getMethod() {
        return method;
    }

    /**
     * Get the body type of the response
     * 
     * @return The body type of the response
     */
    public String getBodyType() {
        return bodyType;
    }

    /**
     * Get the endpoint of the request
     * 
     * @return The endpoint of the request
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * Get the body of the response
     * 
     * @return The body of the response
     */
    public Body getBody() {
        return body;
    }

    /**
     * Get the status of the response
     * 
     * @return The status of the response
     */
    public EStatus getStatus() {
        return status;
    }
}
