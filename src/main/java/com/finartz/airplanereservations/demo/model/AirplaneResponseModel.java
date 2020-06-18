package com.finartz.airplanereservations.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finartz.airplanereservations.demo.entity.Airplane;
import com.finartz.airplanereservations.demo.entity.Company;

public class AirplaneResponseModel extends Response {

    private int id;
    @JsonIgnoreProperties("isSuccess")
    private Company company;
    private int maxQuota;
    private String name;

    public AirplaneResponseModel(Airplane airplane, Company company) {
        this.id = airplane.getId();
        this.maxQuota = airplane.getMaxQuota();
        this.company = company;
        this.name = airplane.getName();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getMaxQuota() {
        return maxQuota;
    }

    public void setMaxQuota(int maxQuota) {
        this.maxQuota = maxQuota;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
