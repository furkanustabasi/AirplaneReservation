package com.finartz.airplanereservations.demo.dao;

import com.finartz.airplanereservations.demo.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends CrudRepository<Customer, Integer> {
}
