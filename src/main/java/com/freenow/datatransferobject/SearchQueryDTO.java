package com.freenow.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.freenow.domainvalue.OnlineStatus;
import org.springframework.stereotype.Component;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Component
public class SearchQueryDTO
{

    private String licensePlate;
    private int rating;
    private OnlineStatus onlineStatus;
    private String username;

    public SearchQueryDTO()
    {
    }


    public String getLicensePlate()
    {
        return licensePlate;
    }


    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
    }


    public int getRating()
    {
        return rating;
    }


    public void setRating(int rating)
    {
        this.rating = rating;
    }


    public OnlineStatus getOnlineStatus()
    {
        return onlineStatus;
    }


    public void setOnlineStatus(OnlineStatus onlineStatus)
    {
        this.onlineStatus = onlineStatus;
    }


    public String getUsername()
    {
        return username;
    }


    public void setUsername(String username)
    {
        this.username = username;
    }

}
