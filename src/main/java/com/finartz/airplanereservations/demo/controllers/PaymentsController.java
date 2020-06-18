package com.finartz.airplanereservations.demo.controllers;

import com.finartz.airplanereservations.demo.dao.PaymentRepo;
import com.finartz.airplanereservations.demo.entity.Payment;
import com.finartz.airplanereservations.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
public class PaymentsController {

    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    TicketsController ticketsController;


    @GetMapping("/payments")
    public Response getById(@RequestParam(value = "id", defaultValue = "0") int id, HttpServletResponse res) {
        Optional<Payment> optionalPayment = paymentRepo.findById(id);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            PaymentResponseModel paymentResponseModel = new PaymentResponseModel(payment);

            Response ticketResponse = ticketsController.arrangeTicketResult(payment.getTicketId(), res);
            if (!ticketResponse.isSuccess) {
                return ticketResponse;
            } else if (ticketResponse instanceof TicketResponseModel) {
                paymentResponseModel.setTicket((TicketResponseModel) ticketResponse);
            } else {
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return new ErrorModel("Ödeme için ilgili bilete erişim esnasında bir hata oluştu.");
            }

            return paymentResponseModel;
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ErrorModel("Ödeme bulunamadı.");
        }
    }

    @PostMapping(path = "/payments")
    public Response post(Payment payment, HttpServletResponse res) {
        if (payment.getMaskedCardNumber() != null) {
            int paymentId = paymentRepo.save(payment).getId();
            if (paymentId > 0) {
                return new SuccessModel(paymentId, "Ödeme başarıyla eklendi.");
            } else {
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return new ErrorModel("Ödeme esnasında bir hata oluştu.");
            }
        } else {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ErrorModel("Hatalı kart numarası.");
        }


    }
}
