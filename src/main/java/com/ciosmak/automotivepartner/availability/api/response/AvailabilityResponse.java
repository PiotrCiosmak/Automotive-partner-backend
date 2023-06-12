package com.ciosmak.automotivepartner.availability.api.response;

import com.ciosmak.automotivepartner.availability.support.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class AvailabilityResponse
{
    private final Long id;
    private final Type type;
    private final LocalDate date;
    private final Long userId;
}
