package com.freenow.service.driver;

import com.freenow.controller.mapper.DriverMapper;
import com.freenow.dataaccessobject.DriverRepository;
import com.freenow.datatransferobject.DriverDTO;
import com.freenow.datatransferobject.SearchQueryDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.DriverDO;
import com.freenow.domainvalue.GeoCoordinate;
import com.freenow.domainvalue.OnlineStatus;
import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import com.freenow.exception.InvalidStatusException;
import com.freenow.service.car.CarService;
import com.freenow.util.DriverSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService
{

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private final DriverRepository driverRepository;
    private CarService carService;

    public DefaultDriverService(final DriverRepository driverRepository,final  CarService carService)
    {

        this.driverRepository = driverRepository;
        this.carService=carService;
    }


    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public DriverDO find(Long driverId) throws EntityNotFoundException
    {
        return findDriverChecked(driverId);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException
    {
        DriverDO driver;
        try
        {
            driver = driverRepository.save(driverDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a driver: {}", driverDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus)
    {
        return driverRepository.findByOnlineStatus(onlineStatus);
    }


    @Override
    @Transactional
    public void selectCar(long driverId, long carId) throws EntityNotFoundException, InvalidStatusException, CarAlreadyInUseException
    {

        DriverDO driverDO=findDriverChecked(driverId);
        if (driverDO.getOnlineStatus().equals(OnlineStatus.OFFLINE))
        {
            throw new InvalidStatusException("Driver with OFFLINE status can not select car");
        }

        CarDO car= carService.find(carId);
        if (car.isSelected())
        {
            throw new CarAlreadyInUseException("Car already selected by another driver");
        }
        car.setSelected(true);
        carService.save(car);

        deSelectCurrentCar(driverDO);

        driverDO.setCarDO(car);
        driverRepository.save(driverDO);
    }


    @Override public void deselectCar(long driverId) throws EntityNotFoundException
    {
        DriverDO driverDO=findDriverChecked(driverId);
        deSelectCurrentCar(driverDO);
        driverDO.setCarDO(null);
        driverRepository.save(driverDO);
    }


    @Override public List<DriverDTO> searchDriversByCriteria(SearchQueryDTO searchQueryDTO)
    {
        List<DriverDO> driverDO=driverRepository.findAll(new DriverSpecification(searchQueryDTO));
        return DriverMapper.makeDriverDTOList(driverDO);
    }


    private void deSelectCurrentCar(DriverDO driverDO)
    {
        Optional<CarDO> currentCar=Optional.ofNullable(driverDO.getCarDO());
        currentCar.ifPresent((currentcar)->{
            currentcar.setSelected(false);
            carService.save(currentcar);
        });
    }


    private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException
    {
        return driverRepository.findById(driverId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverId));
    }

}
