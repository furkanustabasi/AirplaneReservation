package com.finartz.airplanereservations.demo.service;

import com.finartz.airplanereservations.demo.dao.CustomerRepo;
import com.finartz.airplanereservations.demo.dto.CustomerDTO;
import com.finartz.airplanereservations.demo.entity.Customer;
import com.finartz.airplanereservations.demo.model.ErrorModel;
import com.finartz.airplanereservations.demo.model.Response;
import com.finartz.airplanereservations.demo.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    public Response get(int id) {
        Optional<Customer> customerOptional = customerRepo.findById(id);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            CustomerDTO customerDTO = new CustomerDTO();
            try {
                customerDTO = new Mapper<>(customer,customerDTO ).convertToDTO();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return customerDTO;
        } else {
            return new ErrorModel("Şirket bulunamadı.");
        }
    }


    public int post(CustomerDTO customerDTO) {
        if (!customerDTO.getName().equals("")) {
            Customer customer = new Customer();
            try {
                customer = new Mapper<>(customer, customerDTO).convertToEntity();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return customerRepo.save(customer).getId();
       } else {
            return -1;
        }
    }
}
