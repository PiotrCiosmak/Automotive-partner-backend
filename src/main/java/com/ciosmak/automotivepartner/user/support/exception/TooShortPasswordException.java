package com.ciosmak.automotivepartner.user.support.exception;

public class TooShortPasswordException extends RuntimeException
{
    public TooShortPasswordException()
    {
        super("Użyj 8 lub więcej znaków aby stworzyć hasło.");
    }
}
