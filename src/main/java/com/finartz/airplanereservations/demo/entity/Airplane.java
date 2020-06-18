package com.finartz.airplanereservations.demo.entity;

import com.finartz.airplanereservations.demo.model.Response;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Airplane extends Response {
    @Id
    @GeneratedValue
    private int id;
    private int companyId;
    private int maxQuota;
    private String name;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
