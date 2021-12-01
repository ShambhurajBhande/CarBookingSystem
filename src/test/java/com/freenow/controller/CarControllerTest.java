package com.freenow.controller;

import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.domainobject.ManufacturerDo;
import com.freenow.domainvalue.EngineType;
import com.freenow.exception.EntityNotFoundException;
import com.freenow.service.car.CarService;
import com.freenow.util.TestUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CarController.class)
public class CarControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new CarController(carService)).build();
    }


    @Test
    public void givenCar_whenCreatingCar_ReturnNewCar() throws Exception {
        CarDTO carDto = TestUtil.getCarDTO();
        Mockito.when(carService.create(carDto)).thenReturn(carDto);
        RequestBuilder builder = MockMvcRequestBuilders.post("/v1/cars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.toJsonString(carDto));
        MvcResult result =this.mockMvc.perform(builder)
            .andExpect(status().isCreated()).andReturn();
        Assertions.assertEquals(201,result.getResponse().getStatus());
    }


    @Test
    public void givenCar_withOutLicensePlate_throwException() throws Exception {
        CarDTO.CarDTOBuilder carDTOBuilder=new CarDTO.CarDTOBuilder();
        carDTOBuilder.setLicensePlate(null);
        CarDTO carDto = carDTOBuilder.createCarDTO();
        RequestBuilder builder = MockMvcRequestBuilders.post("/v1/cars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.toJsonString(carDto));
        this.mockMvc.perform(builder)
            .andExpect(status().isBadRequest());

    }

    @Test
    public void givenCar_RatingNotInRange_throwException() throws Exception {
        CarDTO.CarDTOBuilder carDTOBuilder=new CarDTO.CarDTOBuilder();
        carDTOBuilder.setRating(-1);
        CarDTO carDto = carDTOBuilder.createCarDTO();
        RequestBuilder builder = MockMvcRequestBuilders.post("/v1/cars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.toJsonString(carDto));
        this.mockMvc.perform(builder)
            .andExpect(status().isBadRequest());

    }

    @Test
    public void givenCar_InvalidSeatCount_throwException() throws Exception {
        CarDTO.CarDTOBuilder carDTOBuilder=new CarDTO.CarDTOBuilder();
        carDTOBuilder.setSeatCount(0);
        CarDTO carDto = carDTOBuilder.createCarDTO();
        RequestBuilder builder = MockMvcRequestBuilders.post("/v1/cars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.toJsonString(carDto));
        this.mockMvc.perform(builder)
            .andExpect(status().isBadRequest());

    }

    @Test
    public void givenCar_withOutEngineType_throwException() throws Exception {
        CarDTO.CarDTOBuilder carDTOBuilder=new CarDTO.CarDTOBuilder();
        carDTOBuilder.setEngineType(null);
        CarDTO carDto = carDTOBuilder.createCarDTO();
        RequestBuilder builder = MockMvcRequestBuilders.post("/v1/cars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.toJsonString(carDto));
        this.mockMvc.perform(builder)
            .andExpect(status().isBadRequest());

    }

    @Test
    public void givenCarId_ReturnCar() throws Exception {
        CarDTO carDto=TestUtil.getCarDTO();
        CarDO carDO=TestUtil.getCarDO();
        Mockito.when(carService.find(1L)).thenReturn(carDO);
        RequestBuilder builder = MockMvcRequestBuilders.get("/v1/cars/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.toJsonString(carDto));
        MvcResult result =this.mockMvc.perform(builder)
            .andExpect(status().isOk()).andReturn();
        CarDTO carDTO=TestUtil.jsonToObject(result.getResponse().getContentAsString());
        Assertions.assertEquals(200,result.getResponse().getStatus());
        Assertions.assertEquals("12345",carDTO.getLicensePlate());

    }

    @Test
    public void givenCarId_deleteCar() throws Exception
    {
        RequestBuilder builder = MockMvcRequestBuilders.delete("/v1/cars/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(ArgumentMatchers.anyString());
        this.mockMvc.perform(builder)
            .andExpect(status().isOk());

    }

    @Test
    public void findAllCars() throws Exception {
        List<CarDTO> carDtos = new ArrayList<>();
        CarDTO car1 = TestUtil.getCarDTO();
        carDtos.add(car1);
        CarDTO car2 = TestUtil.getCarDTO();
        carDtos.add(car2);

       Mockito.when(carService.findAll()).thenReturn(carDtos);

        RequestBuilder builder = MockMvcRequestBuilders.get("/v1/cars/")
            .contentType(MediaType.APPLICATION_JSON);
       MvcResult result= this.mockMvc.perform(builder)
            .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        List<CarDTO> cars= TestUtil.toListOfObjects(content);

        Assert.assertEquals("12345",cars.get(0).getLicensePlate());
    }

}
