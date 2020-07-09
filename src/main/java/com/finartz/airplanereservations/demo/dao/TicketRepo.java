package com.finartz.airplanereservations.demo.dao;

import com.finartz.airplanereservations.demo.entity.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepo extends CrudRepository<Ticket, Integer> {
    @Override
    List<Ticket> findAll();

}
