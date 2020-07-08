package com.finartz.airplanereservations.demo.dto;

import com.finartz.airplanereservations.demo.model.Response;

public class RouteDTO extends Response {

    private int id;
    private int toAirportId;
    private int fromAirportId;


    private String toAirport;
    private String fromAirport;

    public int getFromAirportId() {
        return fromAirportId;
    }

    public void setFromAirportId(int fromAirportId) {
        this.fromAirportId = fromAirportId;
    }

    public String getToAirport() {
        return toAirport;
    }

    public void setToAirport(String toAirport) {
        this.toAirport = toAirport;
    }

    public String getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(String fromAirport) {
        this.fromAirport = fromAirport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getToAirportId() {
        return toAirportId;
    }

    public void setToAirportId(int toAirportId) {
        this.toAirportId = toAirportId;
    }
}
