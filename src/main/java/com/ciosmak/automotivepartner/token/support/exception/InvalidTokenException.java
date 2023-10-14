package com.ciosmak.automotivepartner.token.support.exception;

public class InvalidTokenException extends RuntimeException
{
    public InvalidTokenException()
    {
        super("Token nie jest poprawny.");
    }
}
