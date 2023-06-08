package com.ciosmak.automotivepartner.email.support.exception;

public class InValidEmailException extends RuntimeException
{
    public InValidEmailException()
    {
        super("Podany email jest nieprawidłowy");
    }
}
