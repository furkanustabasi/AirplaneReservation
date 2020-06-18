package com.finartz.airplanereservations.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finartz.airplanereservations.demo.entity.Flight;

public class FlightResponseModel extends Response {

    @JsonIgnoreProperties("isSuccess")
    private AirplaneResponseModel airplane;
    @JsonIgnoreProperties("isSuccess")
    private RouteResponseModel route;

    private int id;
    private String departureTime;
    private String arrivalTime;
    private int quota;
    private double price;
    private String currency;
    private boolean isCancelled;

    public FlightResponseModel(Flight flight) {
        this.id = flight.getId();
        this.departureTime = flight.getDepartureTime();
        this.arrivalTime = flight.getArrivalTime();
        this.quota = flight.getQuota();
        this.price = flight.getPrice();
        this.currency = flight.getCurrency();
        this.isCancelled = flight.isCancelled();
    }

    public AirplaneResponseModel getAirplane() {
        return airplane;
    }

    public void setAirplane(AirplaneResponseModel airplane) {
        this.airplane = airplane;
    }

    public RouteResponseModel getRoute() {
        return route;
    }

    public void setRoute(RouteResponseModel route) {
        this.route = route;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double startedPrice) {
        this.price = price;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
