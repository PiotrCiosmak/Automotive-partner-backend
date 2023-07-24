package com.ciosmak.automotivepartner.statistic.api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class StatisticsRequest
{
    private final LocalDate monthAndYear;
    private final Integer mileage;
    private final BigDecimal lpg;
    private final BigDecimal petrol;
    private final Long userId;

    @JsonCreator
    public StatisticsRequest(LocalDate monthAndYear, Integer mileage, BigDecimal lpg, BigDecimal petrol, Long userId)
    {
        this.monthAndYear = monthAndYear;
        this.mileage = mileage;
        this.lpg = lpg;
        this.petrol = petrol;
        this.userId = userId;
    }
}
