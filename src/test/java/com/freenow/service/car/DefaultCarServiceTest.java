package com.freenow.service.car;

import com.freenow.dataaccessobject.CarRepository;
import com.freenow.dataaccessobject.ManufacturerRepository;
import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.ManufacturerDo;
import com.freenow.domainvalue.EngineType;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.util.TestUtil;
import org.hamcrest.Matchers;
import org.hibernate.annotations.Any;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.regex.Matcher;

@ExtendWith(MockitoExtension.class)
public class DefaultCarServiceTest
{
    @Mock
    CarRepository carRepository;

    @Mock
    ManufacturerRepository manufacturerRepository;

    @InjectMocks
    DefaultCarService defaultCarServiceMock;

    private CarDO carDO;
    private CarDTO carDTO;
    private ManufacturerDo manufacturerDo;

    @BeforeEach
    public void init(){
        carDO= TestUtil.getCarDO();
        carDTO=TestUtil.getCarDTO();
        manufacturerDo=TestUtil.getManufacturerDo();
        defaultCarServiceMock=new DefaultCarService(carRepository,manufacturerRepository);
    }


    @Test
    public void givenCarId_findCar_notFound(){
        Mockito.when(carRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = Assert.assertThrows(RuntimeException.class, () -> {
            defaultCarServiceMock.find(1L);
        });
        Assert.assertEquals("Could not find entity with id: 1",exception.getMessage() );
    }

    @Test
    public void givenCar_withOutManufacturer_throwException() throws ConstraintsViolationException
    {
        Mockito.when(manufacturerRepository.findById(1L)).thenReturn(Optional.empty());
        Mockito.when(carRepository.save(ArgumentMatchers.any(CarDO.class))).thenReturn(carDO);
        CarDTO carDto=defaultCarServiceMock.create(carDTO);
        Assert.assertEquals("12345",carDto.getLicensePlate());
        Assert.assertEquals(null,carDto.getManufacturer());
    }

    @Test
    public void givenCar_withManufacturer_Create() throws ConstraintsViolationException
    {
        carDO.setManufacturer(manufacturerDo);
        Mockito.when(manufacturerRepository.findById(1L)).thenReturn(Optional.ofNullable(manufacturerDo));
        Mockito.when(carRepository.save(ArgumentMatchers.any(CarDO.class))).thenReturn(carDO);

        CarDTO carDto=defaultCarServiceMock.create(carDTO);
        Assert.assertEquals("12345",carDto.getLicensePlate());
        Assert.assertEquals(1L,carDto.getManufacturer().longValue());
    }

    @Test
    public void givenCar_carAlreadyCreated_throwException() throws ConstraintsViolationException
    {
        carDO.setManufacturer(manufacturerDo);
        Mockito.when(manufacturerRepository.findById(1L)).thenReturn(Optional.ofNullable(manufacturerDo));
        Mockito.when(carRepository.save(ArgumentMatchers.any(CarDO.class))).thenThrow(new DataIntegrityViolationException("Car already registered"));
        Exception exception = Assert.assertThrows(ConstraintsViolationException.class, () -> {
            defaultCarServiceMock.create(carDTO);
        });
        Assert.assertEquals("Car already registered",exception.getMessage() );

    }
}

