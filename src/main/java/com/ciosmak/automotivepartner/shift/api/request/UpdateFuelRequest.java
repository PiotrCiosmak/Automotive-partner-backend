package com.ciosmak.automotivepartner.shift.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UpdateFuelRequest
{
    private final Long id;
    private final BigDecimal lpg;
    private final BigDecimal petrol;

    @JsonCreator
    public UpdateFuelRequest(Long id, BigDecimal lpg, BigDecimal petrol)
    {
        this.id = id;
        this.lpg = lpg;
        this.petrol = petrol;
    }
}
