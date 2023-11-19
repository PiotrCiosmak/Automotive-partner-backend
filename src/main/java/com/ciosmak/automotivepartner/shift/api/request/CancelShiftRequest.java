package com.ciosmak.automotivepartner.shift.api.request;

import com.ciosmak.automotivepartner.shift.support.Type;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CancelShiftRequest
{
    private final Long id;
    private final LocalDate date;
    private final Type type;

    @JsonCreator
    public CancelShiftRequest(Long id, LocalDate date, Type type)
    {
        this.id = id;
        this.date = date;
        this.type = type;
    }
}
