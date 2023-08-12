package com.ciosmak.automotivepartner.user.support.exception;

public class IncorrectEmailException extends RuntimeException
{
    public IncorrectEmailException()
    {
        super("Podaj poprawny adres email.");
    }
}