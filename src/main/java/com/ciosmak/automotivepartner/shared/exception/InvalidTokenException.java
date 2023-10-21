package com.ciosmak.automotivepartner.shared.exception;

public class InvalidTokenException extends RuntimeException
{
    public InvalidTokenException()
    {
        super("Token nie jest poprawny.");
    }
}
