package com.finartz.airplanereservations.demo.service;

import com.finartz.airplanereservations.demo.dao.PaymentRepo;
import com.finartz.airplanereservations.demo.dao.TicketRepo;
import com.finartz.airplanereservations.demo.dto.PaymentDTO;
import com.finartz.airplanereservations.demo.entity.Payment;
import com.finartz.airplanereservations.demo.entity.Ticket;
import com.finartz.airplanereservations.demo.model.ErrorModel;
import com.finartz.airplanereservations.demo.model.Response;
import com.finartz.airplanereservations.demo.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;

@Service
public class PaymetService {

    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    TicketRepo ticketRepo;

    public Response get(int id) {
        Optional<Payment> optionalPayment = paymentRepo.findById(id);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            PaymentDTO paymentDTO = new PaymentDTO();
            try {
                paymentDTO = new Mapper<>(payment, paymentDTO).convertToDTO();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return paymentDTO;
        } else {
            return new ErrorModel("Ödeme bulunamadı.");
        }
    }

    public int post(PaymentDTO paymentDTO) {
        Optional<Ticket> optionalTicket = ticketRepo.findById(paymentDTO.getTicketId());
        if (optionalTicket.isPresent() && paymentDTO.getMaskedCardNumber() != null) {
            Payment payment = new Payment();
            try {
                payment = new Mapper<>(payment, paymentDTO).convertToEntity();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int paymentId = paymentRepo.save(payment).getId();
            if (paymentId > 0) {
                return paymentId;
            } else {
                return -1;
            }
        } else {
            return -2;
        }
    }


}
