package com.finartz.airplanereservations.demo.controllers;

import com.finartz.airplanereservations.demo.dto.PaymentDTO;
import com.finartz.airplanereservations.demo.model.ErrorModel;
import com.finartz.airplanereservations.demo.model.Response;
import com.finartz.airplanereservations.demo.model.SuccessModel;
import com.finartz.airplanereservations.demo.service.PaymetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class PaymentsController {


    @Autowired
    PaymetService paymetService;


    @GetMapping("/payments")
    public Response getById(@RequestParam(value = "id", defaultValue = "0") int id, HttpServletResponse res) {

        Response response = paymetService.get(id);
        if (response.getClass() != ErrorModel.class) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return response;
    }

    @PostMapping(path = "/payments")
    public Response post(PaymentDTO paymentDTO, HttpServletResponse res) {
        int responseId = paymetService.post(paymentDTO);
        if (responseId > 0) {
            return new SuccessModel(responseId, "Ödeme başarıyla eklendi.");
        } else if (responseId == -1) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ErrorModel("Ödeme esnasında bir hata oluştu.");
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ErrorModel("Ödeme esnasında bir hata oluştu.");
        }
    }
}
