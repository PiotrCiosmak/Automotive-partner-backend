package com.ciosmak.automotivepartner.availability.support.exception;

public class AvailabilityAlreadySubmittedException extends RuntimeException
{
    public AvailabilityAlreadySubmittedException()
    {
        super("Dyspozycyjność została już złożona.");
    }
}
