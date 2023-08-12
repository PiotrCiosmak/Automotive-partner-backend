package com.ciosmak.automotivepartner.user.support.exception;

public class EmptyFirstNameException extends RuntimeException
{
    public EmptyFirstNameException()
    {
        super("Podaj imie.");
    }
}
