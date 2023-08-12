package com.ciosmak.automotivepartner.car.support.exception;

public class IncorrectRegistrationNumberException extends RuntimeException
{
    public IncorrectRegistrationNumberException()
    {
        super("Podaj poprawny numer rejestracyjny auta.");
    }
}
