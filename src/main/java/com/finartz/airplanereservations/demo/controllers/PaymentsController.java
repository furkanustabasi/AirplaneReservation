package com.finartz.airplanereservations.demo.controllers;

import com.finartz.airplanereservations.demo.dao.PaymentRepo;
import com.finartz.airplanereservations.demo.dao.TicketRepo;
import com.finartz.airplanereservations.demo.dto.PaymentDTO;
import com.finartz.airplanereservations.demo.entity.Payment;
import com.finartz.airplanereservations.demo.entity.Ticket;
import com.finartz.airplanereservations.demo.model.*;
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
public class PaymentsController {

    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    TicketRepo ticketRepo;

    @Autowired
    TicketsController ticketsController;


    @GetMapping("/payments")
    public Response getById(@RequestParam(value = "id", defaultValue = "0") int id, HttpServletResponse res) {
        Optional<Payment> optionalPayment = paymentRepo.findById(id);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
          //  PaymentResponseModel paymentResponseModel = new PaymentResponseModel(payment);
            PaymentDTO paymentDTO = new PaymentDTO();

          /*  Response ticketResponse = ticketsController.arrangeTicketResult(payment.getTicketId(), res);
            if (!ticketResponse.isSuccess) {
                return ticketResponse;
            } else if (ticketResponse instanceof TicketResponseModel) {
                paymentResponseModel.setTicket((TicketResponseModel) ticketResponse);
            } else {
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return new ErrorModel("Ödeme için ilgili bilete erişim esnasında bir hata oluştu.");
            }*/
            try {
                paymentDTO = new Mapper<>(payment,paymentDTO).convertToDTO();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return paymentDTO;
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ErrorModel("Ödeme bulunamadı.");
        }
    }

    @PostMapping(path = "/payments")
    public Response post(PaymentDTO paymentDTO, HttpServletResponse res) {
        Optional<Ticket> optionalTicket = ticketRepo.findById(paymentDTO.getTicketId());
        if (optionalTicket.isPresent()){
            if (paymentDTO.getMaskedCardNumber() != null) {
                Payment payment = new Payment();
                try {
                    payment = new Mapper<>(payment,paymentDTO).convertToEntity();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
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
        }else{
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ErrorModel("Ödeme esnasında bir hata oluştu.");
        }



    }
}
