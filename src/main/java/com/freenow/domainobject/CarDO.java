package com.freenow.domainobject;

import com.freenow.domainvalue.EngineType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Table(
    name = "car",
    uniqueConstraints = @UniqueConstraint(name = "uc_licenseplate", columnNames = {"licensePlate"})
)
public class CarDO
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "license plate can not be null!")
    private String licensePlate;

    @Column(nullable = false)
    @NotNull(message = "seat count can not be null!")
    private int seatCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Engine type can not be null!")
    private EngineType engineType;

    @Column
    private int rating;

    @Column(nullable = false)
    private Boolean convertible = false;

    @Column(nullable = false)
    private Boolean deleted = false;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCarUpdated = ZonedDateTime.now();

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    private boolean selected;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id",referencedColumnName = "id",nullable = false)
    private ManufacturerDo manufacturer;

    public CarDO()
    {
    }


    public CarDO(String licensePlate, int seatCount, EngineType engineType)
    {
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.engineType = engineType;
        this.deleted = false;
    }


    public Long getId()
    {
        return id;
    }


    public void setId(Long id)
    {
        this.id = id;
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

    public void setRating(int rating)
    {
        this.rating = rating;
    }

    public Boolean getConvertible()
    {
        return convertible;
    }

    public void setConvertible(Boolean convertible)
    {
        this.convertible = convertible;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }


    public ManufacturerDo getManufacturer()
    {
        return manufacturer;
    }


    public boolean isSelected()
    {
        return selected;
    }


    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }


    public void setManufacturer(ManufacturerDo manufacturer)
    {
        this.manufacturer = manufacturer;
    }

}
