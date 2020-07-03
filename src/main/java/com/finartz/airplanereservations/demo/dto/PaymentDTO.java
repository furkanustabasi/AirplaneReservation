package com.finartz.airplanereservations.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finartz.airplanereservations.demo.entity.Payment;
import com.finartz.airplanereservations.demo.model.Response;

public class PaymentDTO extends Response {

    @JsonIgnoreProperties("isSuccess")
    private TicketDTO ticket;

    private int id;
    private String maskedCardNumber;
    private boolean isCancelled;

    public PaymentDTO(Payment payment){
        this.id = payment.getId();
        this.maskedCardNumber = payment.getMaskedCardNumber();
        this.isCancelled = payment.isCancelled();
    }

    public TicketDTO getTicket() {
        return ticket;
    }

    public void setTicket(TicketDTO ticket) {
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
