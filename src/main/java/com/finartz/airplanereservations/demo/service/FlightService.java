package com.finartz.airplanereservations.demo.service;

import com.finartz.airplanereservations.demo.dao.*;
import com.finartz.airplanereservations.demo.dto.FlightDTO;
import com.finartz.airplanereservations.demo.entity.*;
import com.finartz.airplanereservations.demo.model.ErrorModel;
import com.finartz.airplanereservations.demo.model.SuccessModel;
import com.finartz.airplanereservations.demo.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Optional;

@Service
public class FlightService {

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

   /* public FlightDTO get(int id) {
        Optional<Flight> optionalFlight = flightRepo.findById(id);
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

                            return new ErrorModel("Uçuş'a ait kalkış ya da varış havaalanı bulunamadı");
                        }

                    } else {

                        return new ErrorModel("Uçuş'a ait rota bulunamadı");
                    }

                } else {

                    return new ErrorModel("Uçaş'a ait havayolu şirketi bulunamadı");
                }
            } else {

                return new ErrorModel("Uçuş'a ait uçak bulunamadı");
            }

            return flightDTO;
        } else {

            return new ErrorModel("Uçuş bulunamadı");
        }
    }*/

    public int post(FlightDTO flightDTO) {
        Optional<Route> optionalRoute = routeRepo.findById(flightDTO.getRouteId());
        Optional<Airplane> optionalAirplane = airplaneRepo.findById(flightDTO.getAirplaneId());
        if (optionalAirplane.isPresent() && optionalRoute.isPresent()) {
            Flight flight = new Flight();
            try {
                flight = new Mapper<>(flight, flightDTO).convertToEntity();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return flightRepo.save(flight).getId();
        } else {
            return 0;
        }
    }


    public int increaseQuota(int id,int newQuota){
        Optional<Flight> optionalFlight = flightRepo.findById(id);
        if (optionalFlight.isPresent()) {
            Flight flight = optionalFlight.get();
            Optional<Airplane> optionalAirplane = airplaneRepo.findById(flight.getAirplaneId());
            if (optionalAirplane.isPresent()) {
                Airplane airplane = optionalAirplane.get();
                if (newQuota <= airplane.getMaxQuota()) {
                    int oldQuota = flight.getQuota();
                    if (newQuota > oldQuota) {
                        flight.setQuota(newQuota);
                        double oldPrice = flight.getPrice();
                        flight.setPrice(Math.round(oldPrice * calculatePrice(oldQuota, newQuota)));
                        flightRepo.save(flight);//update selected flight quota
                        return id;
                    } else {
                        return 0;
                    }
                } else {
                    return -1;
                }
            } else {
                return 0;
            }
        } else {
            return 0;
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

}
