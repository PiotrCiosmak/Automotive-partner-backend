package com.ciosmak.automotivepartner.availability.api.request;

import com.ciosmak.automotivepartner.availability.support.Type;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AvailabilityRequest
{
    private final Type type;
    private final LocalDate date;
    private final Long userId;

    @JsonCreator
    public AvailabilityRequest(Type type, LocalDate date, Long userId)
    {
        this.type = type;
        this.date = date;
        this.userId = userId;
    }
}
