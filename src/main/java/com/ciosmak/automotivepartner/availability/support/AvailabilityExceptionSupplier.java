package com.ciosmak.automotivepartner.availability.support;

import com.ciosmak.automotivepartner.availability.support.exception.AvailabilityAlreadySubmittedException;
import com.ciosmak.automotivepartner.availability.support.exception.IncorrectDateException;
import com.ciosmak.automotivepartner.availability.support.exception.IncorrectTypeException;

import java.util.function.Supplier;

public class AvailabilityExceptionSupplier
{
    public static Supplier<AvailabilityAlreadySubmittedException> availabilityAlreadySubmitted()
    {
        return AvailabilityAlreadySubmittedException::new;
    }

    public static Supplier<IncorrectDateException> incorrectDate()
    {
        return IncorrectDateException::new;
    }

    public static Supplier<IncorrectTypeException> incorrectType()
    {
        return IncorrectTypeException::new;
    }
}
