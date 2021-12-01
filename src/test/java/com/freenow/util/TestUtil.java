package com.freenow.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.ManufacturerDo;
import com.freenow.domainvalue.EngineType;

import java.io.IOException;
import java.util.List;

public class TestUtil
{
    public static CarDO getCarDO()
    {
        ManufacturerDo manufacturer = new ManufacturerDo("name");
        CarDO carDO=new CarDO("12345",2, EngineType.DIESEL);
        carDO.setId(1L);
        return carDO;
    }


    public static CarDTO getCarDTO()
    {
        CarDTO.CarDTOBuilder carDTOBuilder=new CarDTO.CarDTOBuilder();
        carDTOBuilder.setId(1L);
        carDTOBuilder.setLicensePlate("12345");
        carDTOBuilder.setConvertible(true);
        carDTOBuilder.setEngineType(EngineType.DIESEL);
        carDTOBuilder.setSeatCount(2);
        carDTOBuilder.setManufacturer(1L);
        carDTOBuilder.setRating(2);
        CarDTO carDto = carDTOBuilder.createCarDTO();
        return carDto;
    }

    public static ManufacturerDo getManufacturerDo(){
        return  new ManufacturerDo(1L,"BMW");
    }

    public static String toJsonString(final CarDTO car) {
        try {
            return new ObjectMapper().writeValueAsString(car);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static CarDTO jsonToObject(String car) {
        try {
            return new ObjectMapper().readValue(car, CarDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<CarDTO> toListOfObjects(String cars) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(cars, mapper.getTypeFactory().constructCollectionType(List.class, CarDTO.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
