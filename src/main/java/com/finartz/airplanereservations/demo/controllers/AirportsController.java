package com.finartz.airplanereservations.demo.controllers;


import com.finartz.airplanereservations.demo.entity.Airport;
import com.finartz.airplanereservations.demo.dao.AirportRepo;
import com.finartz.airplanereservations.demo.model.ErrorModel;
import com.finartz.airplanereservations.demo.model.Response;
import com.finartz.airplanereservations.demo.model.SuccessModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
public class AirportsController {

    @Autowired
    AirportRepo repo;

    @GetMapping("/airports")
    public Response getById(@RequestParam(value = "id", defaultValue = "0") int id, HttpServletResponse res){
        Optional<Airport> airport = repo.findById(id);
        if (airport.isPresent()) {
            return airport.get();
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ErrorModel("Havalimanı bulunamadı");
        }
    }

    @PostMapping(path = "/airports")
    public Response post(Airport airport, HttpServletResponse res){
        int airportId = repo.save(airport).getId();
        if (airportId > 0) {
            return new SuccessModel(airportId, "Havalimanı başarıyla eklendi.");
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ErrorModel("Havalimanı eklenirken bir hata oluştu.");
        }

    }
}
