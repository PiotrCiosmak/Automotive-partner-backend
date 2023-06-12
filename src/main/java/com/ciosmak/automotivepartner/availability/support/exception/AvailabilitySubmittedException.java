package com.ciosmak.automotivepartner.availability.support.exception;

import java.time.LocalDate;

public class AvailabilitySubmittedException extends RuntimeException
{
    public AvailabilitySubmittedException(Long userId, LocalDate date)
    {
        super(String.format("Kierowca o id %d złożył już dyspozycyjność na %s", userId, date));
    }
}
