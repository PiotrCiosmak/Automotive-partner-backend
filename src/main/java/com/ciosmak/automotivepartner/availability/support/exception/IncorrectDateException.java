package com.ciosmak.automotivepartner.availability.support.exception;

public class IncorrectDateException extends RuntimeException
{
    public IncorrectDateException()
    {
        super("Podaj poprawną datę.");
    }
}
