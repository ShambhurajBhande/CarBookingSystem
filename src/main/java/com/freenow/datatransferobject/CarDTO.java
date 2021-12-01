package com.freenow.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.freenow.domainvalue.EngineType;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Component
public class CarDTO
{
    private Long id;

    @NotNull(message = "license plate can not be null!")
    private String licensePlate;

    @NotNull(message = "seat count can not be null!")
    @Min(1)
    private int seatCount;

    @NotNull(message = "Engine type can not be null!")
    private EngineType engineType;

    @Min(1)
    @Max(5)
    private int rating;

    private Boolean convertible;

    @NotNull(message = "Manufacturer can not be null!")
    private Long manufacturer;


    public CarDTO()
    {
    }


    public CarDTO(CarDTOBuilder carDTOBuilder)
    {
        this.id = carDTOBuilder.id;
        this.licensePlate = carDTOBuilder.licensePlate;
        this.seatCount = carDTOBuilder.seatCount;
        this.engineType = carDTOBuilder.engineType;
        this.convertible=carDTOBuilder.convertible;
        this.rating=carDTOBuilder.rating;
        this.manufacturer= carDTOBuilder.manufacturer;
    }



    public static CarDTOBuilder newBuilder()
    {
        return new CarDTOBuilder();
    }


    public Long getId()
    {
        return id;
    }


    public String getLicensePlate()
    {
        return licensePlate;
    }


    public int getSeatCount()
    {
        return seatCount;
    }


    public EngineType getEngineType()
    {
        return engineType;
    }


    public int getRating()
    {
        return rating;
    }


    public Boolean getConvertible()
    {
        return convertible;
    }


    public Long getManufacturer()
    {
        return manufacturer;
    }


    public static class CarDTOBuilder
    {
        private Long id;
        private String licensePlate;
        private int seatCount;
        private EngineType engineType;
        private int rating;
        private Boolean convertible;
        private Long manufacturer;


        public CarDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public CarDTOBuilder setLicensePlate(String licensePlate)
        {
            this.licensePlate = licensePlate;
            return this;
        }


        public CarDTOBuilder setSeatCount(int seatCount)
        {
            this.seatCount = seatCount;
            return this;
        }


        public CarDTOBuilder setEngineType(EngineType engineType)
        {
            this.engineType = engineType;
            return this;
        }


        public CarDTOBuilder setRating(int rating)
        {
            this.rating = rating;
            return this;
        }


        public CarDTOBuilder setConvertible(Boolean convertible)
        {
            this.convertible = convertible;
            return this;
        }


        public CarDTOBuilder setManufacturer(Long manufacturer)
        {
            this.manufacturer = manufacturer;
            return this;
        }


        public CarDTO createCarDTO()
        {
            return new CarDTO(this);
        }

    }
}
