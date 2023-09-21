package com.ciosmak.automotivepartner.statistic.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class StatisticsUpdateRequest
{
    private final Integer mileage;
    private final BigDecimal lpg;
    private final BigDecimal petrol;

    @JsonCreator
    public StatisticsUpdateRequest(Integer mileage, BigDecimal lpg, BigDecimal petrol)
    {
        this.mileage = mileage;
        this.lpg = lpg;
        this.petrol = petrol;
    }
}
