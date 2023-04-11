package com.app.ivans.ghimli.model;

public class APIResponse {
    private int status;
    private String message;
    private APIModels data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public APIModels getData() {
        return data;
    }

    public void setData(APIModels data) {
        this.data = data;
    }
}
