package com.finartz.airplanereservations.demo.service;


import com.finartz.airplanereservations.demo.dao.AirportRepo;
import com.finartz.airplanereservations.demo.dao.RouteRepo;
import com.finartz.airplanereservations.demo.dto.RouteDTO;
import com.finartz.airplanereservations.demo.entity.Airport;
import com.finartz.airplanereservations.demo.entity.Route;
import com.finartz.airplanereservations.demo.model.ErrorModel;
import com.finartz.airplanereservations.demo.model.Response;
import com.finartz.airplanereservations.demo.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;

@Service
public class RouteService {

    @Autowired
    RouteRepo routeRepo;

    @Autowired
    AirportRepo airportRepo;

    public Response get(int id) {
        Optional<Route> optionalRoute = routeRepo.findById(id);
        if (optionalRoute.isPresent()) {
            Route incomingRoute = optionalRoute.get();
            Optional<Airport> fromAirportOptional = airportRepo.findById(incomingRoute.getFromAirportId());
            Optional<Airport> toAirportOptional = airportRepo.findById(incomingRoute.getToAirportId());

            if (fromAirportOptional.isPresent() && toAirportOptional.isPresent()) {
                Airport fromAirport = fromAirportOptional.get();
                Airport toAirport = toAirportOptional.get();
                RouteDTO routeDTO = new RouteDTO();

                try {
                    routeDTO = new Mapper<>(incomingRoute, routeDTO).convertToDTO();
                    routeDTO.setFromAirport(fromAirport.getName());
                    routeDTO.setToAirport(toAirport.getName());

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return routeDTO;
            } else {

                return new ErrorModel("Belirtilen rota bilgileri mevcut değil.");
            }
        } else {

            return new ErrorModel("Belirtilen rota bilgileri mevcut değil.");
        }
    }

    public int post(RouteDTO routeDTO){
        Optional<Airport> optionalFromAirport = airportRepo.findById(routeDTO.getFromAirportId());
        Optional<Airport> optionalToAirport = airportRepo.findById(routeDTO.getToAirportId());
        if (optionalFromAirport.isPresent() && optionalToAirport.isPresent()) {
            Route route = new Route();
            try {
                route = new Mapper<>(route, routeDTO).convertToEntity();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return routeRepo.save(route).getId();
        } else {
            return 0;
        }
    }
}
