package com.ciosmak.automotivepartner.availability.support.exception;

public class AvailabilityAlreadySubmittedException extends RuntimeException
{
    public Object[] properties;

    public AvailabilityAlreadySubmittedException(String firstName, String lastName)
    {
        properties = new Object[]{firstName, lastName};
    }
}
