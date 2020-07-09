package com.finartz.airplanereservations.demo.dao;

import com.finartz.airplanereservations.demo.entity.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepo extends CrudRepository<Company, Integer> {

    @Override
    List<Company> findAll();

}
