package com.ciosmak.automotivepartner.shift.api.request;

import com.ciosmak.automotivepartner.shift.support.Type;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GenerateShiftRequest
{
    private final LocalDate date;
    private final Type type;
    private final Long carId;
    private final Long userId;

    @JsonCreator
    public GenerateShiftRequest(LocalDate date, Type type, Long carId, Long userId)
    {
        this.date = date;
        this.type = type;
        this.carId = carId;
        this.userId = userId;
    }
}
