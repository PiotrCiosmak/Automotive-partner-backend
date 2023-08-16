package com.ciosmak.automotivepartner.shift.api.request;

import com.ciosmak.automotivepartner.availability.support.Type;
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
    private final Boolean isDone;
    private final Long carId;
    private final Long userId;

    @JsonCreator
    public ShiftRequest(LocalDate date, Type type, Integer startMileage, BigDecimal lpg, BigDecimal petrol, Integer endMileage, Boolean isDone, Long carId, Long userId)
    {
        this.date = date;
        this.type = type;
        this.startMileage = startMileage;
        this.lpg = lpg;
        this.petrol = petrol;
        this.endMileage = endMileage;
        this.isDone = isDone;
        this.carId = carId;
        this.userId = userId;
    }
}
