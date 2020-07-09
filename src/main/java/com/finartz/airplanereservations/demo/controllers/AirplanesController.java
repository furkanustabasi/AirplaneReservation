package com.finartz.airplanereservations.demo.controllers;

import com.finartz.airplanereservations.demo.dto.AirplaneDTO;
import com.finartz.airplanereservations.demo.model.ErrorModel;
import com.finartz.airplanereservations.demo.model.Response;
import com.finartz.airplanereservations.demo.model.SuccessModel;
import com.finartz.airplanereservations.demo.service.AirplaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
public class AirplanesController {

    @Autowired
    AirplaneService airplaneService;

    @GetMapping("/airplanes")
    public Response getById(@RequestParam(value = "id", defaultValue = "0") int id, HttpServletResponse res) {
        Response response = airplaneService.get(id);
        if (response.getClass() == AirplaneDTO.class) {
            return response;
        } else if (response.getClass() == ErrorModel.class) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return response;
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return response;
        }
    }

    @PostMapping(path = "/airplanes")
    public Response post(AirplaneDTO airplaneDTO, HttpServletResponse res) {

        int airplaneId = airplaneService.post(airplaneDTO);

        if (airplaneId > 0) {
            return new SuccessModel(airplaneId, "Uçak başarıyla eklendi.");
        } else if (airplaneId == -1) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ErrorModel("Uçak eklenirken bir hata oluştu.");
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ErrorModel("Uçak eklenirken bir hata oluştu.");
        }
    }

}
