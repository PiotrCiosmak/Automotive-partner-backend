package com.ciosmak.automotivepartner.car.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class UpdateCarMileageRequest
{
    private final Integer mileage;
    private final Long id;

    @JsonCreator
    public UpdateCarMileageRequest(Integer mileage, Long id)
    {
        this.mileage = mileage;
        this.id = id;
    }
}
