package com.ciosmak.automotivepartner.availability.support;

import com.ciosmak.automotivepartner.availability.support.exception.AvailabilityAlreadySubmittedException;
import com.ciosmak.automotivepartner.availability.support.exception.IncorrectAvailabilityDateException;

import java.time.LocalDate;
import java.util.function.Supplier;

public class AvailabilityExceptionSupplier
{
    public static Supplier<AvailabilityAlreadySubmittedException> availabilityAlreadySubmitted(String firstName, String lastName)
    {
        return () -> new AvailabilityAlreadySubmittedException(firstName, lastName);
    }

    public static Supplier<IncorrectAvailabilityDateException> incorrectAvailabilityDate(String firstName, String lastName, LocalDate date)
    {
        return () -> new IncorrectAvailabilityDateException(firstName, lastName, date);
    }
}
