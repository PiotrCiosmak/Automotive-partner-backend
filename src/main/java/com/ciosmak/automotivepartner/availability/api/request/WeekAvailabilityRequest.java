package com.ciosmak.automotivepartner.availability.api.request;

import com.ciosmak.automotivepartner.availability.support.Type;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
public class WeekAvailabilityRequest
{
    private final ArrayList<Type> types;
    private final Long userId;

    @JsonCreator

    public WeekAvailabilityRequest(ArrayList<Type> types, LocalDate date, Long userId)
    {
        this.types = types;
        this.userId = userId;
    }
}
