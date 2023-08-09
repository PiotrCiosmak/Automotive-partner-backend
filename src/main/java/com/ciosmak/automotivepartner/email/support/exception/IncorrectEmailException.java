package com.ciosmak.automotivepartner.email.support.exception;

public class IncorrectEmailException extends RuntimeException
{
    public IncorrectEmailException()
    {
        super("Podaj poprawny adres email.");
    }
}
