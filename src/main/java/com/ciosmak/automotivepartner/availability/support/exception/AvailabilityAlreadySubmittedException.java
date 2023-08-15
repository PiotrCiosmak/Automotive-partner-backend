package com.ciosmak.automotivepartner.availability.support.exception;

public class AvailabilityAlreadySubmittedException extends RuntimeException
{
    public AvailabilityAlreadySubmittedException(String firsName, String LastName)
    {
        super(String.format("Dyspozycyjność na następny tydzień została już złożona przez %s %s.", firsName, LastName));
    }
}
