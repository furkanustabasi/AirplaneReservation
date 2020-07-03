package com.finartz.airplanereservations.demo.utils;


import com.finartz.airplanereservations.demo.entity.IEntity;
import com.finartz.airplanereservations.demo.model.Response;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

import java.text.ParseException;

public class Mapper<E extends IEntity, D extends Response>{

    private E entity;
    private D dto;

    public Mapper(E entity, D dto) {
        this.entity = entity;
        this.dto = dto;
    }


    @Bean
    public static ModelMapper modelMapper() {
        return new ModelMapper();
    }


    public D convertToDTO() throws ParseException {
        return modelMapper().map(entity, getDtoOfInstance());
    }


    public E convertToEntity() throws ParseException {
        return modelMapper().map(dto, getEntityOfInstance());
    }

    @SuppressWarnings("unchecked")
    public Class<D> getDtoOfInstance() {
        return (Class<D>) dto.getClass();
    }

    @SuppressWarnings("unchecked")
    public Class<E> getEntityOfInstance() {
        return (Class<E>) entity.getClass();
    }


 /*   @Override
    public IDTO convertToDTO(Response response) throws ParseException {
        return modelMapper().map(response, IDTO.class);
    }

    @Override
    public Response convertToEntity(IDTO dto) throws ParseException {
        return modelMapper().map(dto, Response.class);
    }*/
}
