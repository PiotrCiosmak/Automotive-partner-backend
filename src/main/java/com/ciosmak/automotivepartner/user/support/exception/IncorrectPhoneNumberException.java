package com.ciosmak.automotivepartner.user.support.exception;

public class IncorrectPhoneNumberException extends RuntimeException
{
    public IncorrectPhoneNumberException()
    {
        super("Podaj poprawny numer telefonu.");
    }
}
