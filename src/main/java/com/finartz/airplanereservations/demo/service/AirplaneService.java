package com.finartz.airplanereservations.demo.service;

import com.finartz.airplanereservations.demo.dao.AirplaneRepo;
import com.finartz.airplanereservations.demo.dao.CompanyRepo;
import com.finartz.airplanereservations.demo.dto.AirplaneDTO;
import com.finartz.airplanereservations.demo.dto.CompanyDTO;
import com.finartz.airplanereservations.demo.entity.Airplane;
import com.finartz.airplanereservations.demo.entity.Company;
import com.finartz.airplanereservations.demo.model.ErrorModel;
import com.finartz.airplanereservations.demo.model.Response;
import com.finartz.airplanereservations.demo.model.SuccessModel;
import com.finartz.airplanereservations.demo.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Optional;

@Service
public class AirplaneService {

    @Autowired
    AirplaneRepo airplaneRepo;

    @Autowired
    CompanyRepo companyRepo;

    public Response get(int id) {
        Optional<Airplane> optionalAirplane = airplaneRepo.findById(id);
        if (optionalAirplane.isPresent()) {
            Optional<Company> companyOptional = companyRepo.findById(optionalAirplane.get().getCompanyId());
            Airplane airplane = optionalAirplane.get();
            if (companyOptional.isPresent()) {
                Company relatedCompany = companyOptional.get();
                AirplaneDTO airplaneDTO = new AirplaneDTO();
                try {
                    airplaneDTO = new Mapper<>(airplane, airplaneDTO).convertToDTO();
                    airplaneDTO.setCompanyName(relatedCompany.getName());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return airplaneDTO;
            } else {
                return new CompanyDTO();
            }
        } else {
            return null;
        }
    }

    public int post(AirplaneDTO airplaneDTO) {
        Optional<Company> optionalCompany = companyRepo.findById(airplaneDTO.getCompanyId());
        if (optionalCompany.isPresent()) {
            Airplane airplane = new Airplane();
            airplane.setCompanyId(airplaneDTO.getCompanyId());
            try {
                airplane = new Mapper<>(airplane, airplaneDTO).convertToEntity();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int airplaneId = airplaneRepo.save(airplane).getId();
            return airplaneId;
        } else {
            return 0;
        }
    }


}
