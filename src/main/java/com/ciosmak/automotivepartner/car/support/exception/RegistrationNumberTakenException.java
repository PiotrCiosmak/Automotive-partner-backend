package com.ciosmak.automotivepartner.car.support.exception;

public class RegistrationNumberTakenException extends RuntimeException
{
    public RegistrationNumberTakenException()
    {
        super("Auto o podanym numerze rejestracyjnym zostało już dodane. Podaj inny numer rejestracyjny.");
    }
}
