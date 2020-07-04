package com.finartz.airplanereservations.demo.entity;

import com.finartz.airplanereservations.demo.model.Response;
import com.finartz.airplanereservations.demo.utils.CardUtil;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Payment extends Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int ticketId;
    private String maskedCardNumber;
    private boolean isCancelled;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getMaskedCardNumber() {
        return CardUtil.maskCard(maskedCardNumber);
    }

    public void setMaskedCardNumber(String mastercardNumber) {
        this.maskedCardNumber = mastercardNumber;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
}
