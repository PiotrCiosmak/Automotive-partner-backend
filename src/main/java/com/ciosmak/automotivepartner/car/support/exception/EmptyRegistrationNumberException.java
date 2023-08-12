package com.ciosmak.automotivepartner.car.support.exception;

public class EmptyRegistrationNumberException extends RuntimeException
{
    public EmptyRegistrationNumberException()
    {
        super("Podaj numer rejestracyjny auta.");
    }
}
