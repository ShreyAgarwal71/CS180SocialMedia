package com.cs180.api;

public class Request<Body> {
    public enum EMethod {
        GET, POST
    }

    private final EMethod method;
    private final Body body;
    // TODO: Replace with enum
    private final String endpoint;

    public Request(EMethod method, String endpoint, Body body) {
        this.method = method;
        this.endpoint = endpoint;
        this.body = body;
    }

    public EMethod getMethod() {
        return method;
    }

    public Body getBody() {
        return body;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
