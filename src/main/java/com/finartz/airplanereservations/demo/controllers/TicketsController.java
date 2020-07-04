package com.finartz.airplanereservations.demo.controllers;


import com.finartz.airplanereservations.demo.dao.CustomerRepo;
import com.finartz.airplanereservations.demo.dto.FlightDTO;
import com.finartz.airplanereservations.demo.dto.TicketDTO;
import com.finartz.airplanereservations.demo.entity.Customer;
import com.finartz.airplanereservations.demo.entity.Ticket;
import com.finartz.airplanereservations.demo.dao.TicketRepo;
import com.finartz.airplanereservations.demo.model.*;
import com.finartz.airplanereservations.demo.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Optional;

@RestController
public class TicketsController {

    @Autowired
    TicketRepo ticketRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    FlightsController flightsController;

    @GetMapping("/tickets")
    public Response getById(@RequestParam(value = "id", defaultValue = "0") int id, HttpServletResponse res) {
        return arrangeTicketResult(id, res);
    }

    @PostMapping(path = "/tickets")
    public Response post(TicketDTO ticketDTO, HttpServletResponse res) {
        Ticket ticket = new Ticket();
        try {
            ticket = new Mapper<>(ticket,ticketDTO).convertToEntity();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int ticketId = ticketRepo.save(ticket).getId();
        if (ticketId > 0) {
            return new SuccessModel(ticketId, "Bilet başarıyla eklendi.");
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ErrorModel("Bilet eklenirken bir hata oluştu.");
        }

    }

    @PutMapping(path = "/tickets")
    public Response cancel(int ticketId, HttpServletResponse res) {
        Optional<Ticket> ticketOptional = ticketRepo.findById(ticketId);
        if (ticketOptional.isPresent()){
            Ticket ticket = ticketOptional.get();
            ticket.setCancelled(true);
            ticketRepo.save(ticket);
            return new SuccessModel(ticketId,"Bilet başarıyla iptal edildi.");
        }else{
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ErrorModel("Bilet eklenirken bir hata oluştu.");
        }

    }


    public Response arrangeTicketResult(int ticketId, HttpServletResponse res) {
        Optional<Ticket> optionalTicket = ticketRepo.findById(ticketId);
        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            TicketResponseModel ticketResponseModel = new TicketResponseModel(ticket);

            Response flightResponse = flightsController.arrangeFlightResult(ticket.getFlightId(), res);

            if (!flightResponse.isSuccess) {
                return flightResponse;
            } else if (flightResponse instanceof FlightDTO) {
                ticketResponseModel.setFlight((FlightResponseModel) flightResponse);
            } else {
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return new ErrorModel("Bilete ait uçuş bilgileri  getirilirken bir hata oluştu.");
            }

            Optional<Customer> optionalCustomer = customerRepo.findById(ticket.getCustomerId());
            if (optionalCustomer.isPresent()) {
                Customer customer = optionalCustomer.get();
                ticketResponseModel.setCustomer(customer);
            } else {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return new ErrorModel("Bilete ait müşteri bilgileri bulunamadı");
            }

            return ticketResponseModel;
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ErrorModel("Bilet bulunamadı");
        }
    }

}
