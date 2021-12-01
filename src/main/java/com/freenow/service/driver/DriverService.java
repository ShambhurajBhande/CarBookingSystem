package com.freenow.service.driver;

import com.freenow.datatransferobject.DriverDTO;
import com.freenow.datatransferobject.SearchQueryDTO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.exception.InvalidStatusException;

import java.util.List;

public interface DriverService
{

    DriverDO find(Long driverId) throws EntityNotFoundException;

    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

    void delete(Long driverId) throws EntityNotFoundException;

    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;

    List<DriverDO> find(OnlineStatus onlineStatus);

    void selectCar(long driverId, long carId) throws EntityNotFoundException, InvalidStatusException, CarAlreadyInUseException;

    void deselectCar(long driverId) throws EntityNotFoundException;

    List<DriverDTO> searchDriversByCriteria(SearchQueryDTO searchCriteriaDTO);
}
