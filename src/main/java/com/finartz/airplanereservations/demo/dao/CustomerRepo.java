package com.finartz.airplanereservations.demo.dao;

import com.finartz.airplanereservations.demo.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepo extends CrudRepository<Customer, Integer> {
}
