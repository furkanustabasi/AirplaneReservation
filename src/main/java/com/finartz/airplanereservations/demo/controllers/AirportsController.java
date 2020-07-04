package com.finartz.airplanereservations.demo.controllers;


import com.finartz.airplanereservations.demo.dto.AirportDTO;
import com.finartz.airplanereservations.demo.entity.Airport;
import com.finartz.airplanereservations.demo.dao.AirportRepo;
import com.finartz.airplanereservations.demo.model.ErrorModel;
import com.finartz.airplanereservations.demo.model.Response;
import com.finartz.airplanereservations.demo.model.SuccessModel;
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
public class AirportsController {

    @Autowired
    AirportRepo repo;

    @GetMapping("/airports")
    public Response getById(@RequestParam(value = "id", defaultValue = "0") int id, HttpServletResponse res){
        Optional<Airport> airportOptional = repo.findById(id);
        if (airportOptional.isPresent()) {
            Airport airport = airportOptional.get();
            AirportDTO airportDTO = new AirportDTO();
            try {
                airportDTO = new Mapper<>(airport,airportDTO).convertToDTO();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return airportDTO;
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    @PostMapping(path = "/airports")
    public Response post(AirportDTO airportDTO, HttpServletResponse res){
       Airport airport = new Airport();
        try {
            airport = new Mapper<>(airport,airportDTO).convertToEntity();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int airportId = repo.save(airport).getId();
        if (airportId > 0) {
            return new SuccessModel(airport.getId(), "Havalimanı başarıyla eklendi.");
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ErrorModel("Havalimanı eklenirken bir hata oluştu.");
        }

    }
}
