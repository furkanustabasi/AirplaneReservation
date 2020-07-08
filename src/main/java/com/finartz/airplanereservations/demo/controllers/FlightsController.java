package com.finartz.airplanereservations.demo.controllers;

import com.finartz.airplanereservations.demo.dao.*;
import com.finartz.airplanereservations.demo.dto.FlightDTO;
import com.finartz.airplanereservations.demo.entity.*;
import com.finartz.airplanereservations.demo.model.*;
import com.finartz.airplanereservations.demo.service.FlightService;
import com.finartz.airplanereservations.demo.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Optional;

@RestController
public class FlightsController {

    @Autowired
    FlightRepo flightRepo;
    @Autowired
    AirplaneRepo airplaneRepo;
    @Autowired
    CompanyRepo companyRepo;
    @Autowired
    RouteRepo routeRepo;
    @Autowired
    AirportRepo airportRepo;

    @Autowired
    FlightService flightService;


    @GetMapping("/flights")
    public Response getById(@RequestParam(value = "id", defaultValue = "0") int id, HttpServletResponse res) {
        return this.arrangeFlightResult(id, res);
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

    private double calculatePrice(int oldQuota, int newQuota) {
        float diffQuota = (float) (newQuota - oldQuota);
        double increasePercentage = (diffQuota / oldQuota) * 100;
        if (increasePercentage >= 10.0) {
            return 1.10;
        } else {
            return 1.0;
        }
    }

    public Response arrangeFlightResult(int flightId, HttpServletResponse res) {
        Optional<Flight> optionalFlight = flightRepo.findById(flightId);
        if (optionalFlight.isPresent()) {
            Flight flight = optionalFlight.get();
            FlightDTO flightDTO = new FlightDTO();
            Optional<Airplane> optionalAirplane = airplaneRepo.findById(flight.getAirplaneId());
            if (optionalAirplane.isPresent()) {
                Airplane airplane = optionalAirplane.get();
                Optional<Company> optionalCompany = companyRepo.findById(airplane.getCompanyId());
                if (optionalCompany.isPresent()) {
                    Company company = optionalCompany.get();
                    Optional<Route> optionalRoute = routeRepo.findById(flight.getRouteId());
                    if (optionalRoute.isPresent()) {
                        Route route = optionalRoute.get();
                        Optional<Airport> fromAirportOptional = airportRepo.findById(route.getFromAirportId());
                        Optional<Airport> toAirportOptional = airportRepo.findById(route.getToAirportId());
                        if (fromAirportOptional.isPresent() && toAirportOptional.isPresent()) {
                            Airport fromAirport = fromAirportOptional.get();
                            Airport toAirport = toAirportOptional.get();
                            try {
                                flightDTO = new Mapper<>(flight, flightDTO).convertToDTO();
                                flightDTO.setCompanyName(company.getName());
                                flightDTO.setFromAirportName(fromAirport.getName());
                                flightDTO.setToAirportName(toAirport.getName());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                            return new ErrorModel("Uçuş'a ait kalkış ya da varış havaalanı bulunamadı");
                        }

                    } else {
                        res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        return new ErrorModel("Uçuş'a ait rota bulunamadı");
                    }

                } else {
                    res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return new ErrorModel("Uçaş'a ait havayolu şirketi bulunamadı");
                }
            } else {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return new ErrorModel("Uçuş'a ait uçak bulunamadı");
            }

            return flightDTO;
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ErrorModel("Uçuş bulunamadı");
        }

    }
}
