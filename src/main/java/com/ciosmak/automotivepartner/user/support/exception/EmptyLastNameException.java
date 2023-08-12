package com.ciosmak.automotivepartner.user.support.exception;

public class EmptyLastNameException extends RuntimeException
{
    public EmptyLastNameException()
    {
        super("Podaj nazwisko.");
    }
}