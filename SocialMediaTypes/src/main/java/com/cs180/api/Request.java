package com.cs180.api;

import java.util.HashMap;
import java.util.UUID;

public class Request<Body> {
    public enum EMethod {
        GET, POST, UNKNOWN
    }

    public enum EHeader {
        ACCESS_TOKEN
    }

    private final EMethod method;
    private final Body body;
    private final String endpoint;
    private final String bodyType;
    private final HashMap<EHeader, String> headers;
    private UUID userId;

    public Request(EMethod method, String endpoint, Body body, HashMap<EHeader, String> headers) {
        this.method = method;
        this.endpoint = endpoint;
        this.body = body;
        this.bodyType = body.getClass().getName();
        this.headers = headers;
        this.userId = null;
    }

    public HashMap<EHeader, String> getHeaders() {
        return headers;
    }

    public EMethod getMethod() {
        return method;
    }

    public Body getBody() {
        return body;
    }

    public String getBodyType() {
        return bodyType;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}