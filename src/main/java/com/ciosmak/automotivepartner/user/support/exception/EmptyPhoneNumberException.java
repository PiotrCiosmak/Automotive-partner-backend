package com.ciosmak.automotivepartner.user.support.exception;

public class EmptyPhoneNumberException extends RuntimeException
{
    public EmptyPhoneNumberException()
    {
        super("Podaj numer telefonu.");
    }
}
