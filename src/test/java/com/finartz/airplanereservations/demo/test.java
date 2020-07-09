package com.finartz.airplanereservations.demo;

import com.finartz.airplanereservations.demo.dao.CompanyRepo;
import com.finartz.airplanereservations.demo.dto.CompanyDTO;
import com.finartz.airplanereservations.demo.service.CompanyService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



public class test {

    @Mock
    CompanyRepo companyRepo;

    @InjectMocks
    CompanyService companyService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testing() {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setName("Pegasus");
        int testing = companyService.post(companyDTO);
        Assert.assertEquals(1, testing);
    }
}
