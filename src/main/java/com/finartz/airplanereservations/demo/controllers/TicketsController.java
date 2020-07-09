package com.finartz.airplanereservations.demo.controllers;


import com.finartz.airplanereservations.demo.dto.TicketDTO;
import com.finartz.airplanereservations.demo.model.ErrorModel;
import com.finartz.airplanereservations.demo.model.Response;
import com.finartz.airplanereservations.demo.model.SuccessModel;
import com.finartz.airplanereservations.demo.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class TicketsController {

    @Autowired
    TicketService ticketService;

    @GetMapping("/tickets")
    public Response getById(@RequestParam(value = "id", defaultValue = "0") int id, HttpServletResponse res) {
        Response response = ticketService.get(id);
        if (response.getClass() == ErrorModel.class) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return response;
    }

    @PostMapping(path = "/tickets")
    public Response post(TicketDTO ticketDTO, HttpServletResponse res) {
        int ticketId = ticketService.post(ticketDTO);
        if (!(ticketId == -1)) {
            if (ticketId > 0) {
                return new SuccessModel(ticketId, "Bilet başarıyla eklendi.");
            } else {
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return new ErrorModel("Bilet eklenirken bir hata oluştu.");
            }
        } else {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ErrorModel("Bilet eklenirken bir hata oluştu.");
        }
    }

    @PutMapping(path = "/tickets")
    public Response cancel(int ticketId, HttpServletResponse res) {
        int responseId = ticketService.cancelTicket(ticketId);
        if (responseId > 0) {
            return new SuccessModel(ticketId, "Bilet başarıyla iptal edildi.");
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ErrorModel("Bilet eklenirken bir hata oluştu.");
        }
    }

}
