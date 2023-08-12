package com.ciosmak.automotivepartner.statistic.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class StatisticsResponse
{
    private final Long id;
    private final LocalDate date;
    private final Integer mileage;
    private final BigDecimal lpg;
    private final BigDecimal petrol;
    private final Long userId;
}
