package com.finartz.airplanereservations.demo.model;

public class ErrorModel extends Response {

    private String message;

    public ErrorModel(String message){
        super.isSuccess = false;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
