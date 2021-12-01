package com.freenow.service.car;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.exception.ConstraintsViolationException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface CarService
{
    CarDO find(Long carId) throws  EntityNotFoundException;

    CarDTO create(CarDTO carDTO) throws EntityNotFoundException,ConstraintsViolationException;

    void delete(Long carId) throws EntityNotFoundException;

    List<CarDTO> findAll();

    void save(CarDO car);
}
