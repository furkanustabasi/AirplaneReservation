package com.finartz.airplanereservations.demo.dao;

import com.finartz.airplanereservations.demo.entity.Route;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RouteRepo extends CrudRepository<Route,Integer> {

    @Override
    List<Route> findAll();
}
