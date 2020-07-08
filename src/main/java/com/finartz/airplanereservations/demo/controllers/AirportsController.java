package com.finartz.airplanereservations.demo.controllers;


import com.finartz.airplanereservations.demo.dto.AirportDTO;
import com.finartz.airplanereservations.demo.model.ErrorModel;
import com.finartz.airplanereservations.demo.model.Response;
import com.finartz.airplanereservations.demo.model.SuccessModel;
import com.finartz.airplanereservations.demo.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AirportsController {

    @Autowired
    AirportService airportService;

    @GetMapping("/airports")
    public Response getById(@RequestParam(value = "id", defaultValue = "0") int id, HttpServletResponse res) {
        AirportDTO airportDTO = airportService.get(id);
        if (airportDTO != null) {
            return airportDTO;
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ErrorModel("Havalimanı bulunamadı");
        }
    }

    @PostMapping(path = "/airports")
    public Response post(AirportDTO airportDTO, HttpServletResponse res) {
        int airportId = airportService.post(airportDTO);
        if (airportId > 0) {
            return new SuccessModel(airportId, "Havalimanı başarıyla eklendi.");
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ErrorModel("Havalimanı eklenirken bir hata oluştu.");
        }
    }
}
