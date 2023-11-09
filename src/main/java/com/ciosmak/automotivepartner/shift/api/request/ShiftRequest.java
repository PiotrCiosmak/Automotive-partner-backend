package com.ciosmak.automotivepartner.shift.api.request;

import com.ciosmak.automotivepartner.shift.support.Type;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class ShiftRequest
{
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

    @JsonCreator
    public ShiftRequest(LocalDate date, Type type, Integer startMileage, BigDecimal lpg, BigDecimal petrol, Integer endMileage, Boolean isStarted, Boolean isDone, Boolean isCarAvailable, Long carId, Long userId)
    {
        this.date = date;
        this.type = type;
        this.startMileage = startMileage;
        this.lpg = lpg;
        this.petrol = petrol;
        this.endMileage = endMileage;
        this.isStarted = isStarted;
        this.isDone = isDone;
        this.isCarAvailable = isCarAvailable;
        this.carId = carId;
        this.userId = userId;
    }
}
