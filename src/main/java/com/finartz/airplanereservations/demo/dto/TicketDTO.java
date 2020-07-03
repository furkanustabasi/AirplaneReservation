package com.finartz.airplanereservations.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finartz.airplanereservations.demo.entity.Customer;
import com.finartz.airplanereservations.demo.entity.Ticket;
import com.finartz.airplanereservations.demo.model.Response;

public class TicketDTO extends Response {
    @JsonIgnoreProperties("isSuccess")
    private Customer customer;
    @JsonIgnoreProperties("isSuccess")
    private FlightDTO flight;

    private int id;
    private double price;
    private boolean isCancelled;

    public TicketDTO(Ticket ticket){
        this.id = ticket.getId();
        this.price = ticket.getPrice();
        this.isCancelled = ticket.isCancelled();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public FlightDTO getFlight() {
        return flight;
    }

    public void setFlight(FlightDTO flight) {
        this.flight = flight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
}
