package com.ciosmak.automotivepartner.availability.support.exception;

public class IncorrectTypeException extends RuntimeException
{
    public IncorrectTypeException()
    {
        super("Podaj poprawny typ zmiany.");
    }
}
