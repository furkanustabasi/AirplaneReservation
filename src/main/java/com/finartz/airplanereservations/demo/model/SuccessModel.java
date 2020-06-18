package com.finartz.airplanereservations.demo.model;

public class SuccessModel extends Response {
    private int id;
    private String message;

    public SuccessModel(int id, String message){
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
