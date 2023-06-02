package com.ciosmak.automotivepartner.car.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class CarRequest
{
    private final String registrationNumber;
    private final Integer mileage;
    private final Boolean blocked;

    //TODO lombok konstruktor chyba też

    @JsonCreator
    public CarRequest(String registrationNumber, Integer mileage, Boolean blocked)
    {
        this.registrationNumber = registrationNumber;
        this.mileage = mileage;
        this.blocked = blocked;
    }
}