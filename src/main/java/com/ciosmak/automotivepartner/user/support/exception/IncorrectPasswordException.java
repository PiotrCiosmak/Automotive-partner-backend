package com.ciosmak.automotivepartner.user.support.exception;

public class IncorrectPasswordException extends RuntimeException
{
    public IncorrectPasswordException()
    {
        super("Podaj poprawne hasło.");
    }
}
