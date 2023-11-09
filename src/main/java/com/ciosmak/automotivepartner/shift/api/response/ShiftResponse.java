package com.ciosmak.automotivepartner.shift.api.response;

import com.ciosmak.automotivepartner.shift.support.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class ShiftResponse
{
    private final Long id;
    private final LocalDate date;
    private final Type type;
    private final Integer startMileage;
    private final BigDecimal lpg;
    private final BigDecimal petrol;
    private final Integer endMileage;
    private final Boolean isStarted;
    private final Boolean isDone;
    private final Boolean isCarAvailable;
    private final Long carId;
    private final Long userId;
}
