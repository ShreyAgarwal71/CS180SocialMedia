package com.cs180.api;

import java.util.UUID;

public class Response<Body> {
    public enum EStatus {
        OK, INTERNAL_SERVER_ERROR, ENDPOINT_NOT_FOUND, BAD_REQUEST, UNKNOWN_ENDPOINT, SERVER_ERROR, UNAUTHORIZED
    }

    private final Request.EMethod method;
    private final String endpoint;
    private final Body body;
    private final EStatus status;
    private final String bodyType;
    private final UUID requestId;

    public Response(Request.EMethod method, String endpoint, Body body, EStatus status, UUID requestId) {
        this.method = method;
        this.endpoint = endpoint;
        this.body = body;
        this.status = status;
        this.bodyType = body.getClass().getName();
        this.requestId = requestId;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public Request.EMethod getMethod() {
        return method;
    }

    public String getBodyType() {
        return bodyType;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Body getBody() {
        return body;
    }

    public EStatus getStatus() {
        return status;
    }
}
