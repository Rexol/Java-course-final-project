package com.example.bullsandcows.resources;

public class ErrorResponse {
    private final String type = "Error";
    private String message;

    public ErrorResponse() {
        this.message = "";
    }

    public String getMessage() {
        return this.message;
    }

    public String getType() {
        return this.type;
    }

    public void setMessage(String s) {
        this.message = s;
    }

}
