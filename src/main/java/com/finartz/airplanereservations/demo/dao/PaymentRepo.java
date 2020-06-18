package com.finartz.airplanereservations.demo.dao;

import com.finartz.airplanereservations.demo.entity.Payment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaymentRepo extends CrudRepository<Payment,Integer> {
    @Override
    List<Payment> findAll();
}
