package com.finartz.airplanereservations.demo.service;

import com.finartz.airplanereservations.demo.dao.AirportRepo;

import com.finartz.airplanereservations.demo.dto.AirportDTO;
import com.finartz.airplanereservations.demo.entity.Airport;
import com.finartz.airplanereservations.demo.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;

@Service
public class AirportService {

    @Autowired
    AirportRepo airportRepo;

    public AirportDTO get(int id) {
        Optional<Airport> airportOptional = airportRepo.findById(id);
        if (airportOptional.isPresent()) {
            Airport airport = airportOptional.get();
            AirportDTO airportDTO = new AirportDTO();
            try {
                airportDTO = new Mapper<>(airport, airportDTO).convertToDTO();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return airportDTO;
        } else {
            return null;
        }
    }

    public int post(AirportDTO airportDTO){
        Airport airport = new Airport();
        try {
            airport = new Mapper<>(airport, airportDTO).convertToEntity();
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return airportRepo.save(airport).getId();
    }


}
