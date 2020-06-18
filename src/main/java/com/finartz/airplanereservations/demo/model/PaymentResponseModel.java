package com.finartz.airplanereservations.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finartz.airplanereservations.demo.entity.Payment;

public class PaymentResponseModel extends Response {

    @JsonIgnoreProperties("isSuccess")
    private TicketResponseModel ticket;

    private int id;
    private String maskedCardNumber;
    private boolean isCancelled;

    public PaymentResponseModel(Payment payment){
        this.id = payment.getId();
        this.maskedCardNumber = payment.getMaskedCardNumber();
        this.isCancelled = payment.isCancelled();
    }

    public TicketResponseModel getTicket() {
        return ticket;
    }

    public void setTicket(TicketResponseModel ticket) {
        this.ticket = ticket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaskedCardNumber() {
        return maskedCardNumber;
    }

    public void setMaskedCardNumber(String maskedCardNumber) {
        this.maskedCardNumber = maskedCardNumber;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
}
