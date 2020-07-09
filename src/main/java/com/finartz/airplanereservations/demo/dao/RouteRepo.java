package com.finartz.airplanereservations.demo.dao;

import com.finartz.airplanereservations.demo.entity.Route;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepo extends CrudRepository<Route,Integer> {

    @Override
    List<Route> findAll();
}
