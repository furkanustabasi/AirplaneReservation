package com.finartz.airplanereservations.demo.controllers;

import com.finartz.airplanereservations.demo.dto.RouteDTO;
import com.finartz.airplanereservations.demo.model.ErrorModel;
import com.finartz.airplanereservations.demo.model.Response;
import com.finartz.airplanereservations.demo.model.SuccessModel;
import com.finartz.airplanereservations.demo.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class RoutesController {

    @Autowired
    RouteService routeService;

    @GetMapping("/routes")
    public Response getById(@RequestParam(value = "id", defaultValue = "0") int id, HttpServletResponse res) {
        Response response = routeService.get(id);
        if (response.getClass() != ErrorModel.class) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return response;
    }

    @PostMapping(path = "/routes")
    public Response post(RouteDTO routeDTO, HttpServletResponse res) {

        int routeId = routeService.post(routeDTO);

        if (routeId > 0) {
            return new SuccessModel(routeId, "Rota başarıyla eklendi.");
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ErrorModel("Rota eklenirken bir hata oluştu.");
        }
    }


}
