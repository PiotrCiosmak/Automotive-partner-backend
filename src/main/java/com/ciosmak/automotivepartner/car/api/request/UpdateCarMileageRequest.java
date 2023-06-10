package com.ciosmak.automotivepartner.car.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class UpdateCarMileageRequest
{
    private final Integer mileage;


    @JsonCreator
    public UpdateCarMileageRequest(Integer mileage)
    {
        this.mileage = mileage;
    }
}
