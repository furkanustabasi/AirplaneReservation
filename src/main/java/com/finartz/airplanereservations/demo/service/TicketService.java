package com.finartz.airplanereservations.demo.service;

import com.finartz.airplanereservations.demo.dao.*;
import com.finartz.airplanereservations.demo.dto.TicketDTO;
import com.finartz.airplanereservations.demo.entity.*;
import com.finartz.airplanereservations.demo.model.ErrorModel;
import com.finartz.airplanereservations.demo.model.Response;
import com.finartz.airplanereservations.demo.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;

@Service
public class TicketService {

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

    public Response get(int id) {
        Optional<Ticket> optionalTicket = ticketRepo.findById(id);
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
                                    return ticketDTO;
                                } else {
                                    return new ErrorModel("Bilete ait müşteri bilgileri bulunamadı");
                                }

                            } else {
                                return new ErrorModel("Bilete ait kalkış ya da varış havaalanı bulunamadı");
                            }

                        } else {
                            return new ErrorModel("Bilete ait rota bulunamadı");
                        }

                    } else {
                        return new ErrorModel("Bilete ait havayolu şirketi bulunamadı");
                    }
                } else {
                    return new ErrorModel("Bilete ait uçak bulunamadı");
                }

            } else {
                return new ErrorModel("Bilete ait uçuş bilgileri  bulunamadı.");
            }


        } else {
            return new ErrorModel("Bilet bulunamadı");
        }
    }

    public int post(TicketDTO ticketDTO) {
        Optional<Customer> optionalCustomer = customerRepo.findById(ticketDTO.getCustomerId());
        Optional<Flight> optionalFlight = flightRepo.findById(ticketDTO.getFlightId());
        if (optionalCustomer.isPresent() && optionalFlight.isPresent()) {
            Ticket ticket = new Ticket();
            try {
                ticket = new Mapper<>(ticket, ticketDTO).convertToEntity();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int ticketId = ticketRepo.save(ticket).getId();
            if (ticketId > 0) {
                return ticketId;
            } else {
                return 0;
            }
        } else {
            return -1;
        }

    }

    public int cancelTicket(int id) {
        Optional<Ticket> ticketOptional = ticketRepo.findById(id);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            ticket.setCancelled(true);
            ticketRepo.save(ticket);
            return id;
        } else {
            return 0;
        }
    }

}
