package com.finartz.airplanereservations.demo;

import com.finartz.airplanereservations.demo.dto.CompanyDTO;
import com.finartz.airplanereservations.demo.service.CompanyService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class test {

    @Autowired
    CompanyService companyService;

    @Test
    public void testing() {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setName("Pegasus");
        int rsp = companyService.post(companyDTO);
        Assert.assertEquals(1, rsp);
    }
}
