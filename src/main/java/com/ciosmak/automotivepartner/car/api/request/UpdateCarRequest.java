package com.ciosmak.automotivepartner.car.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;

public class UpdateCarRequest extends CarRequest
{
    private final Long id;

    @JsonCreator
    public UpdateCarRequest(String registrationNumber, Integer mileage, Boolean blocked, Long id)
    {
        super(registrationNumber, mileage, blocked);
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
}
