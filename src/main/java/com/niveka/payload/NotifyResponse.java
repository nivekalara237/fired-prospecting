package com.niveka.payload;

/**
 * Created by Nivek@lara on 31/05/2019.
 */

public class NotifyResponse {
    private String response;
    private int statusCode;

    public NotifyResponse(String response, int statusCode) {
        this.response = response;
        this.statusCode = statusCode;
    }

    public NotifyResponse() {}

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "NotifyResponse{" +
            "response='" + response + '\'' +
            ", statusCode=" + statusCode +
            '}';
    }
}
