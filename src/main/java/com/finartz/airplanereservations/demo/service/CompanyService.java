package com.finartz.airplanereservations.demo.service;

import com.finartz.airplanereservations.demo.dao.CompanyRepo;
import com.finartz.airplanereservations.demo.dto.CompanyDTO;
import com.finartz.airplanereservations.demo.entity.Company;
import com.finartz.airplanereservations.demo.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepo companyRepo;

    public CompanyDTO get(int id) {
        Optional<Company> companyOptional = companyRepo.findById(id);
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
            return null;
        }
    }


    public int post(CompanyDTO companyDTO) {
        if (!companyDTO.getName().equals("")) {
            Company company = new Company();
            try {
                company = new Mapper<>(company, companyDTO).convertToEntity();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return companyRepo.save(company).getId();
        } else {
            return 0;
        }
    }
}