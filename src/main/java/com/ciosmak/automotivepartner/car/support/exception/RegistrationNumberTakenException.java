package com.ciosmak.automotivepartner.car.support.exception;

public class RegistrationNumberTakenException extends RuntimeException
{
    public RegistrationNumberTakenException()
    {
        super("Podaney numer rejestracyjny jest już zajęty");
    }
}
