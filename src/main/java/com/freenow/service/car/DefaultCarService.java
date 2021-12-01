package com.freenow.service.car;

import com.freenow.controller.mapper.CarMapper;
import com.freenow.dataaccessobject.CarRepository;
import com.freenow.dataaccessobject.ManufacturerRepository;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.ManufacturerDo;
import com.freenow.exception.ConstraintsViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultCarService implements CarService
{
    private static final Logger LOG = LoggerFactory.getLogger(DefaultCarService.class);
    private CarRepository carRepository;
    private ManufacturerRepository manufacturerRepository;


    @Autowired
    public DefaultCarService(CarRepository carRepository, ManufacturerRepository manufacturerRepository)
    {
        this.carRepository = carRepository;
        this.manufacturerRepository = manufacturerRepository;
    }


    @Override
    public CarDO find(Long carId) throws EntityNotFoundException
    {
        return findCar(carId);
    }


    @Override
    public CarDTO create(CarDTO carDTO) throws EntityNotFoundException,ConstraintsViolationException
    {
        Optional<ManufacturerDo> manufacturerDo = getManufacturer(carDTO);
        CarDO car= CarMapper.makeCarDO(carDTO);
        if (manufacturerDo.isPresent())
        {
            car.setManufacturer(manufacturerDo.get());
        }
        try
        {
            car = carRepository.save(car);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a driver: {}", car, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return CarMapper.makeCarDTO(car);
    }

    @Override
    @Transactional
    public void delete(Long carId) throws EntityNotFoundException
    {
        CarDO carDO=findCar(carId);
        carDO.setDeleted(true);
    }


    @Override
    public List<CarDTO> findAll()
    {
        List<CarDO> cars=new ArrayList<>();
        carRepository.findAll().forEach((car)-> cars.add(car));
        return CarMapper.makeCarDTOList(cars);
    }


    @Override
    public void save(CarDO car)
    {
        carRepository.save(car);
    }


    private Optional<ManufacturerDo> getManufacturer(CarDTO carDTO) throws EntityNotFoundException
    {
        return Optional.of(manufacturerRepository.findById(carDTO.getManufacturer()))
            .orElseThrow(() -> new EntityNotFoundException("Manufacturer not found with id: "+ carDTO.getManufacturer()));
    }


    private CarDO findCar(Long carId) throws EntityNotFoundException
    {
        return carRepository.findById(carId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + carId));
    }

}
