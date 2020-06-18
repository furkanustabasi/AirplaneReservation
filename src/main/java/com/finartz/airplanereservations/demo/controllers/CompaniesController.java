package com.finartz.airplanereservations.demo.controllers;

import com.finartz.airplanereservations.demo.entity.Company;
import com.finartz.airplanereservations.demo.dao.CompanyRepo;
import com.finartz.airplanereservations.demo.model.ErrorModel;
import com.finartz.airplanereservations.demo.model.Response;
import com.finartz.airplanereservations.demo.model.SuccessModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
public class CompaniesController {

    @Autowired
    CompanyRepo repo;

    @GetMapping("/companies")
    public Response getById(@RequestParam(value = "id", defaultValue = "0") int id, HttpServletResponse res){
        Optional<Company> company = repo.findById(id);
        if (company.isPresent()) {
            return company.get();
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ErrorModel("Havayolu şirketi bulunamadı");
        }
    }

    @PostMapping(path = "/companies")
    public Response post(Company company, HttpServletResponse res){
        int companyId = repo.save(company).getId();
        if (companyId > 0) {
            return new SuccessModel(companyId, "Havayolu şirketi başarıyla eklendi.");
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ErrorModel("Havayolu şirketi eklenirken bir hata oluştu.");
        }

    }

}
