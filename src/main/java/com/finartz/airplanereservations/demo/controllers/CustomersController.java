package com.finartz.airplanereservations.demo.controllers;

import com.finartz.airplanereservations.demo.dto.CustomerDTO;
import com.finartz.airplanereservations.demo.model.ErrorModel;
import com.finartz.airplanereservations.demo.model.Response;
import com.finartz.airplanereservations.demo.model.SuccessModel;
import com.finartz.airplanereservations.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class CustomersController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/customers")
    public Response getById(@RequestParam(value = "id", defaultValue = "0") int id, HttpServletResponse res) {

        Response response = customerService.get(id);
        if (response.getClass() != ErrorModel.class) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return response;
    }

    @PostMapping(path = "/customers")
    public Response post(CustomerDTO customerDTO, HttpServletResponse res) {
        int customerId = customerService.post(customerDTO);

        if (customerId > 0) {
            return new SuccessModel(customerId, "Müşteri başarıyla eklendi.");
        } else if (customerId == -1) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ErrorModel("Müşteri eklenirken bir hata oluştu.");
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ErrorModel("Müşteri eklenirken bir hata oluştu.");
        }

    }
}
