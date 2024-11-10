package com.cs180.api;

import java.util.HashMap;

public class Request<Body> {
    public enum EMethod {
        GET, POST, UNKNOWN
    }

    private final EMethod method;
    private final Body body;
    private final String endpoint;
    private final String bodyType;
    private final HashMap<String, String> headers;

    public Request(EMethod method, String endpoint, Body body, HashMap<String, String> headers) {
        this.method = method;
        this.endpoint = endpoint;
        this.body = body;
        this.bodyType = body.getClass().getName();
        this.headers = headers;
    }

    public HashMap<String, String> getHeaders() {
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
}
