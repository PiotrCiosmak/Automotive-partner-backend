package com.ciosmak.automotivepartner.availability.support.exception;

import java.time.LocalDate;

public class IncorrectAvailabilityDateException extends RuntimeException
{
    public Object[] properties;

    public IncorrectAvailabilityDateException(String firstName, String lastName, LocalDate date)
    {
        properties = new Object[]{firstName, lastName, date};
    }
}
