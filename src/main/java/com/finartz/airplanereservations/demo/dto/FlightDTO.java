package com.finartz.airplanereservations.demo.dto;

import com.finartz.airplanereservations.demo.model.Response;

public class FlightDTO extends Response {


    private int id;
    private int airplaneId;
    private int routeId;
    private  String departureTime;
    private  String arrivalTime;
    private  int quota;
    private  double price;
    private String currency;
    private  boolean isCancelled;

    private String fromAirportName;
    private String toAirportName;
    private String companyName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(int airplaneId) {
        this.airplaneId = airplaneId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
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

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public String getFromAirportName() {
        return fromAirportName;
    }

    public void setFromAirportName(String fromAirportName) {
        this.fromAirportName = fromAirportName;
    }

    public String getToAirportName() {
        return toAirportName;
    }

    public void setToAirportName(String toAirportName) {
        this.toAirportName = toAirportName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }





}
