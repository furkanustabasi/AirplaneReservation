package com.finartz.airplanereservations.demo.controllers;


import com.finartz.airplanereservations.demo.dao.*;
import com.finartz.airplanereservations.demo.dto.FlightDTO;
import com.finartz.airplanereservations.demo.dto.TicketDTO;
import com.finartz.airplanereservations.demo.entity.*;
import com.finartz.airplanereservations.demo.model.*;
import com.finartz.airplanereservations.demo.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Optional;

@RestController
public class TicketsController {

    @Autowired
    TicketRepo ticketRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    AirplaneRepo airplaneRepo;

    @Autowired
    RouteRepo routeRepo;

    @Autowired
    CompanyRepo companyRepo;

    @Autowired
    FlightRepo flightRepo;

    @Autowired
    AirportRepo airportRepo;


    @GetMapping("/tickets")
    public Response getById(@RequestParam(value = "id", defaultValue = "0") int id, HttpServletResponse res) {
        return arrangeTicketResult(id, res);
    }

    @PostMapping(path = "/tickets")
    public Response post(TicketDTO ticketDTO, HttpServletResponse res) {
        Optional<Customer> optionalCustomer = customerRepo.findById(ticketDTO.getCustomerId());
        Optional<Flight> optionalFlight = flightRepo.findById(ticketDTO.getFlightId());
        if (optionalCustomer.isPresent() && optionalFlight.isPresent()){
            Ticket ticket = new Ticket();
            try {
                ticket = new Mapper<>(ticket, ticketDTO).convertToEntity();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int ticketId = ticketRepo.save(ticket).getId();
            if (ticketId > 0) {
                return new SuccessModel(ticketId, "Bilet başarıyla eklendi.");
            } else {
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return new ErrorModel("Bilet eklenirken bir hata oluştu.");
            }
        }else{
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ErrorModel("Bilet eklenirken bir hata oluştu.");
        }

    }

    @PutMapping(path = "/tickets")
    public Response cancel(int ticketId, HttpServletResponse res) {
        Optional<Ticket> ticketOptional = ticketRepo.findById(ticketId);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            ticket.setCancelled(true);
            ticketRepo.save(ticket);
            return new SuccessModel(ticketId, "Bilet başarıyla iptal edildi.");
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ErrorModel("Bilet eklenirken bir hata oluştu.");
        }

    }


    public Response arrangeTicketResult(int ticketId, HttpServletResponse res) {
        Optional<Ticket> optionalTicket = ticketRepo.findById(ticketId);
        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            TicketDTO ticketDTO = new TicketDTO();
            Optional<Flight> optionalFlight = flightRepo.findById(ticket.getFlightId());
            if (optionalFlight.isPresent()) {
                Flight flight = optionalFlight.get();
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
                                Optional<Customer> optionalCustomer = customerRepo.findById(ticket.getCustomerId());
                                if (optionalCustomer.isPresent()) {
                                    Customer customer = optionalCustomer.get();
                                    try {
                                        ticketDTO = new Mapper<>(ticket, ticketDTO).convertToDTO();
                                        ticketDTO.setCustomerName(customer.getName());
                                        ticketDTO.setCompanyName(company.getName());
                                        ticketDTO.setFromAirportName(fromAirport.getName());
                                        ticketDTO.setToAirportName(toAirport.getName());
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                                    return new ErrorModel("Bilete ait müşteri bilgileri bulunamadı");
                                }

                            } else {
                                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                                return new ErrorModel("Bilete ait kalkış ya da varış havaalanı bulunamadı");
                            }

                        } else {
                            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                            return new ErrorModel("Bilete ait rota bulunamadı");
                        }

                    } else {
                        res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        return new ErrorModel("Bilete ait havayolu şirketi bulunamadı");
                    }
                } else {
                    res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return new ErrorModel("Bilete ait uçak bulunamadı");
                }

            } else {
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return new ErrorModel("Bilete ait uçuş bilgileri  bulunamadı.");
            }

            return ticketDTO;
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ErrorModel("Bilet bulunamadı");
        }
    }

}
