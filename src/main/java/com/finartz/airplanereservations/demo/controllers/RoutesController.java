package com.finartz.airplanereservations.demo.controllers;

import com.finartz.airplanereservations.demo.dao.AirportRepo;
import com.finartz.airplanereservations.demo.entity.Airport;
import com.finartz.airplanereservations.demo.entity.Route;
import com.finartz.airplanereservations.demo.dao.RouteRepo;
import com.finartz.airplanereservations.demo.model.ErrorModel;
import com.finartz.airplanereservations.demo.model.Response;
import com.finartz.airplanereservations.demo.model.RouteResponseModel;
import com.finartz.airplanereservations.demo.model.SuccessModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
public class RoutesController {

    @Autowired
    RouteRepo routeRepo;

    @Autowired
    AirportRepo airportRepo;

    @GetMapping("/routes")
    public Response getById(@RequestParam(value = "id", defaultValue = "0") int id, HttpServletResponse res){
        Optional<Route> route = routeRepo.findById(id);

        if (route.isPresent()) {
            Route incomingRoute = route.get();

            Optional<Airport>  fromAirport = airportRepo.findById(incomingRoute.getFromAirportId());
            Optional<Airport>  toAirport = airportRepo.findById(incomingRoute.getToAirportId());

            if (fromAirport.isPresent() && toAirport.isPresent()) {
                return new RouteResponseModel(id, fromAirport.get(), toAirport.get());
            } else {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return new ErrorModel("Belirtilen rota bilgileri mevcut değil.");
            }
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ErrorModel("Rota bulunamadı");
        }
    }

    @PostMapping(path = "/routes")
    public Response post(Route route, HttpServletResponse res){
        int routeId = routeRepo.save(route).getId();
        if (routeId > 0) {
            return new SuccessModel(routeId, "Rota başarıyla eklendi.");
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ErrorModel("Rota eklenirken bir hata oluştu.");
        }
    }


}
