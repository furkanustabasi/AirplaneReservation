package com.finartz.airplanereservations.demo.dto;

import com.finartz.airplanereservations.demo.model.Response;

import javax.validation.constraints.NotEmpty;

public class CustomerDTO extends Response {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
