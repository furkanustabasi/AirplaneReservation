package com.finartz.airplanereservations.demo.controllers;

import com.finartz.airplanereservations.demo.entity.Flight;
import com.finartz.airplanereservations.demo.model.FlightResponseModel;
import com.finartz.airplanereservations.demo.model.SuccessModel;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import static org.junit.jupiter.api.Assertions.*;

class FlightsControllerTest {

    private MockHttpServletResponse res;
    private FlightResponseModel flightResponseModel;
    private Flight flight;
    private SuccessModel successModel;


    @Test
    void getById() {
        Flight flight = new Flight();
        flight.setId(1);
        flight.setAirplaneId(2);
        flight.setRouteId(3);
        flight.setDepartureTime("12/12/2020 00:00");
        flight.setDepartureTime("12/12/2020 00:00");
        flight.setQuota(100);
        flight.setPrice(100.0);
        flight.setCurrency("TL");
        flight.setCancelled(false);

        FlightResponseModel frm = new FlightResponseModel(flight);


    }

    @Test
    void post() {

        flight = new Flight();
        flight.setId(2);
        flight.setAirplaneId(2);
        flight.setRouteId(3);
        flight.setDepartureTime("12/12/2020 00:00");
        flight.setDepartureTime("12/12/2020 00:00");
        flight.setQuota(100);
        flight.setPrice(100.0);
        flight.setCurrency("TL");
        flight.setCancelled(false);

        res = new MockHttpServletResponse();
        flightResponseModel = new FlightResponseModel(flight);


        FlightsController fc = new FlightsController();


        assertEquals(successModel.isSuccess, fc.post(flight, res));
    }

    @Test
    void increaseQuota() {
    }

    @Test
    void arrangeFlightResult() {
    }
}