package com.finartz.airplanereservations.demo.dao;

import com.finartz.airplanereservations.demo.entity.Airplane;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirplaneRepo extends CrudRepository<Airplane,Integer> {
    @Override
    List<Airplane> findAll();
}
