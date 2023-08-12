package com.ciosmak.automotivepartner.user.support.exception;

public class IncorrectLoginDataException extends RuntimeException
{
    public IncorrectLoginDataException()
    {
        super("Podaj poprawne dane.");
    }
}
