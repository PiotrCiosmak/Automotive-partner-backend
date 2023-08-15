package com.ciosmak.automotivepartner.car.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class CarRequest
{
    private final String registrationNumber;
    private final Integer mileage;
    private final Boolean isBlocked;

    @JsonCreator
    public CarRequest(String registrationNumber, Integer mileage, Boolean isBlocked)
    {
        this.registrationNumber = registrationNumber;
        this.mileage = mileage;
        this.isBlocked = isBlocked;
    }
}