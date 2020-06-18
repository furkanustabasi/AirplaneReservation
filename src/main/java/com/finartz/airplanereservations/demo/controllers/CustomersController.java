package com.finartz.airplanereservations.demo.controllers;

import com.finartz.airplanereservations.demo.dao.CustomerRepo;
import com.finartz.airplanereservations.demo.entity.Customer;
import com.finartz.airplanereservations.demo.model.ErrorModel;
import com.finartz.airplanereservations.demo.model.Response;
import com.finartz.airplanereservations.demo.model.SuccessModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
public class CustomersController {
    @Autowired
    CustomerRepo repo;


    @GetMapping("/customers")
    public Response getById(@RequestParam(value = "id", defaultValue = "0") int id, HttpServletResponse res){
        Optional<Customer> customer = repo.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ErrorModel("Müşteri bulunamadı");
        }
    }

    @PostMapping(path = "/customers")
    public Response post(Customer customer, HttpServletResponse res){
        int customerId = repo.save(customer).getId();
        if (customerId > 0) {
            return new SuccessModel(customerId, "Müşteri başarıyla eklendi.");
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ErrorModel("Müşteri eklenirken bir hata oluştu.");
        }

    }
}
