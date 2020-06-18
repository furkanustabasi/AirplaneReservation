package com.finartz.airplanereservations.demo.dao;

import com.finartz.airplanereservations.demo.entity.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompanyRepo extends CrudRepository<Company, Integer> {

    @Override
    List<Company> findAll();

}
