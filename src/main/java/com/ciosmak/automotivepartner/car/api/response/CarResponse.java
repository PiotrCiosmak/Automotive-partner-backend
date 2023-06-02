package com.ciosmak.automotivepartner.car.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CarResponse
{
    private final Long id;
    private final String registrationNumber;
    private final Integer mileage;
    private final Boolean blocked;
}
