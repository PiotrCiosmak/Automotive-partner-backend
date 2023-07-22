package com.ciosmak.automotivepartner.availability.support.exception;

import java.time.LocalDate;

public class AvailabilityNotFoundException extends RuntimeException
{
    public AvailabilityNotFoundException(Long userId, LocalDate date)
    {
        super(String.format("Kierowca o id %d nie złożył dyspozycyjność na %s", userId, date));
    }
}
