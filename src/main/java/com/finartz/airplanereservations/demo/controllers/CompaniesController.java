package com.finartz.airplanereservations.demo.controllers;

import com.finartz.airplanereservations.demo.dto.CompanyDTO;
import com.finartz.airplanereservations.demo.model.ErrorModel;
import com.finartz.airplanereservations.demo.model.Response;
import com.finartz.airplanereservations.demo.model.SuccessModel;
import com.finartz.airplanereservations.demo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class CompaniesController {

    @Autowired
    CompanyService companyService;

    @GetMapping("/companies")
    public Response getById(@RequestParam(value = "id", defaultValue = "0") int id, HttpServletResponse res) {

        Response response = companyService.get(id);
        if (response.getClass() != ErrorModel.class) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return response;
    }

    @PostMapping(path = "/companies")
    public Response post(CompanyDTO companyDTO, HttpServletResponse res) {

        int companyId = companyService.post(companyDTO);

        if (companyId > 0) {
            return new SuccessModel(companyId, "Havayolu şirketi başarıyla eklendi.");
        } else if (companyId == 0) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ErrorModel("Havayolu şirketi eklenirken bir hata oluştu.");
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ErrorModel("Havayolu şirketi eklenirken bir hata oluştu.");
        }
    }


}
