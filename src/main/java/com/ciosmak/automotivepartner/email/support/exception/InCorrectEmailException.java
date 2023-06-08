package com.ciosmak.automotivepartner.email.support.exception;

public class InCorrectEmailException extends RuntimeException
{
    public InCorrectEmailException()
    {
        super("Podany email jest nieprawidłowy");
    }
}
