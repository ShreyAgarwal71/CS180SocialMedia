package com.cs180.api;

public class Request<Body> {
    public enum EMethod {
        GET, POST, UNKNOWN
    }

    private final EMethod method;
    private Body body;
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

    public void setBody(Body body) {
        this.body = body;
    }
}
