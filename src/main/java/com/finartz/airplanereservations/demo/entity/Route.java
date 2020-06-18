package com.finartz.airplanereservations.demo.entity;

import com.finartz.airplanereservations.demo.model.Response;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Route extends Response {

    @Id
    @GeneratedValue
    private int id;
    //@Min(value = 1, message = "Deneme")
    private int fromAirportId;
    private int toAirportId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromAirportId() {
        return fromAirportId;
    }

    public void setFromAirportId(int fromAirportId) {
        this.fromAirportId = fromAirportId;
    }

    public int getToAirportId() {
        return toAirportId;
    }

    public void setToAirportId(int toAirportId) {
        this.toAirportId = toAirportId;
    }
}
