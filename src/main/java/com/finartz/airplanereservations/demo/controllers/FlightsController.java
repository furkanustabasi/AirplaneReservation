package com.finartz.airplanereservations.demo.controllers;

import com.finartz.airplanereservations.demo.dto.FlightDTO;
import com.finartz.airplanereservations.demo.model.ErrorModel;
import com.finartz.airplanereservations.demo.model.Response;
import com.finartz.airplanereservations.demo.model.SuccessModel;
import com.finartz.airplanereservations.demo.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class FlightsController {

    @Autowired
    FlightService flightService;


    @GetMapping("/flights")
    public Response getById(@RequestParam(value = "id", defaultValue = "0") int id, HttpServletResponse res) {
        Response response = flightService.get(id);
        if(response.getClass() == ErrorModel.class){
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return response;
    }

    @PostMapping(path = "/flights")
    public Response post(FlightDTO flightDTO, HttpServletResponse res) {
        int flightId = flightService.post(flightDTO);
        if (flightId > 0) {
            return new SuccessModel(flightId, "Uçuş başarıyla eklendi.");
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ErrorModel("Uçuş eklenirken bir hata oluştu.");
        }
    }

    @PutMapping(path = "/flights")
    public Response increaseQuota(int flightId, int newQuota, HttpServletResponse res) {
        int responseId = flightService.increaseQuota(flightId, newQuota);
        if (!(responseId == 0)) {
            if (!(responseId == -1)) {
                return new SuccessModel(responseId, "Uçuş başarıyla güncellendi.");
            } else {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return new ErrorModel("Uçuş kotası uçak kotasını aşmıştır.");
            }
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ErrorModel("Uçuş güncellenirken bir hata oluştu.");
        }
    }

}
