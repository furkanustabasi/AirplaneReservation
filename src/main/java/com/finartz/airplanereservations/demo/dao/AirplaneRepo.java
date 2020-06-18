package com.finartz.airplanereservations.demo.dao;

import com.finartz.airplanereservations.demo.entity.Airplane;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AirplaneRepo extends CrudRepository<Airplane,Integer> {
    @Override
    List<Airplane> findAll();
}
