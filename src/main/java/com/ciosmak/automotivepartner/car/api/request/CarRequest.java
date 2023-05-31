package com.ciosmak.automotivepartner.car.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CarRequest
{
    private final String registrationNumber;
    private final Integer mileage;
    private final Boolean blocked;

    //TODO geter lombok, konstruktor chyba też

    @JsonCreator
    public CarRequest(String registrationNumber, Integer mileage, Boolean blocked)
    {
        this.registrationNumber = registrationNumber;
        this.mileage = mileage;
        this.blocked = blocked;
    }

    public String getRegistrationNumber()
    {
        return registrationNumber;
    }

    public Integer getMileage()
    {
        return mileage;
    }

    public Boolean getBlocked()
    {
        return blocked;
    }
}
