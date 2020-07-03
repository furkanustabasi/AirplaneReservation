package com.finartz.airplanereservations.demo.controllers;

import com.finartz.airplanereservations.demo.dao.CustomerRepo;
import com.finartz.airplanereservations.demo.dto.CustomerDTO;
import com.finartz.airplanereservations.demo.entity.Customer;
import com.finartz.airplanereservations.demo.model.ErrorModel;
import com.finartz.airplanereservations.demo.model.Response;
import com.finartz.airplanereservations.demo.model.SuccessModel;
import com.finartz.airplanereservations.demo.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Optional;

@RestController
public class CustomersController {
    @Autowired
    CustomerRepo repo;


    @GetMapping("/customers")
    public Response getById(@RequestParam(value = "id", defaultValue = "0") int id, HttpServletResponse res) {
        Optional<Customer> customerOptional = repo.findById(id);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            CustomerDTO customerDTO = new CustomerDTO();
            try {
                customerDTO = new Mapper<>(customer, customerDTO).convertToDTO();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return customerDTO;
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    @PostMapping(path = "/customers")
    public Response post(CustomerDTO customerDTO, HttpServletResponse res) {
        Customer customer = new Customer();
        try {
            customer = new Mapper<>(customer, customerDTO).convertToEntity();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int customerId = repo.save(customer).getId();
        if (customerId > 0) {
            return new SuccessModel(customerDTO.getId(), "Müşteri başarıyla eklendi.");
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ErrorModel("Müşteri eklenirken bir hata oluştu.");
        }

    }
}
