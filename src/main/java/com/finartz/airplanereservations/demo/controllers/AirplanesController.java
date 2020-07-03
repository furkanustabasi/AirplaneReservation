package com.finartz.airplanereservations.demo.controllers;

import com.finartz.airplanereservations.demo.dao.AirplaneRepo;
import com.finartz.airplanereservations.demo.dao.CompanyRepo;
import com.finartz.airplanereservations.demo.dto.AirplaneDTO;
import com.finartz.airplanereservations.demo.entity.Airplane;
import com.finartz.airplanereservations.demo.entity.Company;
import com.finartz.airplanereservations.demo.model.ErrorModel;
import com.finartz.airplanereservations.demo.model.Response;
import com.finartz.airplanereservations.demo.model.SuccessModel;
import com.finartz.airplanereservations.demo.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Optional;


@RestController
public class AirplanesController {
    @Autowired
    AirplaneRepo airplaneRepo;

    @Autowired
    CompanyRepo companyRepo;

    @GetMapping("/airplanes")
    public Response getById(@RequestParam(value = "id", defaultValue = "0") int id, HttpServletResponse res) {
        Optional<Airplane> optionalAirplane = airplaneRepo.findById(id);
        if (optionalAirplane.isPresent()) {
            Optional<Company> companyOptional = companyRepo.findById(optionalAirplane.get().getCompanyId());
            Airplane airplane = optionalAirplane.get();
            if (companyOptional.isPresent()) {
                Company relatedCompany = companyOptional.get();
                return new AirplaneDTO(airplane, relatedCompany);
            } else {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return new ErrorModel("Uçak şirketi bulunamadı");
            }
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ErrorModel("Uçak bulunamadı");
        }
    }

    @PostMapping(path = "/airplanes")
    public Response post(AirplaneDTO airplaneDTO, HttpServletResponse res) {
        Airplane airplane = new Airplane();
        try {
            airplane = new Mapper<>(airplane, airplaneDTO).convertToEntity();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int airplaneId = airplaneRepo.save(airplane).getId();
        if (airplaneId > 0) {
            return new SuccessModel(airplaneId, "Uçak başarıyla eklendi.");
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ErrorModel("Uçak eklenirken bir hata oluştu.");
        }
    }

}
