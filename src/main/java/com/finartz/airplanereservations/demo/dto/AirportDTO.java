package com.finartz.airplanereservations.demo.dto;

import com.finartz.airplanereservations.demo.model.Response;

public class AirportDTO extends Response {
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
