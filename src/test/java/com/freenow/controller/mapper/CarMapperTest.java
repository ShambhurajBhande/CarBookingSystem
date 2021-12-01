package com.freenow.controller.mapper;

import com.freenow.datatransferobject.CarDTO;
import com.freenow.domainobject.CarDO;
import com.freenow.util.TestUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CarMapperTest
{

    @Test
    public void givenCarDto_makeCarDO(){
        CarDTO carDTO= TestUtil.getCarDTO();
        CarDO carDO=CarMapper.makeCarDO(carDTO);
        Assert.assertEquals(carDTO.getLicensePlate(),carDO.getLicensePlate());
    }

    @Test
    public void givenNullCarDto_NullCarDO() {
        CarDO carDo = CarMapper.makeCarDO(null);
        Assert.assertEquals(null, carDo);
    }

    @Test
    public void givenCarDo_makeCarDTO(){
        CarDO carDO= TestUtil.getCarDO();
        carDO.setManufacturer(TestUtil.getManufacturerDo());
        CarDTO carDTO=CarMapper.makeCarDTO(carDO);
        Assert.assertEquals(carDO.getLicensePlate(),carDTO.getLicensePlate());
    }

    @Test
    public void givenNullCarDo_NullCarDTO() {
        CarDTO carDTO = CarMapper.makeCarDTO(null);
        Assert.assertEquals(null, carDTO);
    }

}
