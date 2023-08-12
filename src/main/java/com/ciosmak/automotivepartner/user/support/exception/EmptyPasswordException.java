package com.ciosmak.automotivepartner.user.support.exception;

public class EmptyPasswordException extends RuntimeException
{
    public EmptyPasswordException()
    {
        super("Podaj hasło.");
    }
}
