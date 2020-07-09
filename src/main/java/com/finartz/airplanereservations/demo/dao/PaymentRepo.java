package com.finartz.airplanereservations.demo.dao;

import com.finartz.airplanereservations.demo.entity.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepo extends CrudRepository<Payment,Integer> {
    @Override
    List<Payment> findAll();
}
