package com.finartz.airplanereservations.demo.dao;

import com.finartz.airplanereservations.demo.entity.Flight;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FlightRepo extends CrudRepository<Flight,Integer> {

    @Override
    List<Flight> findAll();
}
