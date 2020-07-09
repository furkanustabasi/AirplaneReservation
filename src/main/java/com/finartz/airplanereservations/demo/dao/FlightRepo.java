package com.finartz.airplanereservations.demo.dao;

import com.finartz.airplanereservations.demo.entity.Flight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepo extends CrudRepository<Flight,Integer> {

    @Override
    List<Flight> findAll();
}
