package com.finartz.airplanereservations.demo.dao;

import com.finartz.airplanereservations.demo.entity.Airport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepo extends CrudRepository<Airport,Integer> {

    @Override
    List<Airport> findAll();
}
