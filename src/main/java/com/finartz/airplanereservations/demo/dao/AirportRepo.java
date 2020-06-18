package com.finartz.airplanereservations.demo.dao;

import com.finartz.airplanereservations.demo.entity.Airport;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AirportRepo extends CrudRepository<Airport,Integer> {

    @Override
    List<Airport> findAll();
}
