package com.ciosmak.automotivepartner.shared.exception;

public class IncorrectEmailException extends RuntimeException
{
    public IncorrectEmailException()
    {
        super("Podaj poprawny adres email.");
    }
}
