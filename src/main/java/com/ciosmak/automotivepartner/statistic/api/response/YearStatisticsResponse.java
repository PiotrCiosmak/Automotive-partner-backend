package com.ciosmak.automotivepartner.statistic.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class YearStatisticsResponse
{
    private final Integer mileage;
    private final BigDecimal lpg;
    private final BigDecimal petrol;
}
