package com.freenow.controller.mapper;

import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.ManufacturerDo;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CarMapper
{
    public static CarDO makeCarDO(CarDTO carDTO)
    {
       return Optional.ofNullable(carDTO).map( c -> {
            CarDO carDO = new CarDO(carDTO.getLicensePlate(), carDTO.getSeatCount(), carDTO.getEngineType());
            carDO.setConvertible(carDTO.getConvertible());
            carDO.setRating(carDTO.getRating());
           return carDO;
       }).orElse(null);
    }

    public static CarDTO makeCarDTO(CarDO carDO)
    {
        return Optional.ofNullable(carDO).map( c -> {
            Long manufacturer_id= Optional.ofNullable(carDO.getManufacturer()).map(ManufacturerDo::getId).orElse(null);

            CarDTO.CarDTOBuilder carDTOBuilder = CarDTO.newBuilder()
                .setId(carDO.getId())
                .setLicensePlate(carDO.getLicensePlate())
                .setSeatCount(carDO.getSeatCount())
                .setRating(carDO.getRating())
                .setEngineType(carDO.getEngineType())
                .setConvertible(carDO.getConvertible())
                .setManufacturer(manufacturer_id);

            return carDTOBuilder.createCarDTO();
        }).orElse(null);
    }


    public static List<CarDTO> makeCarDTOList(Collection<CarDO> cars)
    {
        return cars.stream()
            .map(CarMapper::makeCarDTO)
            .collect(Collectors.toList());
    }

}
