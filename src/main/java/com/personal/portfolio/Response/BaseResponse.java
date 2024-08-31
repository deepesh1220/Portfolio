package com.personal.portfolio.Response;

public class BaseResponse<T> {

    private String status;
    private String accessToken;
    private String message;
    private T payload;

    public BaseResponse(String status, String accessToken, String message, T payload) {
        this.status = status;
        this.accessToken = accessToken;
        this.message = message;
        this.payload = payload;
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
