package com.ciosmak.automotivepartner.availability.support;

import com.ciosmak.automotivepartner.availability.support.exception.AvailabilityNotFoundException;
import com.ciosmak.automotivepartner.availability.support.exception.AvailabilitySubmittedException;

import java.time.LocalDate;
import java.util.function.Supplier;

public class AvailabilityExceptionSupplier
{
    public static Supplier<AvailabilitySubmittedException> availabilitySubmitted(Long userId, LocalDate date)
    {
        return () -> new AvailabilitySubmittedException(userId, date);
    }

    public static Supplier<AvailabilityNotFoundException> availabilityNotFound(Long userId, LocalDate date)
    {
        return () -> new AvailabilityNotFoundException(userId, date);
    }
}
