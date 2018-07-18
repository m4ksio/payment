package io.m4ks.payment;

public class ApiResponse {
    public ApiResponse(Object data) {
        this.data = data;
    }

    public ApiResponse(Object data, Object links) {
        this.data = data;
        this.links = links;
    }

    public Object data;
    public Object links;
}
