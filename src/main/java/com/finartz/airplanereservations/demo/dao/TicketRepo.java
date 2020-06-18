package com.finartz.airplanereservations.demo.dao;

import com.finartz.airplanereservations.demo.entity.Ticket;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TicketRepo extends CrudRepository<Ticket, Integer> {
    @Override
    List<Ticket> findAll();

}
