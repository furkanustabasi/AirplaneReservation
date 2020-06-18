package com.finartz.airplanereservations.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finartz.airplanereservations.demo.entity.Airport;
import com.finartz.airplanereservations.demo.entity.Route;

public class RouteResponseModel extends Response {

    private int id;
    @JsonIgnoreProperties("isSuccess")
    private Airport from;
    @JsonIgnoreProperties("isSuccess")
    private Airport to;

    public RouteResponseModel(int id, Airport from, Airport to){
        this.id = id;
        this.from = from;
        this.to = to;
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
