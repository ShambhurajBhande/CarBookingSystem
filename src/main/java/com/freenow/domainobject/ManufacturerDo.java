package com.freenow.domainobject;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(
    name = "manufacturer",
    uniqueConstraints = @UniqueConstraint(name = "uc_name", columnNames = {"name"})
)
public class ManufacturerDo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Name can not be null")
    private String name;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated;


    public ManufacturerDo()
    {
    }


    public ManufacturerDo(String name)
    {
        this.name = name;
    }


    public ManufacturerDo(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }


    public long getId()
    {
        return id;
    }


    public String getName()
    {
        return name;
    }


    public ZonedDateTime getDateCreated()
    {
        return dateCreated;
    }


    @Override public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ManufacturerDo that = (ManufacturerDo) o;
        return id == that.id && name.equals(that.name) && dateCreated.equals(that.dateCreated);
    }


    @Override public int hashCode()
    {
        return Objects.hash(id, name, dateCreated);
    }
}
