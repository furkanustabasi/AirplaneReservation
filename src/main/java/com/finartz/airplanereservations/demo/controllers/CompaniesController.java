package com.finartz.airplanereservations.demo.controllers;

import com.finartz.airplanereservations.demo.dto.CompanyDTO;
import com.finartz.airplanereservations.demo.entity.Company;
import com.finartz.airplanereservations.demo.dao.CompanyRepo;

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
public class CompaniesController {

    @Autowired
    CompanyRepo repo;

    @GetMapping("/companies")
    public Response getById(@RequestParam(value = "id", defaultValue = "0") int id, HttpServletResponse res) {
        Optional<Company> companyOptional = repo.findById(id);
        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();
            CompanyDTO companyDTO = new CompanyDTO();
            try {
                companyDTO = new Mapper<>(company, companyDTO).convertToDTO();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return companyDTO;
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    @PostMapping(path = "/companies")
    public Response post(CompanyDTO companyDTO, HttpServletResponse res) {
        Company company = new Company();
        try {
            company = new Mapper<>(company, companyDTO).convertToEntity();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int companyId = repo.save(company).getId();
        if (companyId > 0) {
            return new SuccessModel(companyDTO.getId(), "Havayolu şirketi başarıyla eklendi.");
        } else {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ErrorModel("Havayolu şirketi eklenirken bir hata oluştu.");
        }

    }

}
