package com.ciosmak.automotivepartner.car.support.exception;

public class RegistrationNumberTakenException extends RuntimeException
{
    public RegistrationNumberTakenException(String registrationNumber)
    {
        super(String.format("Auto o numerze rejestracyjnym: %s zostało już dodane. Podaj inny numer rejestracyjny.", registrationNumber));
    }
}
