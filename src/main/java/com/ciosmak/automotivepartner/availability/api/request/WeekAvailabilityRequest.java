package com.ciosmak.automotivepartner.availability.api.request;

import com.ciosmak.automotivepartner.availability.support.Type;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class WeekAvailabilityRequest
{
    private final ArrayList<Type> types;
    private final Long userId;

    @JsonCreator
    public WeekAvailabilityRequest(ArrayList<Type> types, Long userId)
    {
        this.types = types;
        this.userId = userId;
    }
}
